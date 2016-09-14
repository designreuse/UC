package com.yealink.uc.platform.log.track;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.UnsynchronizedAppenderBase;

import com.yealink.uc.platform.log.FilterMessagePattern;

public class TrackAppender extends UnsynchronizedAppenderBase<ILoggingEvent> {

    @Override
    protected void append(final ILoggingEvent event) {
        TrackLogger.get().process(event);
    }

    @Override
    public void start() {
        super.start();
        FilterMessagePattern.get().setContext(getContext());
        FilterMessagePattern.get().start();
    }

    @Override
    public void stop() {
        super.stop();
        FilterMessagePattern.get().stop();
    }

    public void setLogFolder(String logFolder) {
        TrackLogger.get().setLogFolder(logFolder);
    }
}
