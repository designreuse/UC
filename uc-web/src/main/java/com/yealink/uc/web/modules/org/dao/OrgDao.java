package com.yealink.uc.web.modules.org.dao;

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
import static com.yealink.dataservice.client.util.Filter.ne;
import static com.yealink.dataservice.client.util.Filter.regex;

/**
 * @author ChNan
 */
@Repository
@SuppressWarnings("unchecked")
public class OrgDao {
    private IRemoteDataService remoteDataService = RemoteServiceFactory.getInstance().getRemoteDataService();
    private static String ORG_ENTITY_NAME = EntityUtil.getEntityName(Org.class);

    public long save(Org org) {
        return remoteDataService.insertOne(ORG_ENTITY_NAME, org);
    }

    public long batchSave(List<Org> orgList) {
        return remoteDataService.insertMany(ORG_ENTITY_NAME, orgList);
    }

    public long update(Org org) {
        return remoteDataService.updateOne(ORG_ENTITY_NAME, org.get_id(), org);
    }

    public long updateByMap(Long orgId, Long enterpriseId, Map<String, Object> updatedMap) {
        return remoteDataService.updateMany(ORG_ENTITY_NAME, and(eq("_id", orgId), eq("enterpriseId", enterpriseId)).toMap(), updatedMap);
    }

    public long batchUpdateModificationDate(Map<String, Object> modificationMap, List<Long> orgIds) {
        return remoteDataService.updateMany(ORG_ENTITY_NAME, in("_id", orgIds).toMap(), modificationMap);
    }

    public long delete(Long orgId) {
        return remoteDataService.deleteMany(ORG_ENTITY_NAME, eq("_id", orgId).toMap());
    }

    public List<Org> findAll(Long enterpriseId) {
        Pager pager = new Pager();
        pager.setLimit(Integer.MAX_VALUE);
        pager.setOrderbys(Arrays.asList(new OrderBy("index", OrderBy.ASC)));
        Pager<Map<String, Object>> orgMapList = remoteDataService.query(ORG_ENTITY_NAME, null, pager, eq("enterpriseId", enterpriseId).toMap());
        if (CollectionUtils.isEmpty(orgMapList.getData())) return null;
        return mapConvertToOrgList(orgMapList.getData());
    }

    public List<Org> findByIds(List<Long> orgIds, Long enterpriseId) {
        Pager pager = new Pager();
        pager.setLimit(Integer.MAX_VALUE);
        pager.setOrderbys(Arrays.asList(new OrderBy("index", OrderBy.ASC)));
        Pager<Map<String, Object>> orgMapList = remoteDataService.query(ORG_ENTITY_NAME, null, pager, and(in("_id", orgIds), eq("enterpriseId", enterpriseId)).toMap());
        if (CollectionUtils.isEmpty(orgMapList.getData())) return null;
        return mapConvertToOrgList(orgMapList.getData());
    }

    //db.Org.find({"$and":[{"_id":{"$ne":47}},{'orgPath': {'$not':/:47$|^47|:47:/}}]})
    //todo how to accomplish the above query
    public List<Org> findAllExceptItSelf(Long orgId) {
        Pager pager = new Pager();
        pager.setLimit(Integer.MAX_VALUE);
        pager.setOrderbys(Arrays.asList(new OrderBy("index", OrderBy.ASC)));
        Pager<Map<String, Object>> orgMapList = remoteDataService.query(ORG_ENTITY_NAME, null, pager, ne("_id", orgId).toMap());
        if (CollectionUtils.isEmpty(orgMapList.getData())) return null;
        return mapConvertToOrgList(orgMapList.getData());
    }

    public List<Org> findItSelfAndSubOrgList(Long orgId) {
        List<Map<String, Object>> orgMapIncludeSubList = remoteDataService.query(ORG_ENTITY_NAME, null,
            regex("orgPath", ":" + orgId + "$|^" + orgId + "|:" + orgId + ":").toMap());
        return mapConvertToOrgList(orgMapIncludeSubList);
    }

    public Org get(Long orgId, Long enterpriseId) {
        Map<String, Object> orgMap = remoteDataService.queryOne(ORG_ENTITY_NAME, null, and(eq("_id", orgId), eq("enterpriseId", enterpriseId)).toMap());
        if (orgMap == null) return null;
        return DataConverter.copyFromMap(new Org(), orgMap);
    }
    public Org getRootOrg( Long enterpriseId) {
        Map<String, Object> orgMap = remoteDataService.queryOne(ORG_ENTITY_NAME, null, and(eq("parentId", 0), eq("enterpriseId", enterpriseId)).toMap());
        if (orgMap == null) return null;
        return DataConverter.copyFromMap(new Org(), orgMap);
    }
    // if current org 12 is target org 5 parent, the target org 5 's org path will contains 12.
    public boolean checkTargetOrgIsMovedOrgSub(Long orgId, Long targetOrgId) {
        long count = remoteDataService.queryCount(ORG_ENTITY_NAME,
            and(eq("_id", targetOrgId), regex("orgPath", ":" + orgId + "$|^" + orgId + "|:" + orgId + ":")).toMap());
        return count == 1;
    }

    public long findSubOrgCountByName(Long parentId, String orgName) {
        return remoteDataService.queryCount(ORG_ENTITY_NAME, and(eq("parentId", parentId), eq("name", orgName)).toMap());
    }

    public long findSubOrgCountByNameExceptItSelf(Long parentId, Long orgId, String orgName) {
        return remoteDataService.queryCount(ORG_ENTITY_NAME, and(eq("parentId", parentId), eq("name", orgName), ne("_id", orgId)).toMap());
    }

    public long findSubOrgCount(Long orgId) {
        return remoteDataService.queryCount(ORG_ENTITY_NAME, eq("parentId", orgId).toMap());
    }

    public Org findRootOrgCount(Long enterpriseId) {
        return DataConverter.copyFromMap(new Org(), remoteDataService.queryOne(ORG_ENTITY_NAME, null, and(eq("parentId", 0), eq("enterpriseId", enterpriseId)).toMap()));
    }

    public boolean checkOrgExistInEnterprise(Long orgId, Long enterpriseId) {
        return remoteDataService.queryCount(ORG_ENTITY_NAME, and(eq("_id", orgId), eq("enterpriseId", enterpriseId)).toMap()) > 0;
    }

    public List<Long> findOrgIds(List<Long> orgIds, Long enterpriseId) {
        List<Map<String, Object>> idMapList = remoteDataService.query(ORG_ENTITY_NAME, "_id", and(eq("enterpriseId", enterpriseId), in("_id", orgIds)).toMap());
        if (CollectionUtils.isEmpty(idMapList)) return null;
        return (List<Long>) CollectionUtils.collect(idMapList, new Transformer() {
            @Override
            public Long transform(Object input) {
                Number result = ((Map<String, Long>) input).get("_id");
                return result.longValue();
            }
        });
    }

    public List<String> findOrgPath(List<Long> orgIds, Long enterpriseId) {
        List<Map<String, Object>> orgPathMapList = remoteDataService.query(ORG_ENTITY_NAME, "orgPath", and(eq("enterpriseId", enterpriseId), in("_id", orgIds)).toMap());
        if (CollectionUtils.isEmpty(orgPathMapList)) return null;
        return (List<String>) CollectionUtils.collect(orgPathMapList, new Transformer() {
            @Override
            public String transform(Object input) {
                return ((Map<String, String>) input).get("orgPath");
            }
        });
    }

    // db.Org.find({'orgPath': {'$regex':'^1:127:'}})
    public List<Org> findAllSubOrgListInOrgPath(String orgPath) {
        List<Map<String, Object>> allSubOrgMap = remoteDataService.query(ORG_ENTITY_NAME, null, regex("orgPath", "^" + orgPath + ":").toMap());
        return mapConvertToOrgList(allSubOrgMap);
    }

    public int nextIndex(Long parentOrgId) {
        Pager pager = new Pager();
        pager.setLimit(1);
        pager.setOrderbys(Arrays.asList(new OrderBy("index", -1)));
        Pager<Map<String, Object>> orgMap = remoteDataService.query(ORG_ENTITY_NAME, "index", pager, eq("parentId", parentOrgId).toMap());
        if (orgMap.getData().isEmpty()) return 1;
        Object maxIndex = orgMap.getData().get(0).get("index");
        return maxIndex == null ? 1 : (int) maxIndex + 1;
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
