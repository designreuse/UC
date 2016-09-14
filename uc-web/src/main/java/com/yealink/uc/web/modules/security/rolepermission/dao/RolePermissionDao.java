package com.yealink.uc.web.modules.security.rolepermission.dao;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.yealink.dataservice.client.IRemoteDataService;
import com.yealink.dataservice.client.RemoteServiceFactory;
import com.yealink.uc.common.modules.security.rolepermission.entity.RolePermission;
import com.yealink.uc.platform.utils.DataConverter;
import com.yealink.uc.platform.utils.EntityUtil;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Transformer;
import org.springframework.stereotype.Repository;

import static com.yealink.dataservice.client.util.Filter.and;
import static com.yealink.dataservice.client.util.Filter.eq;
import static com.yealink.dataservice.client.util.Filter.in;

/**
 * @author ChNan
 */
@Repository
@SuppressWarnings("unchecked")
public class RolePermissionDao {
    private IRemoteDataService remoteDataService = RemoteServiceFactory.getInstance().getRemoteDataService();
    private static String ROLE_PERMISSION_ENTITY_NAME = EntityUtil.getEntityName(RolePermission.class);

    public long save(RolePermission rolePermission) {
        return remoteDataService.insertOne(ROLE_PERMISSION_ENTITY_NAME, rolePermission);
    }

    public long update(RolePermission rolePermission) {
        return remoteDataService.updateOne(ROLE_PERMISSION_ENTITY_NAME, rolePermission.get_id(), rolePermission);
    }

    public long delete(Long rolePermissionId) {
        return remoteDataService.deleteMany(ROLE_PERMISSION_ENTITY_NAME, eq("_id", rolePermissionId).toMap());
    }

    public List<RolePermission> find(Long roleId) {
        List<Map<String, Object>> rolePermissionMapList = remoteDataService.query(ROLE_PERMISSION_ENTITY_NAME, null, eq("roleId", roleId).toMap());
        if (CollectionUtils.isEmpty(rolePermissionMapList)) return null;
        return mapConvertToList(rolePermissionMapList);
    }

    public List<RolePermission> find(Long roleId, Long projectId) {
        List<Map<String, Object>> rolePermissionMapList = remoteDataService.query(ROLE_PERMISSION_ENTITY_NAME, null, and(eq("roleId", roleId), eq("projectId", projectId)).toMap());
        if (CollectionUtils.isEmpty(rolePermissionMapList)) return null;
        return mapConvertToList(rolePermissionMapList);
    }

    public List<RolePermission> findByRoleIdList(List<Long> roleIdList) {
        List<Map<String, Object>> rolePermissionMapList = remoteDataService.query(ROLE_PERMISSION_ENTITY_NAME, null, in("roleId", roleIdList).toMap());
        if (CollectionUtils.isEmpty(rolePermissionMapList)) return null;
        return mapConvertToList(rolePermissionMapList);
    }

    public long batchCreateRolePermission(List<RolePermission> rolePermissionList) {
        if (CollectionUtils.isEmpty(rolePermissionList)) {
            return 0;
        }
        return remoteDataService.insertMany(ROLE_PERMISSION_ENTITY_NAME, rolePermissionList);
    }

    public long batchDeleteByMenuRole(List<Long> menuIds, Long roleId) {
        if (CollectionUtils.isEmpty(menuIds)) {
            return 0;
        }
        return remoteDataService.deleteMany(ROLE_PERMISSION_ENTITY_NAME, and(eq("roleId", roleId), in("menuId", menuIds)).toMap());
    }

    public long batchDeleteByIdList(List<Long> permissionIdList) {
        return remoteDataService.deleteMany(ROLE_PERMISSION_ENTITY_NAME, in("_id", permissionIdList).toMap());
    }

    public long countByOperation(Long resourceOperationId) {
        return remoteDataService.queryCount(ROLE_PERMISSION_ENTITY_NAME, eq("resourceOperationId", resourceOperationId).toMap());
    }

    public long countByMenu(Long menuId) {
        return remoteDataService.queryCount(ROLE_PERMISSION_ENTITY_NAME, eq("menuId", menuId).toMap());
    }

    public long countByResource(Long resourceId) {
        return remoteDataService.queryCount(ROLE_PERMISSION_ENTITY_NAME, eq("resourceId", resourceId).toMap());
    }

    private List<RolePermission> mapConvertToList(List<Map<String, Object>> rolePermissionMapList) {
        Collection rolePermissionList = CollectionUtils.collect(rolePermissionMapList, new Transformer() {
            @Override
            public Object transform(Object input) {
                return DataConverter.copyFromMap(new RolePermission(), (Map<String, Object>) input);
            }
        });
        return (List<RolePermission>) rolePermissionList;
    }
}
