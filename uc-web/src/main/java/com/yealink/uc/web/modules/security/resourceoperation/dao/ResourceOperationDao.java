package com.yealink.uc.web.modules.security.resourceoperation.dao;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.yealink.dataservice.client.IRemoteDataService;
import com.yealink.dataservice.client.RemoteServiceFactory;
import com.yealink.uc.common.modules.security.resourceoperation.ResourceOperation;
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
public class ResourceOperationDao {
    private IRemoteDataService remoteDataService = RemoteServiceFactory.getInstance().getRemoteDataService();
    private static String RESOURCE_OPERATION_ENTITY_NAME = EntityUtil.getEntityName(ResourceOperation.class);

    public long save(ResourceOperation resourceOperation) {
        return remoteDataService.insertOne(RESOURCE_OPERATION_ENTITY_NAME, resourceOperation);
    }

    public long update(ResourceOperation resourceOperation) {
        return remoteDataService.updateOne(RESOURCE_OPERATION_ENTITY_NAME, resourceOperation.get_id(), resourceOperation);
    }

    public long delete(Long resourceOperationId) {
        return remoteDataService.deleteMany(RESOURCE_OPERATION_ENTITY_NAME, eq("_id", resourceOperationId).toMap());
    }

    public long delete(Long resourceId, Long operationId, Long enterpriseId) {
        return remoteDataService.deleteMany(RESOURCE_OPERATION_ENTITY_NAME, and(eq("resourceId", resourceId), eq("operationId", operationId), eq("platformEnterpriseId", enterpriseId)).toMap());
    }

    public ResourceOperation get(Long resourceOperationId) {
        Map<String, Object> resourceOperationMap = remoteDataService.queryOne(RESOURCE_OPERATION_ENTITY_NAME, null, eq("_id", resourceOperationId).toMap());
        if (resourceOperationMap == null) return null;
        return DataConverter.copyFromMap(new ResourceOperation(), resourceOperationMap);
    }

    public ResourceOperation get(Long resourceId, Long operationId, Long enterpriseId) {
        Map<String, Object> resourceOperationMap = remoteDataService.queryOne(RESOURCE_OPERATION_ENTITY_NAME, null, and(eq("resourceId", resourceId),
            eq("operationId", operationId), eq("enterpriseId", enterpriseId)).toMap());
        if (resourceOperationMap == null) return null;
        return DataConverter.copyFromMap(new ResourceOperation(), resourceOperationMap);
    }

    public List<ResourceOperation> findByIds(List<Long> resourceOperationIds) {
        List<Map<String, Object>> resourceOperationMapList = remoteDataService.query(RESOURCE_OPERATION_ENTITY_NAME, null, in("_id", resourceOperationIds).toMap());
        if (CollectionUtils.isEmpty(resourceOperationMapList)) return null;
        return mapConvertToList(resourceOperationMapList);
    }

    public List<ResourceOperation> findByEnterprise(Long enterpriseId) {
        List<Map<String, Object>> resourceOperationMapList = remoteDataService.query(RESOURCE_OPERATION_ENTITY_NAME, null, eq("platformEnterpriseId", enterpriseId).toMap());
        if (CollectionUtils.isEmpty(resourceOperationMapList)) return null;
        return mapConvertToList(resourceOperationMapList);
    }

    public boolean checkExist(Long resourceId, Long operationId, Long enterpriseId) {
        return remoteDataService.queryCount(RESOURCE_OPERATION_ENTITY_NAME, and(eq("resourceId", resourceId),
            eq("operationId", operationId), eq("enterpriseId", enterpriseId)).toMap()) > 0;
    }

    public long countByOperation(Long operationId) {
        return remoteDataService.queryCount(RESOURCE_OPERATION_ENTITY_NAME, eq("operationId", operationId).toMap());
    }

    public long countByResource(Long resourceId) {
        return remoteDataService.queryCount(RESOURCE_OPERATION_ENTITY_NAME, eq("resourceId", resourceId).toMap());
    }

    private List<ResourceOperation> mapConvertToList(List<Map<String, Object>> resourceOperationMapList) {
        Collection resourceOperationList = CollectionUtils.collect(resourceOperationMapList, new Transformer() {
            @Override
            public Object transform(Object input) {
                return DataConverter.copyFromMap(new ResourceOperation(), (Map<String, Object>) input);
            }
        });
        return (List<ResourceOperation>) resourceOperationList;
    }
}
