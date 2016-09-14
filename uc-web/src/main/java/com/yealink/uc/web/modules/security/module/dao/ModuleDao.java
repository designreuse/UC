package com.yealink.uc.web.modules.security.module.dao;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.yealink.dataservice.client.IRemoteDataService;
import com.yealink.dataservice.client.RemoteServiceFactory;
import com.yealink.uc.common.modules.security.module.entity.Module;
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
public class ModuleDao {
    private IRemoteDataService remoteDataService = RemoteServiceFactory.getInstance().getRemoteDataService();
    private static String MODULE_ENTITY_NAME = EntityUtil.getEntityName(Module.class);

    public long save(Module menu) {
        return remoteDataService.insertOne(MODULE_ENTITY_NAME, menu);
    }

    public long update(Module menu) {
        return remoteDataService.updateOne(MODULE_ENTITY_NAME, menu.get_id(), menu);
    }

    public long delete(Long menuId) {
        return remoteDataService.deleteMany(MODULE_ENTITY_NAME, eq("_id", menuId).toMap());
    }

    public Module get(Long moduleId) {
        Map<String, Object> moduleMap = remoteDataService.queryOne(MODULE_ENTITY_NAME, null, eq("_id", moduleId).toMap());
        if (moduleMap == null) return null;
        return DataConverter.copyFromMap(new Module(), moduleMap);
    }

    public List<Module> findByIds(List<Long> menuIds) {
        List<Map<String, Object>> moduleMapList = remoteDataService.query(MODULE_ENTITY_NAME, null, in("_id", menuIds).toMap());
        if (CollectionUtils.isEmpty(moduleMapList)) return null;
        return mapConvertToList(moduleMapList);
    }

    public boolean checkNameExist(String name, Long projectId) {
        return remoteDataService.queryCount(MODULE_ENTITY_NAME, and(eq("name", name), eq("projectId", projectId)).toMap()) > 0;
    }

    public boolean checkNameExistExceptItSelf(String name, Long projectId, Long moduleId) {
        return remoteDataService.queryCount(MODULE_ENTITY_NAME, and(eq("name", name), eq("projectId", projectId), ne("_id", moduleId)).toMap()) > 0;
    }

    public List<Module> findByProject(Long projectId) {
        List<Map<String, Object>> moduleMapList = remoteDataService.query(MODULE_ENTITY_NAME, null, eq("projectId", projectId).toMap());
        if (CollectionUtils.isEmpty(moduleMapList)) return null;
        return mapConvertToList(moduleMapList);
    }

    public long count(Long projectId) {
        return remoteDataService.queryCount(MODULE_ENTITY_NAME, eq("projectId", projectId).toMap());
    }

    private List<Module> mapConvertToList(List<Map<String, Object>> moduleMapList) {
        Collection menuList = CollectionUtils.collect(moduleMapList, new Transformer() {
            @Override
            public Object transform(Object input) {
                return DataConverter.copyFromMap(new Module(), (Map<String, Object>) input);
            }
        });
        return (List<Module>) menuList;
    }
}
