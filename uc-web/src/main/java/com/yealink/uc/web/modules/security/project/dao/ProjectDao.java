package com.yealink.uc.web.modules.security.project.dao;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.yealink.dataservice.client.IRemoteDataService;
import com.yealink.dataservice.client.RemoteServiceFactory;
import com.yealink.uc.common.modules.security.project.entity.Project;
import com.yealink.uc.platform.utils.DataConverter;
import com.yealink.uc.platform.utils.EntityUtil;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Transformer;
import org.springframework.stereotype.Repository;

import static com.yealink.dataservice.client.util.Filter.and;
import static com.yealink.dataservice.client.util.Filter.eq;
import static com.yealink.dataservice.client.util.Filter.ne;

/**
 * @author ChNan
 */
@Repository
@SuppressWarnings("unchecked")
public class ProjectDao {
    private IRemoteDataService remoteDataService = RemoteServiceFactory.getInstance().getRemoteDataService();
    private static String PROJECT_ENTITY_NAME = EntityUtil.getEntityName(Project.class);

    public long save(Project project) {
        return remoteDataService.insertOne(PROJECT_ENTITY_NAME, project);
    }

    public long update(Project project) {
        return remoteDataService.updateOne(PROJECT_ENTITY_NAME, project.get_id(), project);
    }

    public long delete(Long projectId, Long enterpriseId) {
        return remoteDataService.deleteMany(PROJECT_ENTITY_NAME, and(eq("_id", projectId), eq("platformEnterpriseId", enterpriseId)).toMap());
    }

    public Project get(Long projectId, Long enterpriseId) {
        Map<String, Object> projectMap = remoteDataService.queryOne(PROJECT_ENTITY_NAME, null, and(eq("_id", projectId), eq("platformEnterpriseId", enterpriseId)).toMap());
        if (projectMap == null) return null;
        return DataConverter.copyFromMap(new Project(), projectMap);
    }

    public boolean checkNameExist(String name, Long enterpriseId) {
        return remoteDataService.queryCount(PROJECT_ENTITY_NAME, and(eq("name", name), eq("platformEnterpriseId", enterpriseId)).toMap()) > 0;
    }

    public boolean checkNameExceptItSelt(String name, Long enterpriseId, Long projectId) {
        return remoteDataService.queryCount(PROJECT_ENTITY_NAME, and(eq("name", name), eq("platformEnterpriseId", enterpriseId), ne("_id", projectId)).toMap()) > 0;
    }

    public List<Project> findByEnterprise(Long platformEnterpriseId) {
        List<Map<String, Object>> projectMapList = remoteDataService.query(PROJECT_ENTITY_NAME, null, eq("platformEnterpriseId", platformEnterpriseId).toMap());
        if (CollectionUtils.isEmpty(projectMapList)) return null;
        return mapConvertToList(projectMapList);
    }

    public List<Long> findIdsByEnterprise(Long platformEnterpriseId) {
        List<Map<String, Object>> idMapList = remoteDataService.query(PROJECT_ENTITY_NAME, null, eq("enterpriseId", platformEnterpriseId).toMap());
        if (CollectionUtils.isEmpty(idMapList)) return null;
        return (List<Long>) CollectionUtils.collect(idMapList, new Transformer() {
            @Override
            public Long transform(Object input) {
                Number result = ((Map<String, Long>) input).get("_id");
                return result.longValue();
            }
        });
    }

    private List<Project> mapConvertToList(List<Map<String, Object>> projectMapList) {
        Collection projectList = CollectionUtils.collect(projectMapList, new Transformer() {
            @Override
            public Object transform(Object input) {
                return DataConverter.copyFromMap(new Project(), (Map<String, Object>) input);
            }
        });
        return (List<Project>) projectList;
    }
}
