package com.yealink.uc.web.modules.staff.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.yealink.dataservice.client.util.Pager;
import com.yealink.uc.common.modules.staff.entity.Staff;
import com.yealink.uc.common.modules.staff.vo.StaffStatus;
import com.yealink.uc.platform.exception.BusinessHandleException;
import com.yealink.uc.platform.utils.MessageUtil;
import com.yealink.uc.platform.utils.StringUtil;
import com.yealink.uc.web.modules.staff.dao.StaffDao;
import com.yealink.uc.web.modules.staff.request.QueryStaffRequest;
import com.yealink.uc.web.modules.staff.request.SearchStaffRequest;
import com.yealink.uc.web.modules.stafforgmapping.dao.StaffOrgMappingDao;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.yealink.dataservice.client.util.Filter.eq;
import static com.yealink.dataservice.client.util.Filter.in;
import static com.yealink.dataservice.client.util.Filter.lt;
import static com.yealink.dataservice.client.util.Filter.or;
import static com.yealink.dataservice.client.util.Filter.regex;

/**
 * Created by yewl on 2016/6/20.
 */
@Service
public class QueryStaffService {
    @Autowired
    private StaffDao staffDao;
    @Autowired
    private StaffOrgMappingDao staffOrgMappingDao;

    public Staff getStaff(Long staffId, Long enterpriseId) {
        Staff staff = staffDao.get(staffId, enterpriseId);
        if (staff == null) {
            throw new BusinessHandleException("staff.exception.not.found", staffId);
        }
        return staff;
    }

    public List<Staff> findAllAvailableStaffs(Long enterpriseId) {
        List<Staff> staffList = staffDao.findAllAvailable(enterpriseId);
        if (staffList == null || staffList.isEmpty()) {
            throw new BusinessHandleException("staff.excepiton.not.available.in.enterprise", enterpriseId);
        }
        return staffList;
    }

    public List<Staff> findStaffs(List<Long> staffIdList, Long enterpriseId) {
        if (CollectionUtils.isEmpty(staffIdList)) return Collections.emptyList();
        List<Staff> staffList = staffDao.findStaffs(staffIdList, enterpriseId);
        if (staffIdList.isEmpty()) {
            throw new BusinessHandleException(MessageUtil.getMessage("staff.exception.not.found.staffs", staffIdList));
        }
        return staffList;
    }

    public List<Long> findExistStaffIds(List<Long> staffIdList, Long enterLongId) {
        if (CollectionUtils.isEmpty(staffIdList)) {
            return Collections.emptyList();
        }
        List<Long> staffIds = staffDao.findStaffIds(staffIdList, enterLongId);
        if (staffIdList.isEmpty()) {
            throw new BusinessHandleException(MessageUtil.getMessage("staff.exception.not.found.staffs", staffIdList));
        }
        return staffIds;
    }

    //todo combine with searchStaffList
    public List<Staff> queryStaffList(QueryStaffRequest queryStaffRequest, Long enterpriseId) {
        return staffDao.queryStaffList(queryStaffRequest, enterpriseId);
    }

    public Pager<Map<String, Object>> searchForbiddenStaffList(SearchStaffRequest searchStaffRequest, Long enterpriseId) {
        List<Map<String, Object>> andArray = new ArrayList<>();
        andArray.add(eq("enterpriseId", enterpriseId).toMap());
        andArray.add(eq("status", StaffStatus.RECYCLE.getCode()).toMap());
        Map<String, List<Map<String, Object>>> condMap = new HashMap<>();
        condMap.put("$and", andArray);
        return staffDao.searchStaffList(condMap, searchStaffRequest);
    }

    public Pager<Map<String, Object>> searchStaffList(SearchStaffRequest searchStaffRequest, Long enterpriseId) {
        if (searchStaffRequest.getOrgIdFilter() == null && searchStaffRequest.getStaffIdFilter() == null)
            return new Pager<>();

        Map<String, List<Map<String, Object>>> condMap = new HashMap<>();
        List<Map<String, Object>> andArray = createSearchCondition(searchStaffRequest, enterpriseId);
        if (searchStaffRequest.getOrgIdFilter() != null && searchStaffRequest.getOrgIdFilter() > 0) {
            Long orgId = searchStaffRequest.getOrgIdFilter();
            List<Long> staffIds = staffOrgMappingDao.findStaffIdListByOrg(orgId);
            if (CollectionUtils.isEmpty(staffIds)) {
                return new Pager<>();
            } else {
                andArray.add(in("_id", staffIds).toMap());
            }
        } else if (searchStaffRequest.getStaffIdFilter() != null && searchStaffRequest.getStaffIdFilter() > 0) {
            andArray.add(eq("_id", searchStaffRequest.getStaffIdFilter()).toMap());
        }
        condMap.put("$and", andArray);
        return staffDao.searchStaffList(condMap, searchStaffRequest);
    }

    private List<Map<String, Object>> createSearchCondition(final SearchStaffRequest searchStaffRequest, final Long enterpriseId) {
        List<Map<String, Object>> andArray = new ArrayList<>();
        andArray.add(eq("enterpriseId", enterpriseId).toMap());

        if (!StringUtil.isStrEmpty(searchStaffRequest.getSearchKey())) {
            String queryPattern = ".*" + searchStaffRequest.getSearchKey() + ".*";
            andArray.add(or(regex("username", queryPattern), regex("name", queryPattern)).toMap());
        }
        if (searchStaffRequest.getInitStatusList() == null) {
            andArray.add(lt("status", 0).toMap());
        } else {
            andArray.add(in("status", searchStaffRequest.getInitStatusList()).toMap());
        }
        if (searchStaffRequest.getSelectedStatus() != null) {
            if (searchStaffRequest.getSelectedStatus().equals(0)) {
                andArray.add(lt("status", 0).toMap());
            } else {
                andArray.add(eq("status", searchStaffRequest.getSelectedStatus()).toMap());
            }
        }
        return andArray;
    }

    public long countStaffList(QueryStaffRequest searchStaffRequest, Long enterpriseId) {
        return staffDao.countStaffList(searchStaffRequest, enterpriseId);
    }

}
