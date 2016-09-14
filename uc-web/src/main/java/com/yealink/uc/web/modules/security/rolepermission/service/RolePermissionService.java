package com.yealink.uc.web.modules.security.rolepermission.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.yealink.uc.common.modules.security.rolepermission.entity.RolePermission;
import com.yealink.uc.common.modules.security.rolepermission.vo.PermissionType;
import com.yealink.uc.platform.utils.EntityUtil;
import com.yealink.uc.web.modules.common.dao.IdGeneratorDao;
import com.yealink.uc.web.modules.security.rolepermission.dao.RolePermissionDao;
import com.yealink.uc.web.modules.security.rolepermission.request.EditRolePermissionRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.collections.Transformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author ChNan
 */
@Service
@SuppressWarnings("unchecked")
public class RolePermissionService {
    @Autowired
    RolePermissionDao rolePermissionDao;
    @Autowired
    IdGeneratorDao idGeneratorDao;

    public List<RolePermission> find(Long roleId) {
        return rolePermissionDao.find(roleId);
    }

    public List<RolePermission> find(Long roleId, Long projectId) {
        return rolePermissionDao.find(roleId, projectId);
    }

    public List<RolePermission> findByRoleIdList(List<Long> roleIdList) {
        return rolePermissionDao.findByRoleIdList(roleIdList);
    }

    public boolean authorize(EditRolePermissionRequest editRolePermissionRequest, Long enterpriseId) {
        List<RolePermission> rolePermissionList = rolePermissionDao.find(editRolePermissionRequest.getRoleId(), editRolePermissionRequest.getProjectId());
        if (CollectionUtils.isEmpty(rolePermissionList)) {
            createPermission(editRolePermissionRequest);
        } else {
            deleteOrCreatePermission(editRolePermissionRequest, rolePermissionList);
        }
        return true;
    }


    private void createPermission(EditRolePermissionRequest editRolePermissionRequest) {
        List<RolePermission> rolePermissionList = new ArrayList<>();
        if (editRolePermissionRequest.getType().equals(PermissionType.MENU.getCode())) {
            for (Long menuId : editRolePermissionRequest.getMenuIdList()) {
                rolePermissionList.add(createMenuRolePermission(editRolePermissionRequest, menuId));
            }
        } else if (editRolePermissionRequest.getType().equals(PermissionType.RESOURCE_OPERATION.getCode())) {
            fillRolePermission(editRolePermissionRequest, rolePermissionList);
        }
        rolePermissionDao.batchCreateRolePermission(rolePermissionList);
    }


    private void deleteOrCreatePermission(EditRolePermissionRequest editRolePermissionRequest, List<RolePermission> rolePermissionList) {
        if (editRolePermissionRequest.getType().equals(PermissionType.MENU.getCode())) {
            handlerMenuPermission(editRolePermissionRequest, rolePermissionList);
        } else if (editRolePermissionRequest.getType().equals(PermissionType.RESOURCE_OPERATION.getCode())) {
            handlerResourcePermission(editRolePermissionRequest, rolePermissionList);
        }

    }

    private void fillRolePermission(EditRolePermissionRequest editRolePermissionRequest, List<RolePermission> rolePermissionList) {
        for (Long resourceOperationId : editRolePermissionRequest.getResourceOperationIdList()) {
            RolePermission rolePermission = createResourcePermission(editRolePermissionRequest, resourceOperationId);
            rolePermissionList.add(rolePermission);
        }
    }


    private RolePermission createMenuRolePermission(final EditRolePermissionRequest editRolePermissionRequest, final Long menuId) {
        RolePermission rolePermission = new RolePermission(editRolePermissionRequest.getRoleId(), editRolePermissionRequest.getType(), editRolePermissionRequest.getProjectId());
        rolePermission.set_id(idGeneratorDao.nextId(EntityUtil.getEntityName(RolePermission.class)));
        rolePermission.setMenuId(menuId);
        return rolePermission;
    }

    private void handlerResourcePermission(final EditRolePermissionRequest editRolePermissionRequest, final List<RolePermission> rolePermissionList) {
        List<RolePermission> existResourcePermissions = extractPermissions(rolePermissionList, PermissionType.RESOURCE_OPERATION.getCode());
        List<Long> permissionIdsNeedDelete = extractResourceOperationIds(existResourcePermissions);
        if (CollectionUtils.isEmpty(editRolePermissionRequest.getResourceOperationIdList())) { //todo delete all
            rolePermissionDao.batchDeleteByIdList(permissionIdsNeedDelete);
        } else {
            List<RolePermission> resourcePermissionNeedCreated = new ArrayList<>();
            List<Long> resourcePermissionSelected = new ArrayList<>();
            Map<Long, RolePermission> resourcePermissionMap = Maps.uniqueIndex(existResourcePermissions.iterator(), new Function<RolePermission, Long>() {
                @Override
                public Long apply(final RolePermission input) {
                    return input.getResourceOperationId();
                }
            });
            for (Long resourceOperationId : editRolePermissionRequest.getResourceOperationIdList()) {
                RolePermission existResourcePermission = resourcePermissionMap.get(resourceOperationId);
                if (existResourcePermission == null) {
                    RolePermission rolePermission = createResourcePermission(editRolePermissionRequest, resourceOperationId);
                    resourcePermissionNeedCreated.add(rolePermission);
                } else {
                    resourcePermissionSelected.add(existResourcePermission.get_id());
                }
            }
            permissionIdsNeedDelete.removeAll(resourcePermissionSelected);
            rolePermissionDao.batchCreateRolePermission(resourcePermissionNeedCreated);
            rolePermissionDao.batchDeleteByIdList(permissionIdsNeedDelete);
        }
    }

    private List<Long> extractResourceOperationIds(final List<RolePermission> existResourcePermissions) {
        return (List<Long>) CollectionUtils.collect(existResourcePermissions, new Transformer() {
            @Override
            public Object transform(Object input) {
                return ((RolePermission) input).get_id();
            }
        });
    }

    private void handlerMenuPermission(final EditRolePermissionRequest editRolePermissionRequest, final List<RolePermission> rolePermissionList) {
        List<RolePermission> existMenuPermissions = extractPermissions(rolePermissionList, PermissionType.MENU.getCode());
        List<Long> existMenuIds = extractMenuIds(existMenuPermissions);
        Map<Long, RolePermission> menuIdPermissionMap = createMenuIdPermissionMap(existMenuPermissions);
        List<RolePermission> menuPermissionsNeedCreated = new ArrayList<>();
        List<Long> menuPermissionIdsNeedDelete = Lists.newArrayList();
        if (CollectionUtils.isEmpty(editRolePermissionRequest.getMenuIdList())) { //todo delete all
            menuPermissionIdsNeedDelete.addAll(existMenuIds);
        } else {
            for (Long menuId : editRolePermissionRequest.getMenuIdList()) {
                if (menuIdPermissionMap.get(menuId) == null) {
                    RolePermission rolePermission = createMenuRolePermission(editRolePermissionRequest, menuId);
                    menuPermissionsNeedCreated.add(rolePermission);
                }
            }
            existMenuIds.removeAll(editRolePermissionRequest.getMenuIdList());
            menuPermissionIdsNeedDelete.addAll(existMenuIds);
        }
        rolePermissionDao.batchCreateRolePermission(menuPermissionsNeedCreated);
        rolePermissionDao.batchDeleteByMenuRole(menuPermissionIdsNeedDelete, editRolePermissionRequest.getRoleId());
    }

    private Map<Long, RolePermission> createMenuIdPermissionMap(final List<RolePermission> menuPermissions) {
        return Maps.uniqueIndex(menuPermissions.iterator(), new Function<RolePermission, Long>() {
            @Override
            public Long apply(RolePermission input) {
                return input.getMenuId();
            }
        });
    }

    public List<Long> extractMenuIds(final List<RolePermission> menuPermissions) {
        return Lists.transform(menuPermissions, new Function<RolePermission, Long>() {
            @Override
            public Long apply(RolePermission input) {
                return input.getMenuId();
            }
        });
    }

    public List<RolePermission> extractPermissions(final List<RolePermission> rolePermissionList, final String type) {
        return (List<RolePermission>) CollectionUtils.select(rolePermissionList, new Predicate() {
            @Override
            public boolean evaluate(Object object) {
                return ((RolePermission) object).getType().equals(type);
            }
        });
    }

    private RolePermission createResourcePermission(final EditRolePermissionRequest editRolePermissionRequest, Long resourceOperationId) {
        RolePermission rolePermission = new RolePermission(editRolePermissionRequest.getRoleId(), editRolePermissionRequest.getType(), editRolePermissionRequest.getProjectId());
        rolePermission.set_id(idGeneratorDao.nextId(EntityUtil.getEntityName(RolePermission.class)));
        rolePermission.setResourceOperationId(resourceOperationId);
        return rolePermission;
    }

}
