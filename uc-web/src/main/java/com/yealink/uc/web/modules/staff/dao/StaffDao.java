package com.yealink.uc.web.modules.staff.dao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.yealink.dataservice.client.IRemoteDataService;
import com.yealink.dataservice.client.RemoteServiceFactory;
import com.yealink.dataservice.client.util.OrderBy;
import com.yealink.dataservice.client.util.Pager;
import com.yealink.uc.common.modules.staff.entity.Staff;
import com.yealink.uc.common.modules.staff.vo.StaffStatus;
import com.yealink.uc.platform.utils.DataConverter;
import com.yealink.uc.platform.utils.DateUtil;
import com.yealink.uc.platform.utils.EntityUtil;
import com.yealink.uc.platform.utils.StringUtil;
import com.yealink.uc.platform.web.pager.PagerRequest;
import com.yealink.uc.web.modules.staff.request.QueryStaffRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Transformer;
import org.springframework.stereotype.Repository;

import static com.yealink.dataservice.client.util.Filter.and;
import static com.yealink.dataservice.client.util.Filter.eq;
import static com.yealink.dataservice.client.util.Filter.gte;
import static com.yealink.dataservice.client.util.Filter.in;
import static com.yealink.dataservice.client.util.Filter.lt;
import static com.yealink.dataservice.client.util.Filter.ne;
import static com.yealink.dataservice.client.util.Filter.or;
import static com.yealink.dataservice.client.util.Filter.regex;

/**
 * @author ChNan
 */
@Repository
@SuppressWarnings("unchecked")
public class StaffDao {
    public static final String STAFF_ENTITY_STAFF_NAME = EntityUtil.getEntityName(Staff.class); // This is for openfire temporary, will be removed future
    private RemoteServiceFactory remoteServiceFactory = RemoteServiceFactory.getInstance();
    private IRemoteDataService remoteDataService = remoteServiceFactory.getRemoteDataService();

    public List<Staff> findAllAvailable(Long enterpriseId) {
        List<Map<String, Object>> staffMapList = remoteDataService.query(STAFF_ENTITY_STAFF_NAME, null, and(eq("enterpriseId", enterpriseId), gte("status", StaffStatus.LOCKED.getCode())).toMap());
        return convertToStaffs(staffMapList);
    }

    public List<Staff> findStaffs(List<Long> staffIdList, Long enterpriseId) {
        List<Map<String, Object>> staffMapList = remoteDataService.query(STAFF_ENTITY_STAFF_NAME, null, and(eq("enterpriseId", enterpriseId), in("_id", staffIdList)).toMap());
        return convertToStaffs(staffMapList);
    }
    public List<Staff> find(StaffStatus status, Long enterpriseId) {
        List<Map<String, Object>> staffMapList = remoteDataService.query(STAFF_ENTITY_STAFF_NAME, null, and(eq("enterpriseId", enterpriseId), eq("status", status.getCode())).toMap());
        return convertToStaffs(staffMapList);
    }
    public long count(StaffStatus status, Long enterpriseId) {
        return remoteDataService.queryCount(STAFF_ENTITY_STAFF_NAME, and(eq("enterpriseId", enterpriseId), eq("status", status.getCode())).toMap());
    }

    public Staff findByEmail(String email) {
        Map<String, Object> resultMap = remoteDataService.queryOne(STAFF_ENTITY_STAFF_NAME, null, eq("email", email).toMap());
        return DataConverter.copyFromMap(new Staff(), resultMap);
    }

    public List<Long> findStaffIds(List<Long> staffIdList, Long enterpriseId) {
        List<Map<String, Object>> idMapList = remoteDataService.query(STAFF_ENTITY_STAFF_NAME, "_id", and(eq("enterpriseId", enterpriseId), in("_id", staffIdList)).toMap());
        if (CollectionUtils.isEmpty(idMapList)) {
            return null;
        }
        return (List<Long>) CollectionUtils.collect(idMapList, new Transformer() {
            @Override
            public Long transform(Object input) {
                Number result = ((Map<String, Long>) input).get("_id");
                return result.longValue();
            }
        });
    }

    public List<Long> findByStatus(List<Long> staffIdList, Long enterpriseId, StaffStatus... statuses) {
        List<Integer> statusCode = new ArrayList<>();
        for (StaffStatus staffStatus : statuses) {
            statusCode.add(staffStatus.getCode());
        }
        List<Map<String, Object>> idMapList = remoteDataService.query(STAFF_ENTITY_STAFF_NAME, "_id", and(eq("enterpriseId", enterpriseId), in("_id", staffIdList), in("status", statusCode)).toMap());
        if (CollectionUtils.isEmpty(idMapList)) {
            return null;
        }
        return (List<Long>) CollectionUtils.collect(idMapList, new Transformer() {
            @Override
            public Long transform(Object input) {
                Number result = ((Map<String, Long>) input).get("_id");
                return result.longValue();
            }
        });
    }

    public Long countAvailableStaff(List<Long> staffIdList, Long enterpriseId) {
        return remoteDataService.queryCount(STAFF_ENTITY_STAFF_NAME, and(gte("status", StaffStatus.LOCKED.getCode()), eq("enterpriseId", enterpriseId), in("_id", staffIdList)).toMap());
    }

    private List<Staff> convertToStaffs(List<Map<String, Object>> staffMapList) {
        if (CollectionUtils.isEmpty(staffMapList)) return null;
        return (List<Staff>) CollectionUtils.collect(staffMapList, new Transformer() {
            @Override
            public Object transform(final Object input) {
                return DataConverter.copyFromMap(new Staff(), (Map<String, Object>) input);
            }
        });
    }

    //TODO 后续需要去掉
    public Staff get(Long staffId) {
        return get(staffId, 1L);
    }

    public Staff get(Long staffId, Long enterpriseId) {
        Map<String, Object> staffMap = remoteDataService.queryOne(STAFF_ENTITY_STAFF_NAME, null, and(eq("_id", staffId), eq("enterpriseId", enterpriseId)).toMap());
        if (staffMap == null) return null;
        return DataConverter.copyFromMap(new Staff(), staffMap);
    }

    public Long countUsername(String username, Long enterpriseId) {
        return remoteDataService.queryCount(STAFF_ENTITY_STAFF_NAME, and(eq("username", username), eq("enterpriseId", enterpriseId), gte("status", StaffStatus.RECYCLE.getCode())).toMap());
    }


    public long save(Staff staff) {
        return remoteDataService.insertOne(STAFF_ENTITY_STAFF_NAME, staff);
    }


    public long updateStaff(Staff staff) {
        return remoteDataService.updateOne(STAFF_ENTITY_STAFF_NAME, staff.get_id(), staff);
    }

    public long updateStaffStatus(Long staffId, Long enterpriseId, StaffStatus status, StaffStatus... oldStatus) {
        return batchUpdateStaffStatus(Collections.singletonList(staffId), enterpriseId, status, oldStatus);
    }

    public long batchUpdateStaffStatus(List<Long> staffIds, Long enterpriseId, StaffStatus status, StaffStatus... oldStatus) {
        Map<String, Object> updateMap = new HashMap<>();
        updateMap.put("status", status.getCode());
        updateMap.put("modificationDate", DateUtil.currentDate().getTime());
        List<Integer> oldStatusCode = new ArrayList<>();
        for (StaffStatus staffStatus : oldStatus) {
            oldStatusCode.add(staffStatus.getCode());
        }
        return remoteDataService.updateMany(STAFF_ENTITY_STAFF_NAME,
            and(eq("enterpriseId", enterpriseId), in("_id", staffIds), in("status", oldStatusCode)).toMap(), updateMap);
    }

    public Long updateStaffPassword(Long staffId, Long enterpriseId, String newPassword) {
        Map<String, Object> updateMap = new HashMap<>();
        updateMap.put("encryptedPassword", newPassword);
        updateMap.put("modificationDate", DateUtil.currentDate().getTime());
        return remoteDataService.updateMany(STAFF_ENTITY_STAFF_NAME, and(eq("_id", staffId), eq("enterpriseId", enterpriseId)).toMap(), updateMap);
    }

    public List<String> findExistUsername(List<String> usernameList, Long enterpriseId) {
        List<Map<String, Object>> usernameMapList = remoteDataService.query(STAFF_ENTITY_STAFF_NAME, "username",
            and(eq("enterpriseId", enterpriseId), ne("status", StaffStatus.LOCKED.getCode()), in("username", usernameList)).toMap());
        if (usernameMapList == null || usernameMapList.isEmpty()) {
            return null;
        }
        return (List<String>) CollectionUtils.collect(usernameMapList, new Transformer() {
            @Override
            public Object transform(Object input) {
                return ((Map<String, Object>) input).get("username");
            }
        });
    }

    public long batchCreateStaff(List<Staff> staffs) {
        if (staffs == null || staffs.isEmpty()) {
            return 0;
        }
        return remoteDataService.insertMany(STAFF_ENTITY_STAFF_NAME, staffs);
    }

    public List<Staff> queryStaffList(QueryStaffRequest queryStaffRequest, Long enterpriseId) {
        Map conMap = createQueryStaffCondition(queryStaffRequest, enterpriseId);
        Pager<Map<String, Object>> pager = new Pager();
        pager.setAutoCount(true);
        pager.setSkip(queryStaffRequest.getSkip());
        pager.setLimit(queryStaffRequest.getPageSize());
        pager.setOrderbys(Collections.singletonList(new OrderBy(queryStaffRequest.getOrderByField(), queryStaffRequest.getOrderByType())));
        pager = remoteDataService.query(STAFF_ENTITY_STAFF_NAME, null, pager, conMap);
        return convertToStaffs(pager.getData());
    }

    public long countStaffList(QueryStaffRequest queryStaffRequest, Long enterpriseId) {
        Map conMap = createQueryStaffCondition(queryStaffRequest, enterpriseId);
        return remoteDataService.queryCount(STAFF_ENTITY_STAFF_NAME, conMap);
    }

    private Map createQueryStaffCondition(final QueryStaffRequest queryStaffRequest, final Long enterpriseId) {
        List<Map<String, Object>> andArray = new ArrayList<>();
        andArray.add(eq("enterpriseId", enterpriseId).toMap());
        String queryPattern = ".*" + queryStaffRequest.getSearchKey() + ".*";
        if (!StringUtil.isStrEmpty(queryStaffRequest.getSearchKey())) {
            andArray.add(or(regex("username", queryPattern), regex("name", queryPattern)).toMap());
        }
        if (queryStaffRequest.getInitStatusList() == null) {
            andArray.add(lt("status", 0).toMap());
        } else {
            andArray.add(in("status", queryStaffRequest.getInitStatusList()).toMap());
        }
        if (queryStaffRequest.getSelectedStatus() != null) {
            if (queryStaffRequest.getSelectedStatus().equals(0)) {
                andArray.add(lt("status", 0).toMap());
            } else {
                andArray.add(eq("status", queryStaffRequest.getSelectedStatus()).toMap());
            }
        }
        if (!CollectionUtils.isEmpty(queryStaffRequest.getStaffIds())) {
            andArray.add(in("_id", queryStaffRequest.getStaffIds()).toMap());
        }
        Map conMap = new HashMap();
        conMap.put("$and", andArray);
        return conMap;
    }


    public Pager<Map<String, Object>> searchStaffList(Map conMap, PagerRequest pagerRequest) {
        Pager<Map<String, Object>> pager = new Pager();
        pager.setAutoCount(true);
        pager.setSkip(pagerRequest.getSkip());
        pager.setLimit(pagerRequest.getPageSize());
        pager.setOrderbys(Collections.singletonList(new OrderBy(pagerRequest.getOrderByField(), pagerRequest.getOrderByType())));
        pager = remoteDataService.query(STAFF_ENTITY_STAFF_NAME, null, pager, conMap);
        return pager;
    }

    public long countSearchStaffList(Map conMap) {
        return remoteDataService.queryCount(STAFF_ENTITY_STAFF_NAME, conMap);
    }

}
