package com.yealink.uc.service.modules.account.dao;

import java.util.Map;

import com.yealink.dataservice.client.IRemoteDataService;
import com.yealink.dataservice.client.RemoteServiceFactory;
import com.yealink.uc.common.modules.account.entity.Account;
import com.yealink.uc.platform.utils.DataConverter;
import com.yealink.uc.platform.utils.EntityUtil;

import org.springframework.stereotype.Repository;

import static com.yealink.dataservice.client.util.Filter.and;
import static com.yealink.dataservice.client.util.Filter.eq;
import static com.yealink.dataservice.client.util.Filter.gte;

/**
 * @author ChNan
 */
@Repository
public class AccountDao {
    private IRemoteDataService remoteDataService = RemoteServiceFactory.getInstance().getRemoteDataService();
    private static final String ACCOUNT_ENTITY_NAME = EntityUtil.getEntityName(Account.class);

    public long save(Account account) {
        return remoteDataService.insertOne(ACCOUNT_ENTITY_NAME, account);
    }

    public Account findByUsername(String userName) {
        Map<String, Object> accountMap = remoteDataService.queryOne(ACCOUNT_ENTITY_NAME, null, eq("username", userName).toMap());
        return DataConverter.copyFromMap(new Account(), accountMap);
    }

    public Account findByUsernameDomain(String userName, String domain) {
        Map<String, Object> accountMap = remoteDataService.queryOne(ACCOUNT_ENTITY_NAME, null, and(eq("username", userName), eq("domain", domain)).toMap());
        return DataConverter.copyFromMap(new Account(), accountMap);
    }

    public Account get(Long id) {
        Map<String, Object> accountMap = remoteDataService.queryOne(ACCOUNT_ENTITY_NAME, null, eq("_id", id).toMap());
        return DataConverter.copyFromMap(new Account(), accountMap);
    }


    public Account findByActiveCodeAndResetDate(String activeCode, long overdueDate) {
        Map<String, Object> accountMap = remoteDataService.queryOne(ACCOUNT_ENTITY_NAME, null, and(eq("activeCode", activeCode), gte("resetPasswordDate", overdueDate)).toMap());
        return DataConverter.copyFromMap(new Account(), accountMap);
    }

    public long updateAccount(Account account) {
        return remoteDataService.updateOne(ACCOUNT_ENTITY_NAME, account.get_id(), account);
    }

}
