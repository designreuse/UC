package com.yealink.uc.web.modules.stafforgmapping.dao;

import com.yealink.dataservice.client.IRemoteDataService;
import com.yealink.dataservice.client.RemoteServiceFactory;
import com.yealink.dataservice.client.util.OrderBy;
import com.yealink.dataservice.client.util.Pager;
import com.yealink.uc.platform.utils.DataConverter;
import com.yealink.uc.platform.utils.EntityUtil;
import com.yealink.uc.common.modules.stafforgmapping.entity.StaffOrgMapping;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Transformer;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import static com.yealink.dataservice.client.util.Filter.*;

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

    public Long batchSave(List<StaffOrgMapping> stafforgMappings) {
        return remoteDataService.insertMany(STAFF_ORG_MAPPING_ENTITY_NAME, stafforgMappings);
    }

    public long update(StaffOrgMapping staffOrgMapping) {
        return remoteDataService.updateOne(STAFF_ORG_MAPPING_ENTITY_NAME, staffOrgMapping.get_id(), staffOrgMapping);
    }

    public long updateByMappingId(String mappingId, Map<String, Object> updatedMap) {
        return remoteDataService.updateOne(STAFF_ORG_MAPPING_ENTITY_NAME, mappingId, updatedMap);
    }

    public long updateByOrg(Long orgId, Map<String, Object> updatedMap) {
        return remoteDataService.updateMany(STAFF_ORG_MAPPING_ENTITY_NAME, eq("orgId", orgId).toMap(), updatedMap);
    }

    public List<StaffOrgMapping> findByOrg(Long orgId) {
        Pager pager = new Pager();
        pager.setLimit(Integer.MAX_VALUE);
        pager.setOrderbys(Arrays.asList(new OrderBy("staffIndexInOrg", OrderBy.ASC)));
        Pager<Map<String, Object>> staffOrgMappingMapList = remoteDataService.query(STAFF_ORG_MAPPING_ENTITY_NAME, null, pager, eq("orgId", orgId).toMap());
        if (CollectionUtils.isEmpty(staffOrgMappingMapList.getData())) return null;
        return mapConvertToStafforgMappings(staffOrgMappingMapList.getData());
    }

    public List<Long> findOrgIdsByStaffIds(List<Long> staffIds) {
        List<Map<String, Object>> resutl = remoteDataService.query(STAFF_ORG_MAPPING_ENTITY_NAME, "orgId", in("staffId", staffIds).toMap());
        return (List<Long>) CollectionUtils.collect(resutl, new Transformer() {
            @Override
            public Long transform(Object input) {
                return (Long) ((Map<String, Object>) input).get("orgId");
            }
        });
    }

    public List<Long> findStaffIdListByOrg(Long orgId) {
        Pager pager = new Pager();
        pager.setLimit(Integer.MAX_VALUE);
        pager.setOrderbys(Arrays.asList(new OrderBy("staffIndexInOrg", OrderBy.ASC)));
        Pager<Map<String, Object>> orgMapList = remoteDataService.query(STAFF_ORG_MAPPING_ENTITY_NAME, null, pager, eq("orgId", orgId).toMap());
        if (CollectionUtils.isEmpty(orgMapList.getData())) return null;
        return mapConvertToStaffIdList(orgMapList.getData());
    }

    public List<StaffOrgMapping> findByOrgList(List<Long> orgIdList) {
        Pager pager = new Pager();
        pager.setLimit(Integer.MAX_VALUE);
        pager.setOrderbys(Arrays.asList(new OrderBy("staffIndexInOrg", OrderBy.ASC)));
        Pager<Map<String, Object>> mapList = remoteDataService.query(STAFF_ORG_MAPPING_ENTITY_NAME, null, pager, in("orgId", orgIdList).toMap());
        if (CollectionUtils.isEmpty(mapList.getData())) return null;
        return mapConvertToStafforgMappings(mapList.getData());
    }

    public List<StaffOrgMapping> findByStaff(Long staffId) {
        List<Map<String, Object>> orgMapList = remoteDataService.query(STAFF_ORG_MAPPING_ENTITY_NAME, null, eq("staffId", staffId).toMap());
        if (CollectionUtils.isEmpty(orgMapList)) return null;
        return mapConvertToStafforgMappings(orgMapList);
    }

    public List<StaffOrgMapping> findByStaffList(List<Long> staffIdList) {
        List<Map<String,Object>> maps = remoteDataService.query(STAFF_ORG_MAPPING_ENTITY_NAME, null, in("staffId", staffIdList).toMap());
        return mapConvertToStafforgMappings(maps);
    }

    public Long findCountByOrg(Long orgId) {
        return remoteDataService.queryCount(STAFF_ORG_MAPPING_ENTITY_NAME, eq("orgId", orgId).toMap());
    }


    public long deleteByOrgStaffTitle(Long orgId, Long staffId, String title) {
        return remoteDataService.deleteMany(STAFF_ORG_MAPPING_ENTITY_NAME, and(eq("orgId", orgId), eq("staffId", staffId), eq("title", title)).toMap());
    }

    public long deleteByOrg(Long orgId) {
        return remoteDataService.deleteMany(STAFF_ORG_MAPPING_ENTITY_NAME, eq("orgId", orgId).toMap());
    }

    public long deleteByStaffOrg(Long staffId, Long orgId) {
        return remoteDataService.deleteMany(STAFF_ORG_MAPPING_ENTITY_NAME, and(eq("orgId", orgId), eq("staffId", staffId)).toMap());
    }

    public long deleteByStaff(Long staffId) {
        return remoteDataService.deleteMany(STAFF_ORG_MAPPING_ENTITY_NAME, eq("staffId", staffId).toMap());
    }

    public long deleteByStaffIdList(List<Long> staffIdList) {
        return remoteDataService.deleteMany(STAFF_ORG_MAPPING_ENTITY_NAME, in("staffId", staffIdList).toMap());
    }

    public int nextIndexInOrg(Long orgId) {
        Pager pager = new Pager();
        pager.setLimit(1);
        pager.setOrderbys(Arrays.asList(new OrderBy("staffIndexInOrg", -1)));
        Pager<Map<String, Object>> mapPager = remoteDataService.query(STAFF_ORG_MAPPING_ENTITY_NAME, "staffIndexInOrg", pager, eq("orgId", orgId).toMap());
        if (mapPager.getData().isEmpty()) {
            return 1;
        }
        return (int) mapPager.getData().get(0).get("staffIndexInOrg") + 1;
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

    private List<Long> mapConvertToStaffIdList(List<Map<String, Object>> staffOrgMappingMapList) {
        Collection staffIdList = CollectionUtils.collect(staffOrgMappingMapList, new Transformer() {
            @Override
            public Object transform(Object input) {
                return ((Map<String, Object>) input).get("staffId");
            }
        });
        return (List<Long>) staffIdList;
    }
}
