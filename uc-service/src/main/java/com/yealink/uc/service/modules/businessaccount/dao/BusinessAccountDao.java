package com.yealink.uc.service.modules.businessaccount.dao;

import java.util.List;
import java.util.Map;

import com.yealink.dataservice.client.IRemoteDataService;
import com.yealink.dataservice.client.RemoteServiceFactory;
import com.yealink.uc.common.modules.businessaccount.entity.BusinessAccount;
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
@SuppressWarnings("unchecked")
@Repository
public class BusinessAccountDao {
    private IRemoteDataService remoteDataService = RemoteServiceFactory.getInstance().getRemoteDataService();
    private static final String BUSINESS_ACCOUNT_ENTITY_NAME = EntityUtil.getEntityName(BusinessAccount.class);

    public long save(BusinessAccount businessAccount) {
        return remoteDataService.insertOne(BUSINESS_ACCOUNT_ENTITY_NAME, businessAccount);
    }

    public List<BusinessAccount> findListByStaffId(Long staffId) {
        List<Map<String, Object>> result = remoteDataService.query(BUSINESS_ACCOUNT_ENTITY_NAME, null, eq("staffId", staffId).toMap());
        return (List<BusinessAccount>) CollectionUtils.collect(result, new Transformer() {
            @Override
            public BusinessAccount transform(Object input) {
                return DataConverter.copyFromMap(new BusinessAccount(), (Map<String, Object>) input);
            }
        });
    }

    public BusinessAccount get(Long id) {
        Map<String, Object> businessAccountMap = remoteDataService.queryOne(BUSINESS_ACCOUNT_ENTITY_NAME, null, eq("_id", id).toMap());
        return DataConverter.copyFromMap(new BusinessAccount(), businessAccountMap);
    }

    public List<BusinessAccount> findByStaffIdsAndType(List<Long> staffIds, String businessType) {
        List<Map<String, Object>> result = remoteDataService.query(BUSINESS_ACCOUNT_ENTITY_NAME, null, and(in("staffId", staffIds), eq("businessType", businessType)).toMap());
        return convertToList(result);
    }

    private List<BusinessAccount> convertToList(final List<Map<String, Object>> result) {
        return (List<BusinessAccount>) CollectionUtils.collect(result, new Transformer() {
            @Override
            public BusinessAccount transform(Object input) {
                return DataConverter.copyFromMap(new BusinessAccount(), (Map<String, Object>) input);
            }
        });
    }
}
