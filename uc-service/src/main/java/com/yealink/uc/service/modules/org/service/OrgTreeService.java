package com.yealink.uc.service.modules.org.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.github.stuxuhai.jpinyin.PinyinFormat;
import com.github.stuxuhai.jpinyin.PinyinHelper;
import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.yealink.uc.common.modules.businessaccount.entity.BusinessAccount;
import com.yealink.uc.common.modules.businessaccount.vo.BusinessType;
import com.yealink.uc.common.modules.org.entity.Org;
import com.yealink.uc.common.modules.phone.Phone;
import com.yealink.uc.common.modules.staff.entity.Staff;
import com.yealink.uc.common.modules.stafforgmapping.entity.StaffOrgMapping;
import com.yealink.uc.service.modules.businessaccount.dao.BusinessAccountDao;
import com.yealink.uc.service.modules.org.dao.OrgDao;
import com.yealink.uc.service.modules.org.vo.OrgTreeNodeType;
import com.yealink.uc.service.modules.org.vo.OrgTreeNodeView;
import com.yealink.uc.service.modules.phone.PhoneDao;
import com.yealink.uc.service.modules.staff.dao.StaffDao;
import com.yealink.uc.service.modules.stafforgmapping.dao.StaffOrgMappingDao;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Transformer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author ChNan
 */
@Service
@SuppressWarnings("unchecked")
public class OrgTreeService {
    @Autowired
    private OrgDao orgDao;
    @Autowired
    private StaffDao staffDao;
    @Autowired
    private StaffOrgMappingDao staffOrgMappingDao;
    @Autowired
    BusinessAccountDao businessAccountDao;
    @Autowired
    PhoneDao phoneDao;

    Logger logger = LoggerFactory.getLogger(OrgTreeService.class);

    public List<OrgTreeNodeView> showOrgStaffTreeNodes(Long enterpriseId) {
        logger.info("Go in to listOrgTrees");
        List<Org> orgList = orgDao.findAll(enterpriseId);
        Map<Long, List<StaffOrgMapping>> orgStaffOrgMappingMapListMap = createStaffOrgMappingMap(orgList);
        List<OrgTreeNodeView> treeNodeList = new ArrayList<>();
        List<OrgTreeNodeView> orgNodeList = createOrgNodeList(orgList);
        List<OrgTreeNodeView> staffNodeList = new ArrayList<>();
        staffNodeList.addAll(createStaffNodeList(orgStaffOrgMappingMapListMap, enterpriseId));
        treeNodeList.addAll(orgNodeList);
        treeNodeList.addAll(staffNodeList);
        return treeNodeList;
    }

    private Map<Long, List<StaffOrgMapping>> createStaffOrgMappingMap(final List<Org> orgList) {
        List<Long> orgIdList = (List<Long>) CollectionUtils.collect(orgList, new Transformer() {
            @Override
            public Object transform(final Object input) {
                return ((Org) input).get_id();
            }
        });
        List<StaffOrgMapping> stafforgMappings = staffOrgMappingDao.findByOrgList(orgIdList);
        Map<Long, List<StaffOrgMapping>> orgStaffOrgMappingMapListMap = Maps.newTreeMap();
        for (StaffOrgMapping staffOrgMapping : stafforgMappings) {
            if (orgStaffOrgMappingMapListMap.get(staffOrgMapping.getOrgId()) == null) {
                List<StaffOrgMapping> mappingList = new ArrayList<>();
                mappingList.add(staffOrgMapping);
                orgStaffOrgMappingMapListMap.put(staffOrgMapping.getOrgId(), mappingList);
            } else {
                orgStaffOrgMappingMapListMap.get(staffOrgMapping.getOrgId()).add(staffOrgMapping);
            }
        }
        return orgStaffOrgMappingMapListMap;
    }

    private List<OrgTreeNodeView> createStaffNodeList(Map<Long, List<StaffOrgMapping>> orgStaffOrgMappingMapListMap, Long enterpriseId) {
        List<OrgTreeNodeView> treeNodes = new ArrayList<>();
        if (orgStaffOrgMappingMapListMap == null || orgStaffOrgMappingMapListMap.isEmpty()) return treeNodes;
        List<Staff> allAvailableStaffList = staffDao.findAllAvailable(enterpriseId);
        List<Long> staffIds = Lists.transform(allAvailableStaffList, new Function<Staff, Long>() {
            @Override
            public Long apply(final Staff input) {
                return input.get_id();
            }
        });
        Map<Long, Staff> staffMap = createAvailableStaffMap(allAvailableStaffList);
        Map<Long, BusinessAccount> staffIdExtensionAccountMap = createStaffExtensionMap(staffIds);
        Map<Long, Phone> staffIdPhoneMap = createStaffPhoneMap(staffIds);
        for (Map.Entry<Long, List<StaffOrgMapping>> entry : orgStaffOrgMappingMapListMap.entrySet()) {
            List<StaffOrgMapping> staffOrgMappings = entry.getValue();
            if (CollectionUtils.isEmpty(staffOrgMappings)) continue;
            for (StaffOrgMapping staffOrgMapping : staffOrgMappings) {
                if (staffMap.get(staffOrgMapping.getStaffId()) != null) {
                    BusinessAccount extensionAccount = staffIdExtensionAccountMap.get(staffOrgMapping.getStaffId());
                    Phone phone = staffIdPhoneMap.get(staffOrgMapping.getStaffId());
                    treeNodes.add(createStaffNode(staffOrgMapping, staffMap.get(staffOrgMapping.getStaffId()),
                        extensionAccount != null ? extensionAccount.getUsername() : null, phone != null ? phone.getIp() : null));
                }
            }
        }
        return treeNodes;
    }

    private Map<Long, Staff> createAvailableStaffMap(List<Staff> allAvailableStaffList) {
        Map<Long, Staff> staffMap = Maps.newTreeMap();
        for (Staff staff : allAvailableStaffList) {
            staffMap.put(staff.get_id(), staff);
        }
        return staffMap;
    }

    private Map<Long, Phone> createStaffPhoneMap(final List<Long> staffIds) {
        List<Phone> phones = phoneDao.findByStaffs(staffIds);
        return Maps.uniqueIndex(phones, new Function<Phone, Long>() {
            @Override
            public Long apply(final Phone input) {
                return input.getStaffId();
            }
        });
    }

    private Map<Long, BusinessAccount> createStaffExtensionMap(final List<Long> staffIds) {
        List<BusinessAccount> extensionAccounts = businessAccountDao.findByStaffIdsAndType(staffIds, BusinessType.EXTENSION.getCode());
        return Maps.uniqueIndex(extensionAccounts, new Function<BusinessAccount, Long>() {
            @Override
            public Long apply(final BusinessAccount input) {
                return input.getStaffId();
            }
        });
    }

    private List<OrgTreeNodeView> createOrgNodeList(List<Org> orgList) {
        List<OrgTreeNodeView> treeNodes = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(orgList)) {
            for (Org org : orgList) {
                treeNodes.add(createOrgNode(org));
            }
        }
        return treeNodes;
    }

    private OrgTreeNodeView createStaffNode(StaffOrgMapping staffOrgMapping, Staff staff, String extensionNumber, String phoneIp) {
        String pinyin = PinyinHelper.convertToPinyinString(staff.getName(), "", PinyinFormat.WITHOUT_TONE);
        String pinyinAlia = PinyinHelper.getShortPinyin(staff.getName());
        return new OrgTreeNodeView(staff.get_id(), staff.getName(), staffOrgMapping.getOrgId(), OrgTreeNodeType.STAFF.getCode(),
            OrgTreeNodeType.ORG.getCode(), false, String.valueOf(staff.getGender()),
            pinyin, pinyinAlia, staff.getEmail(), staffOrgMapping.getStaffIndexInOrg(), extensionNumber, phoneIp);
    }

    private OrgTreeNodeView createOrgNode(Org org) {
        String pinyin = PinyinHelper.convertToPinyinString(org.getName(), "", PinyinFormat.WITHOUT_TONE);
        String pinyinAlia = PinyinHelper.getShortPinyin(org.getName());
        return new OrgTreeNodeView(org.get_id(), org.getName(), org.getParentId(), OrgTreeNodeType.ORG.getCode(),
            OrgTreeNodeType.ORG.getCode(), org.getParentId() == 0, null,
            pinyin, pinyinAlia, org.getMail(), org.getIndex());
    }
}
