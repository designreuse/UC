package com.yealink.imweb.modules.common.dao;

import com.yealink.dataservice.client.RemoteServiceFactory;
import org.springframework.stereotype.Repository;

/**
 * @author ChNan
 */
@Repository
public class IdGeneratorDao {
    private RemoteServiceFactory remoteServiceFactory = RemoteServiceFactory.getInstance();

    public Long nextId(String entityName) {
        return remoteServiceFactory.getIdGenerator().nextId(entityName);
    }

    // for staff
    public Long nextId(Integer entityCode) {
        return remoteServiceFactory.getIdGenerator().nextId(entityCode);
    }


}
