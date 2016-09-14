package com.yealink.uc.web.modules.account.dao;

import java.util.List;
import java.util.Map;

import com.yealink.dataservice.client.IRemoteDataService;
import com.yealink.dataservice.client.RemoteServiceFactory;
import com.yealink.uc.common.modules.account.entity.Account;
import com.yealink.uc.platform.utils.DataConverter;
import com.yealink.uc.platform.utils.EntityUtil;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Transformer;
import org.springframework.stereotype.Repository;

import static com.yealink.dataservice.client.util.Filter.and;
import static com.yealink.dataservice.client.util.Filter.eq;
import static com.yealink.dataservice.client.util.Filter.gte;
import static com.yealink.dataservice.client.util.Filter.in;

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

    public long batchSave(List<Account> accountList) {
        return remoteDataService.insertMany(ACCOUNT_ENTITY_NAME, accountList);
    }

    public Account findByUsername(String userName, Long enterpriseId) {
        Map<String, Object> accountMap = remoteDataService.queryOne(ACCOUNT_ENTITY_NAME, null, and(eq("username", userName), eq("enterpriseId", enterpriseId)).toMap());
        return DataConverter.copyFromMap(new Account(), accountMap);
    }

    public Account findByUsername(String userName) {
        Map<String, Object> accountMap = remoteDataService.queryOne(ACCOUNT_ENTITY_NAME, null, eq("username", userName).toMap());
        return DataConverter.copyFromMap(new Account(), accountMap);
    }

    public List<Account> findByStaffId(long staffId) {
        List<Map<String, Object>> accountMapList = remoteDataService.query(ACCOUNT_ENTITY_NAME, null, eq("staffId", staffId).toMap());
        if (accountMapList == null || accountMapList.isEmpty()) {
            return null;
        }
        return converAccountList(accountMapList);
    }

    public Account getByStaffAndType(long staffId, String type) {
        Map<String, Object> accountMap = remoteDataService.queryOne(ACCOUNT_ENTITY_NAME, null, and(eq("staffId", staffId), eq("type", type)).toMap());
        if (accountMap == null) {
            return null;
        }
        return DataConverter.copyFromMap(new Account(), accountMap);
    }

    public List<Account> findByStaffAndType(List<Long> staffIds, String type) {
        List<Map<String, Object>> accountMapList = remoteDataService.query(ACCOUNT_ENTITY_NAME, null, and(in("staffId", staffIds), eq("type", type)).toMap());
        if (accountMapList == null || accountMapList.isEmpty()) {
            return null;
        }
        return converAccountList(accountMapList);
    }

    public Account get(Long id) {
        Map<String, Object> accountMap = remoteDataService.queryOne(ACCOUNT_ENTITY_NAME, null, eq("_id", id).toMap());
        return DataConverter.copyFromMap(new Account(), accountMap);
    }


    public List<Account> findByActiveCodeAndResetDate(String activeCode, long overdueDate) {
        List<Map<String, Object>> accountMapList = remoteDataService.query(ACCOUNT_ENTITY_NAME, null, and(eq("activeCode", activeCode), gte("activeCodeExpiredDate", overdueDate)).toMap());
        return converAccountList(accountMapList);
    }

    public long updateAccount(Account account) {
        return remoteDataService.updateOne(ACCOUNT_ENTITY_NAME, account.get_id(), account);
    }

    public long updateAccounts(List<Account> accountList) {
        long count = 0;
        for (Account account : accountList) {
            count += updateAccount(account);
        }
        return count;
    }

    private List<Account> converAccountList(List<Map<String, Object>> accountMapList) {
        return (List<Account>) CollectionUtils.collect(accountMapList, new Transformer() {
            @Override
            public Account transform(Object input) {
                return DataConverter.copyFromMap(new Account(), (Map<String, Object>) input);
            }
        });
    }
}
