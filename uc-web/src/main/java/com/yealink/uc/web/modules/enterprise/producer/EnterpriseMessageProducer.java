package com.yealink.uc.web.modules.enterprise.producer;

import com.yealink.dataservice.client.util.Event;
import com.yealink.uc.web.modules.enterprise.listener.IEnterpriseEventListener;
import com.yealink.uc.web.modules.common.producer.MessageProducer;

import org.springframework.stereotype.Service;

/**
 * @author ChNan
 */
@Service
public class EnterpriseMessageProducer extends MessageProducer implements IEnterpriseEventListener {
    private static final String SOURCE_ENTERPRISE = "Enterprise";

    @Override
    public void enterpriseEdited(final Long enterpriseId) {
        Event event = createEvent("edit");
        event.setResourceId(enterpriseId);
        publishEvent(event);
    }

    @Override
    protected String getSource() {
        return  SOURCE_ENTERPRISE;
    }

    @Override
    protected String getTopic() {
        return Event.TOPIC_ORG_SERVICE;
    }
}
