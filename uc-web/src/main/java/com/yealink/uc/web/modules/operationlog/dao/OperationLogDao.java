package com.yealink.uc.web.modules.operationlog.dao;

import com.yealink.dataservice.client.IRemoteDataService;
import com.yealink.dataservice.client.RemoteServiceFactory;
import com.yealink.uc.platform.utils.EntityUtil;
import com.yealink.uc.common.modules.operationlog.entity.OperationLog;

import org.springframework.stereotype.Repository;

/**
 * @author ChNan
 */
@Repository
public class OperationLogDao {
    public static final String OPERATION_ENTITY_NAME = EntityUtil.getEntityName(OperationLog.class);
    private IRemoteDataService remoteDataService = RemoteServiceFactory.getInstance().getRemoteDataService();

    public long save(OperationLog operationLog) {
        return remoteDataService.insertOne(OPERATION_ENTITY_NAME, operationLog);
    }
}
