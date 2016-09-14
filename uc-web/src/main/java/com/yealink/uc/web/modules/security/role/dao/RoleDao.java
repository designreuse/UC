package com.yealink.uc.web.modules.security.role.dao;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.yealink.dataservice.client.IRemoteDataService;
import com.yealink.dataservice.client.RemoteServiceFactory;
import com.yealink.uc.common.modules.security.role.entity.Role;
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
@SuppressWarnings("unchecked")
public class RoleDao {
    private IRemoteDataService remoteDataService = RemoteServiceFactory.getInstance().getRemoteDataService();
    private static String PROJECT_ENTITY_NAME = EntityUtil.getEntityName(Role.class);

    public long save(Role role) {
        return remoteDataService.insertOne(PROJECT_ENTITY_NAME, role);
    }

    public long update(Role role) {
        return remoteDataService.updateOne(PROJECT_ENTITY_NAME, role.get_id(), role);
    }

    public long delete(Long roleId, Long enterpriseId) {
        return remoteDataService.deleteMany(PROJECT_ENTITY_NAME, and(eq("_id", roleId), eq("platformEnterpriseId", enterpriseId)).toMap());
    }

    public Role get(Long roleId, Long enterpriseId) {
        Map<String, Object> roleMap = remoteDataService.queryOne(PROJECT_ENTITY_NAME, null, and(eq("_id", roleId), eq("platformEnterpriseId", enterpriseId)).toMap());
        if (roleMap == null) return null;
        return DataConverter.copyFromMap(new Role(), roleMap);
    }

    public boolean checkNameExist(String name, Long enterpriseId) {
        return remoteDataService.queryCount(PROJECT_ENTITY_NAME, and(eq("name", name), eq("platformEnterpriseId", enterpriseId)).toMap()) > 0;
    }

    public long count(Long roleLevelId, Long enterpriseId) {
        return remoteDataService.queryCount(PROJECT_ENTITY_NAME, and(eq("roleLevelId", roleLevelId), eq("platformEnterpriseId", enterpriseId)).toMap());
    }

    public boolean checkNameExistExceptItSelf(String name, Long enterpriseId, Long roleId) {
        return remoteDataService.queryCount(PROJECT_ENTITY_NAME, and(eq("name", name), eq("platformEnterpriseId", enterpriseId), ne("_id", roleId)).toMap()) > 0;
    }

    public List<Role> findByEnterprise(Long platformEnterpriseId) {
        List<Map<String, Object>> roleMapList = remoteDataService.query(PROJECT_ENTITY_NAME, null, eq("platformEnterpriseId", platformEnterpriseId).toMap());
        if (CollectionUtils.isEmpty(roleMapList)) return null;
        return mapConvertToList(roleMapList);
    }

    public List<Long> findIdsByEnterprise(Long platformEnterpriseId) {
        List<Map<String, Object>> idMapList = remoteDataService.query(PROJECT_ENTITY_NAME, null, eq("enterpriseId", platformEnterpriseId).toMap());
        if (CollectionUtils.isEmpty(idMapList)) return null;
        return (List<Long>) CollectionUtils.collect(idMapList, new Transformer() {
            @Override
            public Long transform(Object input) {
                Number result = ((Map<String, Long>) input).get("_id");
                return result.longValue();
            }
        });
    }

    private List<Role> mapConvertToList(List<Map<String, Object>> roleMapList) {
        Collection roleList = CollectionUtils.collect(roleMapList, new Transformer() {
            @Override
            public Object transform(Object input) {
                return DataConverter.copyFromMap(new Role(), (Map<String, Object>) input);
            }
        });
        return (List<Role>) roleList;
    }
}
