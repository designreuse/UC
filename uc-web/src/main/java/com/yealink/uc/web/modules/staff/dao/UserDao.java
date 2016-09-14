package com.yealink.uc.web.modules.staff.dao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.yealink.dataservice.client.IRemoteDataService;
import com.yealink.dataservice.client.RemoteServiceFactory;
import com.yealink.uc.common.modules.staff.entity.User;
import com.yealink.uc.common.modules.staff.vo.StaffStatus;
import com.yealink.uc.platform.utils.DateUtil;

import org.springframework.stereotype.Repository;

import static com.yealink.dataservice.client.util.Filter.and;
import static com.yealink.dataservice.client.util.Filter.eq;
import static com.yealink.dataservice.client.util.Filter.in;

/**
 * @author ChNan
 *
 * 为了暂时兼容IM服务器端的user表，所以在对Staff增删改的时候一并修改User表，到后面User废弃的时候直接把类去掉就可以
 */
@Repository
@SuppressWarnings("unchecked")
public class UserDao {
    public static final String STAFF_ENTITY_USER_NAME = "User";
    private RemoteServiceFactory remoteServiceFactory = RemoteServiceFactory.getInstance();
    private IRemoteDataService remoteDataService = remoteServiceFactory.getRemoteDataService();

    public long save(User user) {
        return remoteDataService.insertOne(STAFF_ENTITY_USER_NAME, user);
    }

    public long updateUser(User user) {
        return remoteDataService.updateOne(STAFF_ENTITY_USER_NAME, user.get_id(), user);
    }

    public long updateUserStatus(Long staffId, Long enterpriseId, StaffStatus status, StaffStatus... oldStatus) {
        return batchUpdateUserStatus(Collections.singletonList(staffId), enterpriseId, status, oldStatus);
    }

    public long batchUpdateUserStatus(List<Long> staffIds, Long enterpriseId, StaffStatus status, StaffStatus... oldStatus) {
        Map<String, Object> updateMap = new HashMap<>();
        updateMap.put("status", status.getCode());
        updateMap.put("modificationDate", DateUtil.currentDate().getTime());
        List<Integer> oldStatusCode = new ArrayList<>();
        for (StaffStatus staffStatus : oldStatus) {
            oldStatusCode.add(staffStatus.getCode());
        }
        return remoteDataService.updateMany(STAFF_ENTITY_USER_NAME,
            and(eq("enterpriseId", enterpriseId), in("_id", staffIds), in("status", oldStatusCode)).toMap(), updateMap);
    }

    public long batchCreateStaff(List<User> users) {
        if (users == null || users.isEmpty()) {
            return 0;
        }
        return remoteDataService.insertMany(STAFF_ENTITY_USER_NAME, users);
    }

}
