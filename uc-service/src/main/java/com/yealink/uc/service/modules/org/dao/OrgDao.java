package com.yealink.uc.service.modules.org.dao;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.yealink.dataservice.client.IRemoteDataService;
import com.yealink.dataservice.client.RemoteServiceFactory;
import com.yealink.dataservice.client.util.OrderBy;
import com.yealink.dataservice.client.util.Pager;
import com.yealink.uc.common.modules.org.entity.Org;
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
public class OrgDao {
    private IRemoteDataService remoteDataService = RemoteServiceFactory.getInstance().getRemoteDataService();
    private static String ORG_ENTITY_NAME = EntityUtil.getEntityName(Org.class);

    public List<Org> findAll(Long enterpriseId) {
        Pager pager = new Pager();
        pager.setLimit(Integer.MAX_VALUE);
        pager.setOrderbys(Arrays.asList(new OrderBy("index", OrderBy.ASC)));
        Pager<Map<String, Object>> orgMapList = remoteDataService.query(ORG_ENTITY_NAME, null, pager, eq("enterpriseId", enterpriseId).toMap());
        if (CollectionUtils.isEmpty(orgMapList.getData())) return null;
        return mapConvertToOrgList(orgMapList.getData());
    }
    public List<Org> find(List<Long> orgIds) {
        Pager pager = new Pager();
        pager.setLimit(Integer.MAX_VALUE);
        pager.setOrderbys(Arrays.asList(new OrderBy("index", OrderBy.ASC)));
        Pager<Map<String, Object>> orgMapList = remoteDataService.query(ORG_ENTITY_NAME, null, pager, in("_id", orgIds).toMap());
        if (CollectionUtils.isEmpty(orgMapList.getData())) return null;
        return mapConvertToOrgList(orgMapList.getData());
    }
    public Org get(Long orgId, Long enterpriseId) {
        Map<String, Object> orgMap = remoteDataService.queryOne(ORG_ENTITY_NAME, null, and(eq("_id", orgId), eq("enterpriseId", enterpriseId)).toMap());
        if (orgMap == null) return null;
        return DataConverter.copyFromMap(new Org(), orgMap);
    }

    private List<Org> mapConvertToOrgList(List<Map<String, Object>> orgMapIncludeSubList) {
        Collection orgList = CollectionUtils.collect(orgMapIncludeSubList, new Transformer() {
            @Override
            public Object transform(Object input) {
                return DataConverter.copyFromMap(new Org(), (Map<String, Object>) input);
            }
        });
        return (List<Org>) orgList;
    }

}
