package com.yealink.uc.web.modules.phone;

import java.util.Map;

import com.yealink.dataservice.client.IRemoteDataService;
import com.yealink.dataservice.client.RemoteServiceFactory;
import com.yealink.uc.common.modules.phone.Phone;
import com.yealink.uc.platform.utils.DataConverter;
import com.yealink.uc.platform.utils.EntityUtil;

import org.springframework.stereotype.Repository;

import static com.yealink.dataservice.client.util.Filter.eq;

/**
 * @author ChNan
 */
@SuppressWarnings("unchecked")
@Repository
public class PhoneDao {
    private IRemoteDataService remoteDataService = RemoteServiceFactory.getInstance().getRemoteDataService();
    private static final String PHONE_ENTITY_NAME = EntityUtil.getEntityName(Phone.class);

    public long save(Phone phone) {
        return remoteDataService.insertOne(PHONE_ENTITY_NAME, phone);
    }

    public long update(Phone phone) {
        return remoteDataService.updateOne(PHONE_ENTITY_NAME, phone.get_id(), phone);
    }

    public Phone getByStaff(Long staffId) {
        Map<String, Object> result = remoteDataService.queryOne(PHONE_ENTITY_NAME, null, eq("staffId", staffId).toMap());
        return DataConverter.copyFromMap(new Phone(), result);
    }
}
