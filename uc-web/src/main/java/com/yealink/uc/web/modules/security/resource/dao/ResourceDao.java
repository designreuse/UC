package com.yealink.uc.web.modules.security.resource.dao;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.yealink.dataservice.client.IRemoteDataService;
import com.yealink.dataservice.client.RemoteServiceFactory;
import com.yealink.uc.common.modules.security.resource.entity.Resource;
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
public class ResourceDao {
    private IRemoteDataService remoteDataService = RemoteServiceFactory.getInstance().getRemoteDataService();
    private static String RESOURCE_ENTITY_NAME = EntityUtil.getEntityName(Resource.class);

    public long save(Resource resource) {
        return remoteDataService.insertOne(RESOURCE_ENTITY_NAME, resource);
    }

    public long update(Resource resource) {
        return remoteDataService.updateOne(RESOURCE_ENTITY_NAME, resource.get_id(), resource);
    }

    public long delete(Long resourceId) {
        return remoteDataService.deleteMany(RESOURCE_ENTITY_NAME, eq("_id", resourceId).toMap());
    }

    public Resource get(Long resourceId) {
        Map<String, Object> resourceMap = remoteDataService.queryOne(RESOURCE_ENTITY_NAME, null, eq("_id", resourceId).toMap());
        if (resourceMap == null) return null;
        return DataConverter.copyFromMap(new Resource(), resourceMap);
    }

    public List<Resource> findByIds(List<Long> resourceIds) {
        List<Map<String, Object>> resourceMapList = remoteDataService.query(RESOURCE_ENTITY_NAME, null, in("_id", resourceIds).toMap());
        if (CollectionUtils.isEmpty(resourceMapList)) return null;
        return mapConvertToList(resourceMapList);
    }

    public List<Resource> findByModule(Long moduleId) {
        List<Map<String, Object>> resourceMapList = remoteDataService.query(RESOURCE_ENTITY_NAME, null, eq("moduleId", moduleId).toMap());
        if (CollectionUtils.isEmpty(resourceMapList)) return null;
        return mapConvertToList(resourceMapList);
    }

    public List<Resource> findByProject(Long projectId) {
        List<Map<String, Object>> resourceMapList = remoteDataService.query(RESOURCE_ENTITY_NAME, null, eq("projectId", projectId).toMap());
        if (CollectionUtils.isEmpty(resourceMapList)) return null;
        return mapConvertToList(resourceMapList);
    }

    public boolean checkNameExist(String name, Long moduleId) {
        return remoteDataService.queryCount(RESOURCE_ENTITY_NAME, and(eq("name", name), eq("moduleId", moduleId)).toMap()) > 0;
    }

    public boolean checkNameExistExceptItSelf(String name, Long moduleId, Long resourceId) {
        return remoteDataService.queryCount(RESOURCE_ENTITY_NAME, and(eq("name", name), eq("moduleId", moduleId), ne("_id", resourceId)).toMap()) > 0;
    }

    public long count(Long moduleId) {
        return remoteDataService.queryCount(RESOURCE_ENTITY_NAME, eq("moduleId", moduleId).toMap());
    }

    private List<Resource> mapConvertToList(List<Map<String, Object>> resourceMapList) {
        Collection resourceList = CollectionUtils.collect(resourceMapList, new Transformer() {
            @Override
            public Object transform(Object input) {
                return DataConverter.copyFromMap(new Resource(), (Map<String, Object>) input);
            }
        });
        return (List<Resource>) resourceList;
    }
}
