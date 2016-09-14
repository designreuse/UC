package com.yealink.uc.web.modules.org.producer;

import java.util.HashMap;
import java.util.Map;

import com.yealink.dataservice.client.util.Event;
import com.yealink.uc.common.modules.org.entity.Org;
import com.yealink.uc.common.modules.org.vo.OrgEventType;
import com.yealink.uc.web.modules.common.producer.MessageProducer;
import com.yealink.uc.web.modules.org.listener.IOrgEventListener;

import org.springframework.stereotype.Service;

/**
 * @author ChNan
 */
@Service
public class OrgMessageProducer extends MessageProducer implements IOrgEventListener {
    private static String SOURCE_ORG = "Org";

    @Override
    public void createOrg(Long orgId) {
        Event event = createEvent(OrgEventType.CREATE.name());
        event.setResourceId(orgId);
        publishEvent(event);
    }

    @Override
    public void editOrg(Long orgId) {
        Event event = createEvent(OrgEventType.EDIT.name());
        event.setResourceId(orgId);
        publishEvent(event);
    }

    @Override
    public void deleteOrg(Org org) {
        Event event = createEvent(OrgEventType.DELETE.name());
        event.setResourceId(org.get_id());
        Map<String, Object> map = new HashMap<>();
        map.put("name", org.getName());
        map.put("parentId", org.getParentId());
        event.setExValue(map);
        publishEvent(event);
    }

    @Override
    public void moveOrg(Org org, Long oldParentId) {
        Event event = createEvent(OrgEventType.MOVE.name());
        event.setResourceId(org.get_id());
        Map<String, Object> map = new HashMap<>();
        map.put("oldParentId", oldParentId);
        map.put("newParentId", org.getParentId());
        event.setExValue(map);
        publishEvent(event);
    }

    @Override
    protected String getSource() {
        return SOURCE_ORG;
    }

    @Override
    protected String getTopic() {
        return Event.TOPIC_ORG_SERVICE;
    }
}
