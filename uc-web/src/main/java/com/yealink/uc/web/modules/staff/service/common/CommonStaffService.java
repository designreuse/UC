package com.yealink.uc.web.modules.staff.service.common;

import java.util.ArrayList;
import java.util.List;

import com.yealink.uc.common.modules.staff.entity.Staff;
import com.yealink.uc.common.modules.staff.entity.User;
import com.yealink.uc.common.modules.stafforgmapping.entity.StaffOrgMapping;
import com.yealink.uc.platform.crypto.AuthFactory;
import com.yealink.uc.platform.exception.BusinessHandleException;
import com.yealink.uc.platform.utils.DataConverter;
import com.yealink.uc.platform.utils.MessageUtil;
import com.yealink.uc.web.modules.org.dao.OrgDao;
import com.yealink.uc.web.modules.org.service.common.OrgCommonService;
import com.yealink.uc.web.modules.staff.dao.StaffDao;
import com.yealink.uc.web.modules.staff.dao.UserDao;
import com.yealink.uc.web.modules.stafforgmapping.dao.StaffOrgMappingDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by yewl on 2016/6/20.
 */
@Service
public class CommonStaffService {
    private static final String STAFF_DEFAULT_PASSWORD = "11111111";
    public static  final String STAFF_DEFAULT_ENCRYPT_PASSWORD = AuthFactory.encryptPassword(STAFF_DEFAULT_PASSWORD);
    @Autowired
    private StaffDao staffDao;
    @Autowired
    private OrgDao orgDao;
    @Autowired
    private OrgCommonService orgCommonService;
    @Autowired
    private StaffOrgMappingDao staffOrgMappingDao;
    @Autowired
    UserDao userDao;

    /**
     * 合并staff和org关联关系
     * 1.查询目标org列表，确保目标org是存在的<br>
     * 2.判断之前是否存在目标org中，若存在则使用之前的关联关系，若不存在则新建一个关联关系
     * @param staffId
     * @param newOrgIds
     * @param oldStaffOrgMappings
     * @param enterpriseId
     * @return
     */
    public List<StaffOrgMapping> mergeOrgMappings(Long staffId, List<Long> newOrgIds, List<StaffOrgMapping> oldStaffOrgMappings, Long enterpriseId) {
        if(newOrgIds == null){
            return null;
        }
        newOrgIds = findExistOrgs(newOrgIds, enterpriseId);
        if(oldStaffOrgMappings == null){
            oldStaffOrgMappings = new ArrayList<>();
        }
        List<StaffOrgMapping> mergeStaffOrgMappings = new ArrayList<>();
        boolean hasOldMapping;
        for(Long orgId : newOrgIds){
            hasOldMapping = false;
            for(StaffOrgMapping oldStaffOrgMapping : oldStaffOrgMappings){
                if (orgId.equals(oldStaffOrgMapping.getOrgId())) {
                    mergeStaffOrgMappings.add(oldStaffOrgMapping);
                    hasOldMapping = true;
                    break;
                }
            }
            if(!hasOldMapping){
                mergeStaffOrgMappings.add(new StaffOrgMapping(staffId, orgId, staffOrgMappingDao.nextIndexInOrg(orgId)));
            }
        }
        return mergeStaffOrgMappings;
    }

    public List<Long> findExistOrgs(List<Long> orgIds, Long enterpriseId) {
        List<Long> exitOrgIds = orgDao.findOrgIds(orgIds, enterpriseId);
        if(exitOrgIds == null || exitOrgIds.isEmpty()){
            throw new BusinessHandleException(MessageUtil.getMessage("staff.exception.target.org.not.exist"));
        }
        return exitOrgIds;
    }


    public void batchEditOrgModification(List<Long> orgIds, Long modificationDate){
        orgCommonService.batchEditOrgModificationByIds(orgIds, modificationDate);
    }

    public static List<Long> getOrgIdsFromMapping(List<StaffOrgMapping> stafforgMappings) {
        List<Long> orgIds = new ArrayList<>();
        for (StaffOrgMapping staffOrgMapping : stafforgMappings){
            orgIds.add(staffOrgMapping.getOrgId());
        }
        return orgIds;
    }

    //todo this is for openfire , will be removed future
    public void saveUser(String password, final String createStaffUsername, final Staff staff) {
        User user = DataConverter.copy(new User(), staff);
        user.setUsername(createStaffUsername);
        user.setEncryptedPassword(AuthFactory.encryptPassword(password));
        userDao.save(user);
    }

    public void updateUser(String password,final Staff staff) {
        User user = DataConverter.copy(new User(), staff);
        user.setEncryptedPassword(AuthFactory.encryptPassword(password));
        userDao.updateUser(user);
    }

}
