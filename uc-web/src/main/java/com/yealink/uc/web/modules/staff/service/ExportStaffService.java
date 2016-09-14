package com.yealink.uc.web.modules.staff.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.yealink.uc.common.modules.org.entity.Org;
import com.yealink.uc.common.modules.staff.entity.Staff;
import com.yealink.uc.common.modules.stafforgmapping.entity.StaffOrgMapping;
import com.yealink.uc.platform.utils.DataConverter;
import com.yealink.uc.web.modules.org.dao.OrgDao;
import com.yealink.uc.web.modules.staff.entity.ExportStaff;
import com.yealink.uc.web.modules.stafforgmapping.dao.StaffOrgMappingDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

/**
 * Created by yewl on 2016/8/5.
 */
@Service
public class ExportStaffService {
    @Autowired
    private QueryStaffService queryStaffService;

    @Autowired
    private StaffOrgMappingDao staffOrgMappingDao;

    @Autowired
    private OrgDao orgDao;

    public List<ExportStaff> exportStaffs(Long enterpriseId){
        List<ExportStaff> exportStaffList = new ArrayList<>();
        List<Staff> staffList = queryStaffService.findAllAvailableStaffs(enterpriseId);
        for(Staff staff : staffList){
            exportStaffList.add(staffToExportStaff(staff));
        }
        setOrgForExportStaff(exportStaffList, enterpriseId);
        return exportStaffList;
    }

    /**
     * 将Staff转换成ExportStaff，ExportStaff的org字段还未设置
     * @param staff
     * @return
     */
    private ExportStaff staffToExportStaff(Staff staff) {
        ExportStaff exportStaff = DataConverter.copy(new ExportStaff(), staff);
        StringBuilder stringBuilder = new StringBuilder();
        if(staff.getMobilePhones() != null){
            for(String mobilePhone : staff.getMobilePhones()){
                stringBuilder.append(mobilePhone);
                stringBuilder.append("\n");
            }
        }
        if(stringBuilder.length() > 0){
            stringBuilder = stringBuilder.deleteCharAt(stringBuilder.length() - 1);
            exportStaff.setMobilePhone(stringBuilder.toString());
        }
        return  exportStaff;
    }

    private void setOrgForExportStaff(List<ExportStaff> exportStaffList, Long enterpriseId) {
        List<Long> staffIdList = new ArrayList<>();
        for(ExportStaff exportStaff : exportStaffList){
            staffIdList.add(exportStaff.get_id());
        }
        Map<Long, Set<Long>> staffOrgMap = constructStaffOrgMap(staffIdList);
        Map<Long, String> orgIdPathNameMap = constructOrgIdPathNameMap(enterpriseId);
        for(ExportStaff exportStaff : exportStaffList){
            StringBuilder orgBuilder = new StringBuilder();
            Set<Long> orgIds = staffOrgMap.get(exportStaff.get_id());
            if (CollectionUtils.isEmpty(orgIds)) continue;
            for(Long orgId : orgIds){
                orgBuilder.append(orgIdPathNameMap.get(orgId));
                orgBuilder.append("\n");
            }
            if(orgBuilder.length() > 0){
                orgBuilder = orgBuilder.deleteCharAt(orgBuilder.length() - 1);
                exportStaff.setOrg(orgBuilder.toString());
            }
        }
    }

    private Map<Long, Set<Long>> constructStaffOrgMap(List<Long> staffIdList) {
        List<StaffOrgMapping> stafforgMappings = staffOrgMappingDao.findByStaffList(staffIdList);
        Map<Long, Set<Long>> staffOrgMap = new HashMap();
        for(StaffOrgMapping staffOrgMapping : stafforgMappings){
            Set<Long> orgIdList = staffOrgMap.get(staffOrgMapping.getStaffId());
            if(orgIdList == null){
                orgIdList = new HashSet<>();
                staffOrgMap.put(staffOrgMapping.getStaffId(), orgIdList);
            }
            orgIdList.add(staffOrgMapping.getOrgId());
        }
        return staffOrgMap;
    }

    private Map<Long, String> constructOrgIdPathNameMap(Long enterpriseId) {
        Map<Long, String> orgIdPathNameMap = new HashMap<>();
        List<Org> orgList = orgDao.findAll(enterpriseId);
        Map<Long, String> idOrgNameMap = new HashMap<>();
        for(Org org : orgList){
            idOrgNameMap.put(org.get_id(), org.getName());
        }
        for(Org org : orgList){
            String[] orgIds = org.getOrgPath().split(":");
            StringBuilder orgPathNameBuffer = new StringBuilder();
            for(String orgId : orgIds){
                orgPathNameBuffer.append(idOrgNameMap.get(Long.valueOf(orgId)));
                orgPathNameBuffer.append("/");
            }
            if(orgPathNameBuffer.length() != 0){
                orgPathNameBuffer.deleteCharAt(orgPathNameBuffer.length() - 1);
            }
            orgIdPathNameMap.put(org.get_id(), orgPathNameBuffer.toString());
        }
        return orgIdPathNameMap;
    }


}
