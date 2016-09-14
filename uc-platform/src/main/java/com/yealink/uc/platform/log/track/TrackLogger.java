package com.yealink.uc.platform.log.track;

import ch.qos.logback.classic.spi.ILoggingEvent;

import com.yealink.uc.platform.log.ILoggingEventProcessor;
import com.yealink.uc.platform.log.action.ActionLog;
import com.yealink.uc.platform.log.action.ActionResult;
import com.yealink.uc.platform.log.action.IActionLogger;

public class TrackLogger implements IActionLogger {
    private ThreadLocal<ILoggingEventProcessor> loggingEventProcessor = new ThreadLocal<>();
    private static TrackLogger INSTANCE = new TrackLogger();
    private String logFolder;

    public static TrackLogger get() {
        return INSTANCE;
    }

    public void process(ILoggingEvent event) {
        ILoggingEventProcessor processor = loggingEventProcessor.get();
        if (null != processor) {
            processor.process(event);
        }
    }

    public void cleanUp() {
        ILoggingEventProcessor processor = loggingEventProcessor.get();
        processor.getActionLog().save(); // create logAction log
        processor.cleanup(); // finally close writer to end write trace log
        loggingEventProcessor.remove();
    }

    public void initialize() {
        loggingEventProcessor.set(new ILoggingEventProcessor(logFolder));
    }


    public void setLogFolder(final String logFolder) {
        this.logFolder = logFolder;
    }

    public void action(String action) {
        ActionLog actionLog = loggingEventProcessor.get().getActionLog();
        actionLog.logAction(action);
    }

    public void requestId(String requestId) {
        ActionLog actionLog = loggingEventProcessor.get().getActionLog();
        actionLog.logRequestId(requestId);
    }

    public void result(ActionResult result) {
        ActionLog actionLog = loggingEventProcessor.get().getActionLog();
        actionLog.logResult(result);
    }

    @Override
    public void logContext(final String key, final String value) {
        ActionLog actionLog = loggingEventProcessor.get().getActionLog();
        actionLog.logContext(key, value);
    }
}
