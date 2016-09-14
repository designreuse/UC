package com.yealink.uc.web.modules.security.operation.service;

import java.util.List;

import com.yealink.uc.common.modules.security.operation.entity.Operation;
import com.yealink.uc.platform.exception.BusinessHandleException;
import com.yealink.uc.platform.utils.DataConverter;
import com.yealink.uc.platform.utils.EntityUtil;
import com.yealink.uc.web.modules.common.dao.IdGeneratorDao;
import com.yealink.uc.web.modules.security.operation.dao.OperationDao;
import com.yealink.uc.web.modules.security.operation.request.CreateOperationRequest;
import com.yealink.uc.web.modules.security.operation.request.EditOperationRequest;
import com.yealink.uc.web.modules.security.resourceoperation.dao.ResourceOperationDao;
import com.yealink.uc.web.modules.security.rolepermission.dao.RolePermissionDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author ChNan
 */
@Service
public class OperationService {
    @Autowired
    OperationDao operationDao;
    @Autowired
    IdGeneratorDao idGeneratorDao;

    @Autowired
    RolePermissionDao rolePermissionDao;
    @Autowired
    ResourceOperationDao resourceOperationDao;

    public Operation get(Long operationId, Long enterpriseId) {
        return operationDao.get(operationId, enterpriseId);
    }

    public Operation get(Long operationId) {
        return operationDao.get(operationId);
    }

    public List<Operation> findAllByEnterprise(Long enterpriseId) {
        return operationDao.findByEnterprise(enterpriseId);
    }

    public boolean create(CreateOperationRequest createOperationRequest, Long enterpriseId) {
        validateNameDuplicate(createOperationRequest.getName(), enterpriseId);
        Operation operation = DataConverter.copy(new Operation(), createOperationRequest);
        operation.set_id(idGeneratorDao.nextId(EntityUtil.getEntityName(Operation.class)));
        operation.setPlatformEnterpriseId(enterpriseId);
        return operationDao.save(operation) > 0;
    }

    public boolean edit(EditOperationRequest editOperationRequest, Long enterpriseId) {
        Operation operation = operationDao.get(editOperationRequest.getId(), enterpriseId);
        if (operation == null)
            throw new BusinessHandleException("operation.exception.not.exist");
        validateNameDuplicate(editOperationRequest.getName(), enterpriseId, editOperationRequest.getId());
        operation = DataConverter.copy(operation, editOperationRequest);
        return operationDao.update(operation) > 0;
    }

    private void validateNameDuplicate(String name, Long enterpriseId) {
        if (operationDao.checkNameExist(name, enterpriseId))
            throw new BusinessHandleException("operation.exception.name.duplicate", name);
    }

    private void validateNameDuplicate(String name, Long enterpriseId, Long operationId) {
        if (operationDao.checkNameExceptItSelt(name, enterpriseId, operationId))
            throw new BusinessHandleException("operation.exception.name.duplicate", name);
    }

    public boolean delete(Long operationId, Long enterpriseId) {
        long resourceOperationCount = resourceOperationDao.countByOperation(operationId);
        if (resourceOperationCount > 0)
            throw new BusinessHandleException("operation.exception.inused");
        return operationDao.delete(operationId, enterpriseId) > 0;
    }
}
