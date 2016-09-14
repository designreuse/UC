package com.yealink.uc.web.modules.security.rolelevel.service;

import java.util.List;

import com.yealink.uc.common.modules.security.role.RoleLevel;
import com.yealink.uc.platform.exception.BusinessHandleException;
import com.yealink.uc.platform.utils.DataConverter;
import com.yealink.uc.platform.utils.EntityUtil;
import com.yealink.uc.web.modules.common.dao.IdGeneratorDao;
import com.yealink.uc.web.modules.security.role.dao.RoleDao;
import com.yealink.uc.web.modules.security.rolelevel.dao.RoleLevelDao;
import com.yealink.uc.web.modules.security.rolelevel.request.CreateRoleLevelRequest;
import com.yealink.uc.web.modules.security.rolelevel.request.EditRoleLevelRequest;
import com.yealink.uc.web.modules.security.rolepermission.dao.RolePermissionDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author ChNan
 */
@Service
public class RoleLevelService {
    @Autowired
    RoleLevelDao roleLevelDao;
    @Autowired
    IdGeneratorDao idGeneratorDao;
    @Autowired
    RolePermissionDao rolePermissionDao;
    @Autowired
    RoleDao roleDao;

    public RoleLevel get(Long roleLevelId, Long enterpriseId) {
        return roleLevelDao.get(roleLevelId, enterpriseId);
    }

    public RoleLevel get(Long roleLevelId) {
        return roleLevelDao.get(roleLevelId);
    }

    public List<RoleLevel> findAllByEnterprise(Long enterpriseId) {
        return roleLevelDao.findByEnterprise(enterpriseId);
    }

    public List<Long> findIdsByEnterprise(Long enterpriseId) {
        return roleLevelDao.findIdsByEnterprise(enterpriseId);
    }

    public boolean create(CreateRoleLevelRequest createRoleLevelRequest, Long enterpriseId) {
        validateNameDuplicate(createRoleLevelRequest.getName(), enterpriseId);
        RoleLevel roleLevel = DataConverter.copy(new RoleLevel(), createRoleLevelRequest);
        roleLevel.set_id(idGeneratorDao.nextId(EntityUtil.getEntityName(RoleLevel.class)));
        roleLevel.setPlatformEnterpriseId(enterpriseId);
        return roleLevelDao.save(roleLevel) > 0;
    }

    public boolean edit(EditRoleLevelRequest editRoleLevelRequest, Long enterpriseId) {
        RoleLevel roleLevel = roleLevelDao.get(editRoleLevelRequest.getId(), enterpriseId);
        if (roleLevel == null)
            throw new BusinessHandleException("roleLevel.exception.not.exist");
        validateNameDuplicate(editRoleLevelRequest.getName(), enterpriseId, editRoleLevelRequest.getId());
        roleLevel = DataConverter.copy(roleLevel, editRoleLevelRequest);
        return roleLevelDao.update(roleLevel) > 0;
    }

    private void validateNameDuplicate(String name, Long enterpriseId) {
        if (roleLevelDao.checkNameExist(name, enterpriseId))
            throw new BusinessHandleException("roleLevel.exception.name.duplicate", name);
    }

    private void validateNameDuplicate(String name, Long enterpriseId, Long roleLevelId) {
        if (roleLevelDao.checkNameExceptItSelt(name, enterpriseId, roleLevelId))
            throw new BusinessHandleException("roleLevel.exception.name.duplicate", name);
    }

    public boolean delete(Long roleLevelId, Long enterpriseId) {
        long count = roleDao.count(roleLevelId, enterpriseId);
        if (count > 0) throw new BusinessHandleException("roleLevel.exception.inused");
        return roleLevelDao.delete(roleLevelId, enterpriseId) > 0;
    }
}
