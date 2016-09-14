package com.yealink.uc.web.modules.common.producer;

import com.yealink.dataservice.client.RemoteServiceFactory;
import com.yealink.dataservice.client.util.Event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class MessageProducer {
    RemoteServiceFactory remoteServiceFactory = RemoteServiceFactory.getInstance();
    Logger logger = LoggerFactory.getLogger(MessageProducer.class);

    public void publishEvent(Event event) {
        remoteServiceFactory.getMQSender().publishEvent(event);
    }

    protected Event createEvent(String eventType) {
        logger.info("Send message: Source=" + getSource() + ", Topic=" + getTopic() + ",eventType=" + eventType);
        Event event = new Event();
        event.setSource(getSource());
        event.setEventTime(System.currentTimeMillis());
        event.setOperation(eventType);
        event.setTopic(getTopic());
        return event;
    }

    protected abstract String getSource();

    protected abstract String getTopic();
}
