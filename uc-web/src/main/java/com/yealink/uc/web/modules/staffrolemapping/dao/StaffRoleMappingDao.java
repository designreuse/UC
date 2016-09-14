package com.yealink.uc.web.modules.staffrolemapping.dao;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.yealink.dataservice.client.IRemoteDataService;
import com.yealink.dataservice.client.RemoteServiceFactory;
import com.yealink.uc.common.modules.staffrolemapping.StaffRoleMapping;
import com.yealink.uc.platform.utils.DataConverter;
import com.yealink.uc.platform.utils.EntityUtil;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Transformer;
import org.springframework.stereotype.Repository;

import static com.yealink.dataservice.client.util.Filter.eq;

/**
 * @author ChNan
 */
@Repository
@SuppressWarnings("unchecked")
public class StaffRoleMappingDao {
    private IRemoteDataService remoteDataService = RemoteServiceFactory.getInstance().getRemoteDataService();
    private static String STAFF_ROLE_MAPPING_ENTITY_NAME = EntityUtil.getEntityName(StaffRoleMapping.class);

    public long save(StaffRoleMapping staffRoleMapping) {
        return remoteDataService.insertOne(STAFF_ROLE_MAPPING_ENTITY_NAME, staffRoleMapping);
    }

    public long update(StaffRoleMapping staffRoleMapping) {
        return remoteDataService.updateOne(STAFF_ROLE_MAPPING_ENTITY_NAME, staffRoleMapping.get_id(), staffRoleMapping);
    }

    public long delete(Long staffRoleMappingId) {
        return remoteDataService.deleteMany(STAFF_ROLE_MAPPING_ENTITY_NAME, eq("_id", staffRoleMappingId).toMap());
    }

    public List<StaffRoleMapping> findByStaff(Long staffId) {
        List<Map<String, Object>> staffRoleMappingMapList = remoteDataService.query(STAFF_ROLE_MAPPING_ENTITY_NAME, null, eq("staffId", staffId).toMap());
        if (CollectionUtils.isEmpty(staffRoleMappingMapList)) return null;
        return mapConvertToOrgList(staffRoleMappingMapList);
    }

    public List<Long> findRoleIds(Long staffId) {
        List<Map<String, Object>> idMapList = remoteDataService.query(STAFF_ROLE_MAPPING_ENTITY_NAME, "roleId", eq("staffId", staffId).toMap());
        if (CollectionUtils.isEmpty(idMapList)) return null;
        return (List<Long>) CollectionUtils.collect(idMapList, new Transformer() {
            @Override
            public Long transform(Object input) {
                Number result = ((Map<String, Long>) input).get("roleId");
                return result.longValue();
            }
        });
    }

    public long count(Long roleId) {
        return remoteDataService.queryCount(STAFF_ROLE_MAPPING_ENTITY_NAME, eq("roleId", roleId).toMap());
    }

    private List<StaffRoleMapping> mapConvertToOrgList(List<Map<String, Object>> staffRoleMappingMapList) {
        Collection staffRoleMappingList = CollectionUtils.collect(staffRoleMappingMapList, new Transformer() {
            @Override
            public Object transform(Object input) {
                return DataConverter.copyFromMap(new StaffRoleMapping(), (Map<String, Object>) input);
            }
        });
        return (List<StaffRoleMapping>) staffRoleMappingList;
    }
}
