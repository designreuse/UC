package com.yealink.uc.service.modules.stafforgmapping.dao;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.yealink.dataservice.client.IRemoteDataService;
import com.yealink.dataservice.client.RemoteServiceFactory;
import com.yealink.dataservice.client.util.OrderBy;
import com.yealink.dataservice.client.util.Pager;
import com.yealink.uc.common.modules.stafforgmapping.entity.StaffOrgMapping;
import com.yealink.uc.platform.utils.DataConverter;
import com.yealink.uc.platform.utils.EntityUtil;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Transformer;
import org.springframework.stereotype.Repository;

import static com.yealink.dataservice.client.util.Filter.in;

/**
 * @author ChNan
 */
@Repository
@SuppressWarnings("unchecked")
public class StaffOrgMappingDao {
    private IRemoteDataService remoteDataService = RemoteServiceFactory.getInstance().getRemoteDataService();
    private static String STAFF_ORG_MAPPING_ENTITY_NAME = EntityUtil.getEntityName(StaffOrgMapping.class);


    public Long save(StaffOrgMapping staffOrgMapping) {
        return remoteDataService.insertOne(STAFF_ORG_MAPPING_ENTITY_NAME, staffOrgMapping);
    }


    public List<StaffOrgMapping> findByOrgList(List<Long> orgIdList) {
        Pager pager = new Pager();
        pager.setLimit(Integer.MAX_VALUE);
        pager.setOrderbys(Arrays.asList(new OrderBy("staffIndexInOrg", OrderBy.ASC)));
        Pager<Map<String, Object>> mapList = remoteDataService.query(STAFF_ORG_MAPPING_ENTITY_NAME, null, pager, in("orgId", orgIdList).toMap());
        if (CollectionUtils.isEmpty(mapList.getData())) return null;
        return mapConvertToStafforgMappings(mapList.getData());
    }
    public List<StaffOrgMapping> findByStaffIds(List<Long> staffIds) {
        Pager pager = new Pager();
        pager.setLimit(Integer.MAX_VALUE);
        pager.setOrderbys(Arrays.asList(new OrderBy("staffIndexInOrg", OrderBy.ASC)));
        Pager<Map<String, Object>> mapList = remoteDataService.query(STAFF_ORG_MAPPING_ENTITY_NAME, null, pager, in("staffId", staffIds).toMap());
        if (CollectionUtils.isEmpty(mapList.getData())) return null;
        return mapConvertToStafforgMappings(mapList.getData());
    }

    private List<StaffOrgMapping> mapConvertToStafforgMappings(List<Map<String, Object>> staffOrgMappingMapList) {
        Collection stafforgMappings = CollectionUtils.collect(staffOrgMappingMapList, new Transformer() {
            @Override
            public Object transform(Object input) {
                return DataConverter.copyFromMap(new StaffOrgMapping(), (Map<String, Object>) input);
            }
        });
        return (List<StaffOrgMapping>) stafforgMappings;
    }
}
