package com.yealink.uc.web.modules.enterprise.dao;

import java.util.Map;

import com.yealink.dataservice.client.IRemoteDataService;
import com.yealink.dataservice.client.RemoteServiceFactory;
import com.yealink.uc.platform.utils.DataConverter;
import com.yealink.uc.platform.utils.EntityUtil;
import com.yealink.uc.common.modules.enterprise.entity.Enterprise;

import org.springframework.stereotype.Repository;

import static com.yealink.dataservice.client.util.Filter.and;
import static com.yealink.dataservice.client.util.Filter.eq;
import static com.yealink.dataservice.client.util.Filter.ne;

/**
 * @author ChNan
 */
@Repository
public class EnterpriseDao {
    private IRemoteDataService remoteDataService = RemoteServiceFactory.getInstance().getRemoteDataService();
    private static final String ENTERPRISE_ENTITY_NAME = EntityUtil.getEntityName(Enterprise.class);

    public Enterprise findOne(Long enterpriseId) {
        Map<String, Object> enterpriseMap = remoteDataService.queryOne(ENTERPRISE_ENTITY_NAME, null, eq("_id", enterpriseId).toMap());
        if (enterpriseMap == null) {
            return null;
        }
        return DataConverter.copyFromMap(new Enterprise(), enterpriseMap);
    }

    public long update(Enterprise enterprise) {
        return remoteDataService.updateOne(ENTERPRISE_ENTITY_NAME, enterprise.get_id(), enterprise);
    }

    public long countDomainExceptItself(String domain, Long id) {
        return remoteDataService.queryCount(ENTERPRISE_ENTITY_NAME, and(eq("domain", domain), ne("_id", id)).toMap());
    }

    public long countEmailExceptItself(String email, Long id) {
        return remoteDataService.queryCount(ENTERPRISE_ENTITY_NAME, and(eq("email", email), ne("_id", id)).toMap());
    }
}
