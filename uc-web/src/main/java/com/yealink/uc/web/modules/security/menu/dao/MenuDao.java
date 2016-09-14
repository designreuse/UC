package com.yealink.uc.web.modules.security.menu.dao;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.yealink.dataservice.client.IRemoteDataService;
import com.yealink.dataservice.client.RemoteServiceFactory;
import com.yealink.dataservice.client.util.OrderBy;
import com.yealink.dataservice.client.util.Pager;
import com.yealink.uc.common.modules.security.menu.entity.Menu;
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
public class MenuDao {
    private IRemoteDataService remoteDataService = RemoteServiceFactory.getInstance().getRemoteDataService();
    private static String MENU_ENTITY_NAME = EntityUtil.getEntityName(Menu.class);

    public long save(Menu menu) {
        return remoteDataService.insertOne(MENU_ENTITY_NAME, menu);
    }

    public long update(Menu menu) {
        return remoteDataService.updateOne(MENU_ENTITY_NAME, menu.get_id(), menu);
    }

    public long delete(Long menuId) {
        return remoteDataService.deleteMany(MENU_ENTITY_NAME, eq("_id", menuId).toMap());
    }

    public Menu get(Long menuId) {
        Map<String, Object> menuMap = remoteDataService.queryOne(MENU_ENTITY_NAME, null, eq("_id", menuId).toMap());
        if (menuMap == null) return null;
        return DataConverter.copyFromMap(new Menu(), menuMap);
    }

    public int nextIndex(Long moduleId) {
        Pager pager = new Pager();
        pager.setLimit(1);
        pager.setOrderbys(Arrays.asList(new OrderBy("index", -1)));
        Pager<Map<String, Object>> orgMap = remoteDataService.query(MENU_ENTITY_NAME, "index", pager, eq("moduleId", moduleId).toMap());
        if (orgMap.getData().isEmpty()) return 1;
        Object maxIndex = orgMap.getData().get(0).get("index");
        return maxIndex == null ? 1 : (int) maxIndex + 1;
    }

    public List<Menu> findByProject(Long projectId) {
        List<Map<String, Object>> menuMapList = remoteDataService.query(MENU_ENTITY_NAME, null, eq("projectId", projectId).toMap());
        if (CollectionUtils.isEmpty(menuMapList)) return null;
        return mapConvertToList(menuMapList);
    }

    public List<Menu> findByIds(List<Long> menuIds) {
        List<Map<String, Object>> menuMapList = remoteDataService.query(MENU_ENTITY_NAME, null, in("_id", menuIds).toMap());
        if (CollectionUtils.isEmpty(menuMapList)) return null;
        return mapConvertToList(menuMapList);
    }

    public List<Menu> findByModule(Long moduleId) {
        List<Map<String, Object>> menuMapList = remoteDataService.query(MENU_ENTITY_NAME, null, eq("moduleId", moduleId).toMap());
        if (CollectionUtils.isEmpty(menuMapList)) return null;
        return mapConvertToList(menuMapList);
    }

    public boolean checkNameExist(String name, Long moduleId) {
        return remoteDataService.queryCount(MENU_ENTITY_NAME, and(eq("name", name), eq("moduleId", moduleId)).toMap()) > 0;
    }

    public boolean checkNameExistExceptItSelf(String name, Long moduleId, Long menuId) {
        return remoteDataService.queryCount(MENU_ENTITY_NAME, and(eq("name", name), eq("moduleId", moduleId), ne("_id", menuId)).toMap()) > 0;
    }

    public long count(Long moduleId) {
        return remoteDataService.queryCount(MENU_ENTITY_NAME, eq("moduleId", moduleId).toMap());
    }

    private List<Menu> mapConvertToList(List<Map<String, Object>> menuMapList) {
        Collection menuList = CollectionUtils.collect(menuMapList, new Transformer() {
            @Override
            public Object transform(Object input) {
                return DataConverter.copyFromMap(new Menu(), (Map<String, Object>) input);
            }
        });
        return (List<Menu>) menuList;
    }
}
