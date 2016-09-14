package com.yealink.uc.web.modules.security.role.service;

import java.util.List;

import com.yealink.uc.common.modules.security.role.entity.Role;
import com.yealink.uc.platform.exception.BusinessHandleException;
import com.yealink.uc.platform.utils.DataConverter;
import com.yealink.uc.platform.utils.EntityUtil;
import com.yealink.uc.web.modules.common.dao.IdGeneratorDao;
import com.yealink.uc.web.modules.security.role.dao.RoleDao;
import com.yealink.uc.web.modules.security.role.request.CreateRoleRequest;
import com.yealink.uc.web.modules.security.role.request.EditRoleRequest;
import com.yealink.uc.web.modules.staffrolemapping.dao.StaffRoleMappingDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author ChNan
 */
@Service
public class RoleService {
    @Autowired
    RoleDao roleDao;
    @Autowired
    IdGeneratorDao idGeneratorDao;

    @Autowired
    StaffRoleMappingDao staffRoleMappingDao;


    public Role get(Long roleId, Long enterpriseId) {
        return roleDao.get(roleId, enterpriseId);
    }

    public List<Role> findAllByEnterprise(Long enterpriseId) {
        return roleDao.findByEnterprise(enterpriseId);
    }

    public List<Long> findIdsByEnterprise(Long enterpriseId) {
        return roleDao.findIdsByEnterprise(enterpriseId);
    }

    public boolean create(CreateRoleRequest createRoleRequest, Long enterpriseId) {
        validateNameDuplicate(createRoleRequest.getName(), enterpriseId);
        Role role = DataConverter.copy(new Role(), createRoleRequest);
        role.set_id(idGeneratorDao.nextId(EntityUtil.getEntityName(Role.class)));
        role.setPlatformEnterpriseId(enterpriseId);
        return roleDao.save(role) > 0;
    }

    public boolean edit(EditRoleRequest editRoleRequest, Long enterpriseId) {
        Role role = roleDao.get(editRoleRequest.getId(), enterpriseId);
        if (role == null)
            throw new BusinessHandleException("role.exception.not.exist");
        validateNameDuplicate(editRoleRequest.getName(), enterpriseId, editRoleRequest.getId());
        role = DataConverter.copy(role, editRoleRequest);
        return roleDao.update(role) > 0;
    }

    private void validateNameDuplicate(String name, Long enterpriseId) {
        if (roleDao.checkNameExist(name, enterpriseId))
            throw new BusinessHandleException("role.exception.name.duplicate", name);
    }

    private void validateNameDuplicate(String name, Long enterpriseId, Long roleId) {
        if (roleDao.checkNameExistExceptItSelf(name, enterpriseId, roleId))
            throw new BusinessHandleException("role.exception.name.duplicate", name);
    }


    public boolean delete(Long roleId, Long enterpriseId) {
        long staffRoleCount = staffRoleMappingDao.count(roleId);
        if (staffRoleCount > 0)
            throw new BusinessHandleException("role.exception.used.bystaff");

        return roleDao.delete(roleId, enterpriseId) > 0;
    }
}
