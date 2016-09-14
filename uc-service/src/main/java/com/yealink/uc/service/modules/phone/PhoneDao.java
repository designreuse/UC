package com.yealink.uc.service.modules.phone;

import java.util.List;
import java.util.Map;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.yealink.dataservice.client.IRemoteDataService;
import com.yealink.dataservice.client.RemoteServiceFactory;
import com.yealink.uc.common.modules.phone.Phone;
import com.yealink.uc.platform.utils.DataConverter;
import com.yealink.uc.platform.utils.EntityUtil;

import org.springframework.stereotype.Repository;

import static com.yealink.dataservice.client.util.Filter.in;

/**
 * @author ChNan
 */
@SuppressWarnings("unchecked")
@Repository
public class PhoneDao {
    private IRemoteDataService remoteDataService = RemoteServiceFactory.getInstance().getRemoteDataService();
    private static final String PHONE_ENTITY_NAME = EntityUtil.getEntityName(Phone.class);

    public List<Phone> findByStaffs(List<Long> staffIds) {
        List<Map<String, Object>> results = remoteDataService.query(PHONE_ENTITY_NAME, null, in("staffId", staffIds).toMap());
        return Lists.transform(results, new Function<Map<String, Object>, Phone>() {
            @Override
            public Phone apply(final Map<String, Object> input) {
                return DataConverter.copyFromMap(new Phone(),input);
            }
        });
    }
}
