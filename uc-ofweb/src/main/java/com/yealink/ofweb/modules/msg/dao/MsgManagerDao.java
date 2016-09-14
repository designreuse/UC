package com.yealink.ofweb.modules.msg.dao;

import com.yealink.dataservice.client.IRemoteDataService;
import com.yealink.dataservice.client.RemoteServiceFactory;
import com.yealink.dataservice.client.util.Filter;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 消息管理
 * author:pengzhiyuan
 * Created on:2016/8/8.
 */
@Repository
public class MsgManagerDao {
    private static final String YL_PROPERTY_ENTITYNAME="Property";
    private static IRemoteDataService remoteDataService = RemoteServiceFactory.getInstance().getRemoteDataService();

    /**
     * 根据传输的_id列表获取对应的属性值
     *
     * @param ids
     * @return
     */
    public List<Map<String, Object>> queryPropertys(List<String> ids) {
        String queryFields = "propValue";
        List<Map<String, Object>> propertyList = remoteDataService.query(YL_PROPERTY_ENTITYNAME, queryFields,
                Filter.in("_id", ids).toMap());
        return propertyList;
    }

    /**
     *  保存属性设置信息
     * @param id
     * @param propValue
     */
    public void saveProperty(String id, String propValue) {
        Map<String, Object> valueMap = new HashMap<String, Object>();
        valueMap.put("_id", id);
        valueMap.put("propValue", propValue);
        remoteDataService.insertOne(YL_PROPERTY_ENTITYNAME, valueMap);
    }

    /**
     * 更新属性设置
     * @param id
     * @param propValue
     */
    public void updateProperty(String id, String propValue) {
        Map<String, String> entity = new HashMap<String, String>();
        entity.put("propValue", propValue);
        remoteDataService.updateOne(YL_PROPERTY_ENTITYNAME, id, entity);
    }

    /**
     * 查询属性信息
     * @param id
     * @return
     */
    public Map<String, Object> queryPropertyById(String id) {
        String queryFields = "propValue";
        Map<String, Object> fileMap = remoteDataService.queryOne(YL_PROPERTY_ENTITYNAME, queryFields,
                Filter.eq("_id", id).toMap());
        return fileMap;
    }

}
