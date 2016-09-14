package com.yealink.uc.web.modules.staff.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.yealink.dataservice.client.util.Pager;
import com.yealink.uc.common.modules.businessaccount.entity.BusinessAccount;
import com.yealink.uc.common.modules.businessaccount.vo.BusinessType;
import com.yealink.uc.common.modules.org.entity.Org;
import com.yealink.uc.common.modules.org.vo.OrgTreeNode;
import com.yealink.uc.common.modules.staff.entity.Staff;
import com.yealink.uc.common.modules.stafforgmapping.entity.StaffOrgMapping;
import com.yealink.uc.platform.response.PageModel;
import com.yealink.uc.platform.utils.DataConverter;
import com.yealink.uc.web.modules.businessaccount.BusinessAccountService;
import com.yealink.uc.web.modules.org.service.OrgService;
import com.yealink.uc.web.modules.org.service.QueryOrgService;
import com.yealink.uc.web.modules.staff.dao.StaffDao;
import com.yealink.uc.web.modules.staff.request.SearchStaffRequest;
import com.yealink.uc.web.modules.staff.response.SearchStaffResponse;
import com.yealink.uc.web.modules.staff.response.StaffExtension;
import com.yealink.uc.web.modules.staff.vo.StaffSearchInfo;
import com.yealink.uc.web.modules.stafforgmapping.service.StaffOrgMappingService;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author ChNan
 */
@Service
public class SearchStaffService {
    @Autowired
    private StaffOrgMappingService staffOrgMappingService;
    @Autowired
    private QueryStaffService queryStaffService;
    @Autowired
    private QueryOrgService queryOrgService;
    @Autowired
    BusinessAccountService businessAccountService;
    @Autowired
    StaffDao staffDao;
    @Autowired
    OrgService orgService;

    public SearchStaffResponse searchStaffList(SearchStaffRequest searchStaffRequest, Long enterpriseId) {
        SearchStaffResponse response = new SearchStaffResponse();
        List<StaffSearchInfo> staffSearchInfos = new ArrayList<>();
        if (searchStaffRequest.getOrgIdFilter() != null && searchStaffRequest.getOrgIdFilter().equals(OrgTreeNode.FORBIDDEN_ORG_ID)) {
            Pager<Map<String, Object>> staffsPager = queryStaffService.searchForbiddenStaffList(searchStaffRequest, enterpriseId);
            PageModel<StaffSearchInfo> staffOrgInfoPageModel = PageModel.createPageModel(searchStaffRequest.getPageNo(), searchStaffRequest.getPageSize(),
                staffsPager.getTotal(), null);
            final List<Staff> staffs = convertToStaffs(staffsPager.getData());
            if (staffs == null) {
                response.setStaffPageModel(new PageModel());
                return response;
            }
            final Map<Long, BusinessAccount> staffIdAccountMap = createStaffIdExtensionAccountMap(staffs);
            staffSearchInfos = Lists.transform(staffs, new Function<Staff, StaffSearchInfo>() {
                @Override
                public StaffSearchInfo apply(final Staff input) {
                    StaffExtension extension = createStaffExtensionInfo(input.get_id(), staffIdAccountMap);
                    return new StaffSearchInfo(input, orgService.createForbiddenOrg(), extension);
                }
            });
            staffOrgInfoPageModel.setRecords(staffSearchInfos);
            response.setStaffPageModel(staffOrgInfoPageModel);
        } else {
            Pager<Map<String, Object>> staffsPager = queryStaffService.searchStaffList(searchStaffRequest, enterpriseId);
            PageModel<StaffSearchInfo> staffOrgInfoPageModel = PageModel.createPageModel(searchStaffRequest.getPageNo(), searchStaffRequest.getPageSize(),
                staffsPager.getTotal(), null);
            final List<Staff> staffs = convertToStaffs(staffsPager.getData());
            if (staffs == null) {
                response.setStaffPageModel(staffOrgInfoPageModel);
                return response;
            }
            final Map<Long, BusinessAccount> staffIdAccountMap = createStaffIdExtensionAccountMap(staffs);
            if (searchStaffRequest.getOrgIdFilter() != null && searchStaffRequest.getOrgIdFilter() > 0) {
                staffSearchInfos = constructByOneOrg(searchStaffRequest, enterpriseId, staffs, staffIdAccountMap);
            } else if (searchStaffRequest.getStaffIdFilter() != null && searchStaffRequest.getStaffIdFilter() > 0) { // in this case staffs is only one.
                staffSearchInfos = constructByOneStaff(searchStaffRequest, enterpriseId, staffs, staffIdAccountMap);
            }
            staffOrgInfoPageModel.setRecords(staffSearchInfos);
            response.setStaffPageModel(staffOrgInfoPageModel);
        }
        return response;
    }

    private Map<Long, BusinessAccount> createStaffIdExtensionAccountMap(final List<Staff> staffs) {
        List<Long> staffIds = Lists.transform(staffs, new Function<Staff, Long>() {
            @Override
            public Long apply(final Staff input) {
                return input.get_id();
            }
        });
        List<BusinessAccount> extensionAccounts = businessAccountService.findByStaffIdsAndType(staffIds, BusinessType.EXTENSION.getCode());
        return Maps.uniqueIndex(extensionAccounts, new Function<BusinessAccount, Long>() {
            @Override
            public Long apply(final BusinessAccount input) {
                return input.getStaffId();
            }
        });
    }

    private List<StaffSearchInfo> constructByOneStaff(final SearchStaffRequest searchStaffRequest, final Long enterpriseId, final List<Staff> staffs, final Map<Long, BusinessAccount> staffIdAccountMap) {
        final List<StaffSearchInfo> staffSearchInfos;
        List<StaffOrgMapping> mappings = staffOrgMappingService.findByStaff(searchStaffRequest.getStaffIdFilter(), enterpriseId);
        List<Long> orgIds = Lists.transform(mappings, new Function<StaffOrgMapping, Long>() {
            @Override
            public Long apply(final StaffOrgMapping input) {
                return input.getOrgId();
            }
        });
        List<Org> orgs = queryOrgService.findByIds(orgIds, enterpriseId);
        staffSearchInfos = Lists.transform(orgs, new Function<Org, StaffSearchInfo>() {
            @Override
            public StaffSearchInfo apply(final Org input) {
                StaffExtension extension = createStaffExtensionInfo(staffs.get(0).get_id(), staffIdAccountMap);
                return new StaffSearchInfo(staffs.get(0), input, extension);
            }
        });
        return staffSearchInfos;
    }

    private List<StaffSearchInfo> constructByOneOrg(final SearchStaffRequest searchStaffRequest, final Long enterpriseId, final List<Staff> staffs, final Map<Long, BusinessAccount> staffIdAccountMap) {
        final List<StaffSearchInfo> staffSearchInfos;
        final Org org = queryOrgService.getOrg(searchStaffRequest.getOrgIdFilter(), enterpriseId);
        staffSearchInfos = Lists.transform(staffs, new Function<Staff, StaffSearchInfo>() {
            @Override
            public StaffSearchInfo apply(final Staff input) {
                StaffExtension extension = createStaffExtensionInfo(input.get_id(), staffIdAccountMap);
                return new StaffSearchInfo(input, org, extension);
            }
        });
        return staffSearchInfos;
    }

    private StaffExtension createStaffExtensionInfo(final Long staffId, final Map<Long, BusinessAccount> staffIdAccountMap) {
        BusinessAccount extensionAccount = staffIdAccountMap.get(staffId);
        StaffExtension extension = new StaffExtension();
        if (extensionAccount != null) {
            extension = new StaffExtension(extensionAccount.getUsername(), extensionAccount.getEncryptedPassword(),
                extensionAccount.getPinCode(), extensionAccount.getStatus());
        }
        return extension;
    }

    public List<Staff> convertToStaffs(List<Map<String, Object>> staffMapList) {
        if (CollectionUtils.isEmpty(staffMapList)) return null;

        return Lists.transform(staffMapList, new Function<Map<String, Object>, Staff>() {
            @Override
            public Staff apply(final Map<String, Object> input) {
                return DataConverter.copyFromMap(new Staff(), input);
            }
        });
    }

}
