package com.yealink.uc.web.modules.security.rolelevel.dao;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.yealink.dataservice.client.IRemoteDataService;
import com.yealink.dataservice.client.RemoteServiceFactory;
import com.yealink.uc.common.modules.security.role.RoleLevel;
import com.yealink.uc.platform.utils.DataConverter;
import com.yealink.uc.platform.utils.EntityUtil;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Transformer;
import org.springframework.stereotype.Repository;

import static com.yealink.dataservice.client.util.Filter.and;
import static com.yealink.dataservice.client.util.Filter.eq;
import static com.yealink.dataservice.client.util.Filter.ne;

/**
 * @author ChNan
 */
@Repository
public class RoleLevelDao {
    private IRemoteDataService remoteDataService = RemoteServiceFactory.getInstance().getRemoteDataService();
    private static String OPERATION_ENTITY_NAME = EntityUtil.getEntityName(RoleLevel.class);

    public long save(RoleLevel roleLevel) {
        return remoteDataService.insertOne(OPERATION_ENTITY_NAME, roleLevel);
    }

    public long update(RoleLevel roleLevel) {
        return remoteDataService.updateOne(OPERATION_ENTITY_NAME, roleLevel.get_id(), roleLevel);
    }

    public long delete(Long roleLevelId, Long enterpriseId) {
        return remoteDataService.deleteMany(OPERATION_ENTITY_NAME, and(eq("_id", roleLevelId), eq("platformEnterpriseId", enterpriseId)).toMap());
    }

    public RoleLevel get(Long roleLevelId) {
        Map<String, Object> roleLevelMap = remoteDataService.queryOne(OPERATION_ENTITY_NAME, null, eq("_id", roleLevelId).toMap());
        if (roleLevelMap == null) return null;
        return DataConverter.copyFromMap(new RoleLevel(), roleLevelMap);
    }

    public RoleLevel get(Long roleLevelId, Long enterpriseId) {
        Map<String, Object> roleLevelMap = remoteDataService.queryOne(OPERATION_ENTITY_NAME, null, and(eq("_id", roleLevelId), eq("platformEnterpriseId", enterpriseId)).toMap());
        if (roleLevelMap == null) return null;
        return DataConverter.copyFromMap(new RoleLevel(), roleLevelMap);
    }

    public boolean checkNameExist(String name, Long enterpriseId) {
        return remoteDataService.queryCount(OPERATION_ENTITY_NAME, and(eq("name", name), eq("platformEnterpriseId", enterpriseId)).toMap()) > 0;
    }

    public boolean checkNameExceptItSelt(String name, Long enterpriseId, Long roleLevelId) {
        return remoteDataService.queryCount(OPERATION_ENTITY_NAME, and(eq("name", name), eq("platformEnterpriseId", enterpriseId), ne("_id", roleLevelId)).toMap()) > 0;
    }

    public List<RoleLevel> findByEnterprise(Long platformEnterpriseId) {
        List<Map<String, Object>> roleLevelMapList = remoteDataService.query(OPERATION_ENTITY_NAME, null, eq("platformEnterpriseId", platformEnterpriseId).toMap());
        if (CollectionUtils.isEmpty(roleLevelMapList)) return null;
        return mapConvertToList(roleLevelMapList);
    }

    public List<Long> findIdsByEnterprise(Long platformEnterpriseId) {
        List<Map<String, Object>> idMapList = remoteDataService.query(OPERATION_ENTITY_NAME, null, eq("platformEnterpriseId", platformEnterpriseId).toMap());
        if (CollectionUtils.isEmpty(idMapList)) return null;
        return (List<Long>) CollectionUtils.collect(idMapList, new Transformer() {
            @Override
            public Long transform(Object input) {
                Number result = ((Map<String, Long>) input).get("_id");
                return result.longValue();
            }
        });
    }

    private List<RoleLevel> mapConvertToList(List<Map<String, Object>> roleLevelMapList) {
        Collection roleLevelList = CollectionUtils.collect(roleLevelMapList, new Transformer() {
            @Override
            public Object transform(Object input) {
                return DataConverter.copyFromMap(new RoleLevel(), (Map<String, Object>) input);
            }
        });
        return (List<RoleLevel>) roleLevelList;
    }
}
