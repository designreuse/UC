package com.yealink.uc.web.modules.security.operation.dao;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.yealink.dataservice.client.IRemoteDataService;
import com.yealink.dataservice.client.RemoteServiceFactory;
import com.yealink.uc.common.modules.security.operation.entity.Operation;
import com.yealink.uc.platform.utils.DataConverter;
import com.yealink.uc.platform.utils.EntityUtil;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Transformer;
import org.springframework.stereotype.Repository;

import static com.yealink.dataservice.client.util.Filter.and;
import static com.yealink.dataservice.client.util.Filter.eq;
import static com.yealink.dataservice.client.util.Filter.in;
import static com.yealink.dataservice.client.util.Filter.ne;

/**
 * @author ChNan
 */
@Repository
@SuppressWarnings("unchecked")
public class OperationDao {
    private IRemoteDataService remoteDataService = RemoteServiceFactory.getInstance().getRemoteDataService();
    private static String OPERATION_ENTITY_NAME = EntityUtil.getEntityName(Operation.class);

    public long save(Operation operation) {
        return remoteDataService.insertOne(OPERATION_ENTITY_NAME, operation);
    }

    public long update(Operation operation) {
        return remoteDataService.updateOne(OPERATION_ENTITY_NAME, operation.get_id(), operation);
    }

    public long delete(Long operationId, Long enterpriseId) {
        return remoteDataService.deleteMany(OPERATION_ENTITY_NAME, and(eq("_id", operationId), eq("platformEnterpriseId", enterpriseId)).toMap());
    }

    public Operation get(Long operationId) {
        Map<String, Object> operationMap = remoteDataService.queryOne(OPERATION_ENTITY_NAME, null, eq("_id", operationId).toMap());
        if (operationMap == null) return null;
        return DataConverter.copyFromMap(new Operation(), operationMap);
    }

    public Operation get(Long operationId, Long enterpriseId) {
        Map<String, Object> operationMap = remoteDataService.queryOne(OPERATION_ENTITY_NAME, null, and(eq("_id", operationId), eq("platformEnterpriseId", enterpriseId)).toMap());
        if (operationMap == null) return null;
        return DataConverter.copyFromMap(new Operation(), operationMap);
    }

    public boolean checkNameExist(String name, Long enterpriseId) {
        return remoteDataService.queryCount(OPERATION_ENTITY_NAME, and(eq("name", name), eq("platformEnterpriseId", enterpriseId)).toMap()) > 0;
    }

    public boolean checkNameExceptItSelt(String name, Long enterpriseId, Long operationId) {
        return remoteDataService.queryCount(OPERATION_ENTITY_NAME, and(eq("name", name), eq("platformEnterpriseId", enterpriseId), ne("_id", operationId)).toMap()) > 0;
    }

    public List<Operation> findByIds(List<Long> operationIds) {
        List<Map<String, Object>> operationMapList = remoteDataService.query(OPERATION_ENTITY_NAME, null, in("_id", operationIds).toMap());
        if (CollectionUtils.isEmpty(operationMapList)) return null;
        return mapConvertToList(operationMapList);
    }

    public List<Operation> findByEnterprise(Long platformEnterpriseId) {
        List<Map<String, Object>> operationMapList = remoteDataService.query(OPERATION_ENTITY_NAME, null, eq("platformEnterpriseId", platformEnterpriseId).toMap());
        if (CollectionUtils.isEmpty(operationMapList)) return null;
        return mapConvertToList(operationMapList);
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

    private List<Operation> mapConvertToList(List<Map<String, Object>> operationMapList) {
        Collection operationList = CollectionUtils.collect(operationMapList, new Transformer() {
            @Override
            public Object transform(Object input) {
                return DataConverter.copyFromMap(new Operation(), (Map<String, Object>) input);
            }
        });
        return (List<Operation>) operationList;
    }
}
