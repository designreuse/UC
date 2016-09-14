package com.yealink.uc.web.modules.staff.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.yealink.uc.common.modules.account.entity.Account;
import com.yealink.uc.common.modules.businessaccount.entity.BusinessAccount;
import com.yealink.uc.common.modules.businessaccount.vo.BusinessType;
import com.yealink.uc.common.modules.enterprise.entity.Enterprise;
import com.yealink.uc.common.modules.org.entity.Org;
import com.yealink.uc.common.modules.staff.entity.Staff;
import com.yealink.uc.common.modules.staff.entity.User;
import com.yealink.uc.common.modules.staff.vo.ImportStaff;
import com.yealink.uc.common.modules.staff.vo.StaffStatus;
import com.yealink.uc.common.modules.stafforgmapping.entity.StaffOrgMapping;
import com.yealink.uc.platform.crypto.AuthFactory;
import com.yealink.uc.platform.exception.BusinessHandleException;
import com.yealink.uc.platform.utils.DataConverter;
import com.yealink.uc.platform.utils.EntityUtil;
import com.yealink.uc.platform.utils.ExcelReader;
import com.yealink.uc.platform.utils.MessageUtil;
import com.yealink.uc.web.modules.account.dao.AccountDao;
import com.yealink.uc.web.modules.account.service.CreateAccountService;
import com.yealink.uc.web.modules.businessaccount.BusinessAccountDao;
import com.yealink.uc.web.modules.common.dao.IdGeneratorDao;
import com.yealink.uc.web.modules.org.dao.OrgDao;
import com.yealink.uc.web.modules.staff.dao.StaffDao;
import com.yealink.uc.web.modules.staff.dao.UserDao;
import com.yealink.uc.web.modules.staff.producer.StaffMessageProducer;
import com.yealink.uc.web.modules.staff.vo.StaffDetail;
import com.yealink.uc.web.modules.stafforgmapping.dao.StaffOrgMappingDao;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by yewl on 2016/6/20.
 */
@Service
public class ImportStaffService {
    public static final String SLASH_STRING = "/";
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private static final int START_ROW = 2;
    private static final String[] IMPORT_FILE_TABLE_HEAD = {"name", "username", "orgPaths", "mobilePhone", "email", "extensionNumber", "phoneMac"};
    private static final String STAFF_DEFAULT_PASSWORD = "123456";
    private static final String STAFF_DEFAULT_ENCRYPT_PASSWORD = AuthFactory.encryptPassword(STAFF_DEFAULT_PASSWORD);
    @Autowired
    private StaffDao staffDao;
    @Autowired
    private AccountDao accountDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private OrgDao orgDao;
    @Autowired
    private StaffOrgMappingDao staffOrgMappingDao;
    @Autowired
    private IdGeneratorDao idGeneratorDao;
    @Autowired
    BusinessAccountDao businessAccountDao;
    @Autowired
    private StaffMessageProducer staffMessageProducer;
    @Autowired
    CreateAccountService createAccountService;

    @Caching(evict = {
        @CacheEvict(value = {"orgTreeNodes", "orgStaffTreeNodes", "orgTreeNodesWithForbiddenStaff"}, key = "#loginedEnterprise.get_id()")
    })
    public Map<String, Object> importStaff(MultipartFile importStaffFile, Enterprise loginedEnterprise) {
        validateImportFile(importStaffFile);
        HashMap<String, Object> resultMap = new HashMap<>();
        List<ImportStaff> importStaffs = createImportStaffs(importStaffFile);
        if (importStaffs == null || importStaffs.isEmpty()) {
            resultMap.put("success", false);
            resultMap.put("message", MessageUtil.getMessage("staff.exception.import.staff.empty"));
            return resultMap;
        }
        List<String> unRepeatUsernameList = checkRepeatUsernameInList(importStaffs); //去除导入文件中重复的username
        List<String> existUsernameList = staffDao.findExistUsername(unRepeatUsernameList, loginedEnterprise.get_id()); //校验username在DB是否已经存在
        if (existUsernameList != null && !existUsernameList.isEmpty()) {
            appendExistUsernameMessage(importStaffs, existUsernameList);
        }

        List<ImportStaff> errorImportStaffList = removeErrorStaff(importStaffs);

        createNotExistOrg(importStaffs, loginedEnterprise.get_id());
        List<StaffDetail> staffDetailList = createStaffDetails(importStaffs, loginedEnterprise);
        List<Staff> staffs = Lists.transform(staffDetailList, new Function<StaffDetail, Staff>() {
            @Override
            public Staff apply(final StaffDetail input) {
                return input.getStaff();
            }
        });

        long successImport = staffDao.batchCreateStaff(staffs);

        if (successImport == 0) {
            resultMap.put("success", false);
            resultMap.put("message", MessageUtil.getMessage("staff.exception.import.not.success"));
            return resultMap;
        }

        List<BusinessAccount> imAccounts = Lists.transform(staffDetailList, new Function<StaffDetail, BusinessAccount>() {
            @Override
            public BusinessAccount apply(final StaffDetail input) {
                return input.getImAccount();
            }
        });
        List<Account> ucAccounts = Lists.transform(staffDetailList, new Function<StaffDetail, Account>() {
            @Override
            public Account apply(final StaffDetail input) {
                return input.getUcAccount();
            }
        });

        accountDao.batchSave(ucAccounts);

        businessAccountDao.batchSave(imAccounts);

        saveUsers(staffDetailList); //todo 暂时为了兼容openfire那边的数据结构，创建一个user，但是需要删除掉UC 1.0版本

        if (!errorImportStaffList.isEmpty()) {
            resultMap.put("errorImportStaffList", errorImportStaffList);
        }

        //构造保存StaffOrgMapping
        saveStaffOrgMapping(loginedEnterprise, importStaffs);

        resultMap.put("success", true);
        resultMap.put("message", MessageUtil.getMessage("staff.exception.import.success", successImport));
        return resultMap;
    }

    //剔除有错误信息的导入staff
    private List<ImportStaff> removeErrorStaff(final List<ImportStaff> importStaffs) {
        List<ImportStaff> errorImportStaffList = findErrorImportStaffList(importStaffs);
        importStaffs.removeAll(errorImportStaffList);
        return errorImportStaffList;
    }

    private void saveUsers(final List<StaffDetail> staffDetailList) {
        List<User> users = Lists.transform(staffDetailList, new Function<StaffDetail, User>() {
            @Override
            public User apply(final StaffDetail input) {
                User user = DataConverter.copy(new User(), input.getStaff());
                user.setUsername(input.getImAccount().getUsername());
                user.setEncryptedPassword(input.getImAccount().getEncryptedPassword());
                return user;
            }
        });
        userDao.batchCreateStaff(users);
    }

    private void saveStaffOrgMapping(final Enterprise loginedEnterprise, final List<ImportStaff> importStaffs) {
        List<StaffOrgMapping> staffOrgMappings = constructStaffOrgMapping(importStaffs, loginedEnterprise.get_id());
        staffOrgMappingDao.batchSave(staffOrgMappings);
        staffMessageProducer.importStaff(getOrgIds(staffOrgMappings));
    }

    private List<Long> getOrgIds(List<StaffOrgMapping> safforgMappings) {
        Set<Long> orgIds = new HashSet<>();
        for (StaffOrgMapping staffOrgMapping : safforgMappings) {
            orgIds.add(staffOrgMapping.getOrgId());
        }
        return new ArrayList<>(orgIds);
    }

    private void validateImportFile(MultipartFile importStaffFile) {
        if (!ExcelReader.validateExcel(importStaffFile)) {
            throw new BusinessHandleException(MessageUtil.getMessage("staff.exception.import.only.support.excel"));
        }
        if (importStaffFile.getSize() > 5 * 1024 * 1024) {
            throw new BusinessHandleException(MessageUtil.getMessage("staff.exception.import.file.too.big", 5));
        }
    }

    private List<ImportStaff> createImportStaffs(MultipartFile importStaffFile) {
        List<ImportStaff> importStaffList = new ArrayList<>();
        ExcelReader excelReader;
        try {
            excelReader = new ExcelReader(importStaffFile, 0);
            int rowCount = excelReader.getRowCount();
            if (rowCount > 5001) throw new BusinessHandleException("导入数据最大条数不能超过5000条！请重新上传");
            int rowIndex = START_ROW, colIndex;
            Map<String, Object> importStaffMap = new HashMap<>();
            for (; rowIndex <= rowCount; rowIndex++) {
                for (colIndex = 1; colIndex <= IMPORT_FILE_TABLE_HEAD.length; colIndex++) {
                    importStaffMap.put(IMPORT_FILE_TABLE_HEAD[colIndex - 1], excelReader.getCellData(rowIndex, colIndex));
                }
                ImportStaff importStaff = DataConverter.copyFromMap(new ImportStaff(), importStaffMap);
                importStaff.setRowNum(rowIndex);
                importStaff.constructGender();
                importStaff.constructMobilePhones();
                importStaff.validate();
                importStaffList.add(importStaff);
            }
        } catch (IOException | InvalidFormatException e) {
            logger.error(MessageUtil.traceExceptionMessage(e));
        }
        return importStaffList;
    }

    private List<String> checkRepeatUsernameInList(List<ImportStaff> importStaffList) {
        Map<String, Integer> checkedUsername = new HashMap<>();
        for (ImportStaff importStaff : importStaffList) {
            if (importStaff.hasError()) {
                continue;
            }
            if (checkedUsername.keySet().contains(importStaff.getUsername())) {
                importStaff.appendErrorMessage(MessageUtil.getMessage("staff.exception.import.file.repeat.username", checkedUsername.get(importStaff.getUsername())));
                continue;
            }
            checkedUsername.put(importStaff.getUsername(), importStaff.getRowNum());
        }
        return new ArrayList<>(checkedUsername.keySet());
    }

    private void appendExistUsernameMessage(List<ImportStaff> importStaffList, List<String> existUsernameList) {
        for (String existUsername : existUsernameList) {
            for (ImportStaff importStaff : importStaffList) {
                if (existUsername.equalsIgnoreCase(importStaff.getUsername())) {
                    importStaff.appendErrorMessage(MessageUtil.getMessage("staff.exception.import.username.exist"));
                }
            }
        }
    }

    private List<ImportStaff> findErrorImportStaffList(List<ImportStaff> importStaffList) {
        List<ImportStaff> errorImportStaffList = new ArrayList<>();
        for (ImportStaff importStaff : importStaffList) {
            if (importStaff.hasError()) {
                errorImportStaffList.add(importStaff);
            }
        }
        return errorImportStaffList;
    }

    /**
     * 构造用户与部门的映射关系
     * 1.获取enterprise下的org
     * 2.将org的orgPath转成name表示，并存到Map<orgId, orgPath>中
     * 3.staff的orgPath若不是以根基点开头则补上
     * 4.生成staff的id，staff的orgPath与Map<orgId, orgPath>匹配
     * 6.匹配结果为空则把staff放在根节点下
     * 7.匹配上了查询org对应的最大index给staff，并记录Map<orgId, maxIndex>
     *
     * @param importStaffList
     * @param enterpriseId
     */
    private List<StaffOrgMapping> constructStaffOrgMapping(List<ImportStaff> importStaffList, Long enterpriseId) {
        List<Org> orgList = orgDao.findAll(enterpriseId);
        Map<String, Long> orgPathIdMap = constructOrgPathIdMap(orgList);
        Org rootOrg = findRootOrg(orgList);
        Map<Long, Integer> orgNextIndexMap = new HashMap<>();

        List<StaffOrgMapping> staffOrgMappings = new ArrayList<>();
        for (ImportStaff importStaff : importStaffList) {
            String[] staffOrgPaths = importStaff.getOrgPaths().split("\n"); //为了使用一个人员多个组织的情况 ,例如 开发有限公司/研发中心/软件四部\n开发有限公司/总监部门

            Set<String> staffOrgPathSet = new HashSet<>();
            Collections.addAll(staffOrgPathSet, staffOrgPaths);
            if (staffOrgPathSet.isEmpty()) {
                staffOrgPathSet.add(rootOrg.getName());
            }
            Set<Long> orgIds = new HashSet<>();
            for (String staffOrgPath : staffOrgPathSet) {
                if (!staffOrgPath.split(SLASH_STRING)[0].equalsIgnoreCase(rootOrg.getName())) {
                    staffOrgPath = rootOrg.getName() + SLASH_STRING + staffOrgPath;
                }
                orgIds.add((orgPathIdMap.containsKey(staffOrgPath)) ? orgPathIdMap.get(staffOrgPath) : rootOrg.get_id());
            }
            for (Long orgId : orgIds) {
                if (!orgNextIndexMap.containsKey(orgId)) {
                    orgNextIndexMap.put(orgId, staffOrgMappingDao.nextIndexInOrg(orgId));
                }
                staffOrgMappings.add(new StaffOrgMapping(importStaff.getId(), orgId, orgNextIndexMap.get(orgId)));
                orgNextIndexMap.put(orgId, orgNextIndexMap.get(orgId) + 1);
            }
        }
        return staffOrgMappings;
    }

    //创建不存在的部门
    private void createNotExistOrg(List<ImportStaff> importStaffList, long enterpriseId) {
        List<Org> orgList = orgDao.findAll(enterpriseId);
        Map<String, Long> orgPathIdMap = constructOrgPathIdMap(orgList);
        Org rootOrg = findRootOrg(orgList);
        List<Org> newOrgList = new ArrayList<>();
        for (ImportStaff importStaff : importStaffList) {
            String[] staffOrgPaths = importStaff.getOrgPaths().split("\n");
            for (String staffOrgPath : staffOrgPaths) {
                String regex = SLASH_STRING;
                if (!staffOrgPath.split(regex)[0].equalsIgnoreCase(rootOrg.getName())) {
                    staffOrgPath = rootOrg.getName() + regex + staffOrgPath;
                }
                if (orgPathIdMap.containsKey(staffOrgPath)) {
                    continue;
                }
                String[] orgNames = staffOrgPath.split(regex);
                StringBuilder orgNamePathBuilder = new StringBuilder();
                StringBuilder orgIdPathBuilder = new StringBuilder();
                long parentId = rootOrg.get_id();
                for (String orgName : orgNames) {
                    orgIdPathBuilder.append(":");
                    orgNamePathBuilder.append(regex).append(orgName);
                    if (!orgPathIdMap.containsKey(orgNamePathBuilder.toString().substring(1))) {
                        Org org = new Org();
                        long orgNextId = idGeneratorDao.nextId(EntityUtil.getEntityName(Org.class));
                        org.set_id(orgNextId);
                        org.setIndex(orgDao.nextIndex(parentId));
                        org.setName(orgName);
                        org.setEnterpriseId(enterpriseId);
                        org.setModificationDate(System.currentTimeMillis());
                        org.setOrgPath(orgIdPathBuilder.append(orgNextId).toString().substring(1));
                        org.setParentId(parentId);
                        newOrgList.add(org);
                        orgPathIdMap.put(orgNamePathBuilder.toString().substring(1), orgNextId);
                    }
                    parentId = orgPathIdMap.get(orgNamePathBuilder.toString().substring(1));
                    orgIdPathBuilder.append(parentId);
                }
            }
        }
        if (!newOrgList.isEmpty()) {
            orgDao.batchSave(newOrgList);
        }
    }

    private Org findRootOrg(List<Org> orgList) {
        for (Org org : orgList) {
            if (org.getParentId() == 0) {
                return org;
            }
        }
        throw new BusinessHandleException("staff.exception.root.node.not.exist");
    }

    // 构造组织完整路径和orgid的map,例如 厦门亿联网络/技术部门/UC部门/服务器端开发组  -> 20
    private Map<String, Long> constructOrgPathIdMap(List<Org> orgList) {
        Map<String, Long> orgPathIdMap = new HashMap<>();
        Map<Long, String> idOrgNameMap = new HashMap<>();
        for (Org org : orgList) {
            idOrgNameMap.put(org.get_id(), org.getName());
        }
        for (Org org : orgList) {
            String[] orgIds = org.getOrgPath().split(":");
            StringBuilder orgPathNameBuffer = new StringBuilder();
            for (String orgId : orgIds) {
                orgPathNameBuffer.append(idOrgNameMap.get(Long.valueOf(orgId)));
                orgPathNameBuffer.append(SLASH_STRING);
            }
            if (orgPathNameBuffer.length() != 0) {
                orgPathNameBuffer.deleteCharAt(orgPathNameBuffer.length() - 1);
            }
            orgPathIdMap.put(orgPathNameBuffer.toString(), org.get_id());
        }
        return orgPathIdMap;
    }

    private List<StaffDetail> createStaffDetails(List<ImportStaff> importStaffList, Enterprise loginedEnterprise) {
        List<StaffDetail> staffDetailList = new ArrayList<>();
        for (ImportStaff importStaff : importStaffList) {
            StaffDetail staffDetail = new StaffDetail();
            Staff staff = DataConverter.copy(new Staff(), importStaff);
            staff.set_id(idGeneratorDao.nextId(EntityUtil.getEntityCode(Staff.class)));
            staff.setCreationDate(new Date().getTime());
            staff.setModificationDate(new Date().getTime());
            staff.setEnterpriseId(loginedEnterprise.get_id());
            staff.setDomain(loginedEnterprise.getDomain());
            staff.set_id(importStaff.getId());
            staff.setStatus(StaffStatus.WORKING.getCode());
            staffDetail.setStaff(staff);

            BusinessAccount imAccount = new BusinessAccount();
            imAccount.setBusinessType(BusinessType.IM.getCode());
            imAccount.setUsername(importStaff.getUsername());
            imAccount.setEncryptedPassword(AuthFactory.encryptPassword(STAFF_DEFAULT_PASSWORD));
            imAccount.set_id(idGeneratorDao.nextId(EntityUtil.getEntityCode(BusinessAccount.class)));
            imAccount.setStaffId(staff.get_id());
            staffDetail.setImAccount(imAccount);

            Account primaryUcAccount = createAccountService.createAccount(loginedEnterprise.get_id(), staff.get_id(),
                staff.getDomain(), imAccount.getUsername(), STAFF_DEFAULT_PASSWORD, -1);
            staffDetail.setUcAccount(primaryUcAccount);

            staffDetailList.add(staffDetail);
        }
        return staffDetailList;
    }
}
