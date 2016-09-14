package com.yealink.uc.service.modules.staff.dao;

import java.util.List;
import java.util.Map;

import com.yealink.dataservice.client.IRemoteDataService;
import com.yealink.dataservice.client.RemoteServiceFactory;
import com.yealink.uc.common.modules.staff.entity.Staff;
import com.yealink.uc.common.modules.staff.vo.StaffStatus;
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
@SuppressWarnings("unchecked")
public class StaffDao {
    public static final String STAFF_ENTITY_NAME = EntityUtil.getEntityName(Staff.class);
    private IRemoteDataService remoteDataService = RemoteServiceFactory.getInstance().getRemoteDataService();

    public Staff get(Long staffId, Long enterpriseId) {
        Map<String, Object> staffMap = remoteDataService.queryOne(STAFF_ENTITY_NAME, null, and(eq("_id", staffId), eq("enterpriseId", enterpriseId)).toMap());
        if (staffMap == null) return null;
        return DataConverter.copyFromMap(new Staff(), staffMap);
    }

    public Staff get(Long staffId) {
        Map<String, Object> staffMap = remoteDataService.queryOne(STAFF_ENTITY_NAME, null, eq("_id", staffId).toMap());
        if (staffMap == null) return null;
        return DataConverter.copyFromMap(new Staff(), staffMap);
    }

    public List<Staff> findAllAvailable(Long enterpriseId) {
        List<Map<String, Object>> staffMapList = remoteDataService.query(STAFF_ENTITY_NAME, null, and(eq("enterpriseId", enterpriseId), gte("status", StaffStatus.LOCKED.getCode())).toMap());
        return convertToStaffs(staffMapList);
    }

    public List<Staff> findStaffs(List<Long> staffIdList, Long enterpriseId) {
        List<Map<String, Object>> staffMapList = remoteDataService.query(STAFF_ENTITY_NAME, null, and(eq("enterpriseId", enterpriseId), in("_id", staffIdList)).toMap());
        return convertToStaffs(staffMapList);
    }

    public List<Staff> findStaffs(List<Long> staffIdList) {
        List<Map<String, Object>> staffMapList = remoteDataService.query(STAFF_ENTITY_NAME, null, in("_id", staffIdList).toMap());
        return convertToStaffs(staffMapList);
    }


    public List<Long> findStaffIds(List<Long> staffIdList, Long enterpriseId) {
        List<Map<String, Object>> idMapList = remoteDataService.query(STAFF_ENTITY_NAME, "_id", and(eq("enterpriseId", enterpriseId), in("_id", staffIdList)).toMap());
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

    private List<Staff> convertToStaffs(List<Map<String, Object>> staffMapList) {
        if (CollectionUtils.isEmpty(staffMapList)) return null;
        return (List<Staff>) CollectionUtils.collect(staffMapList, new Transformer() {
            @Override
            public Object transform(final Object input) {
                return DataConverter.copyFromMap(new Staff(), (Map<String, Object>) input);
            }
        });
    }
}
