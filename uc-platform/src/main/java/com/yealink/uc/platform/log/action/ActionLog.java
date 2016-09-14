package com.yealink.uc.platform.log.action;

import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

import com.yealink.uc.platform.utils.DateUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ActionLog implements IActionLogger {
    private static final String LOG_DATE_FORMAT = "yyyy-MM-dd-HH-mm-ss-SSS";
    private static final String LOG_SPLITER = " | ";
    private String action;
    private String requestId;
    private ActionResult result = ActionResult.SUCCESS;
    private Date requestDate = new Date();
    Logger logger = LoggerFactory.getLogger(ActionLog.class);
    Map<String, String> context = new TreeMap<>();

    @Override
    public void logContext(String key, String value) {
        logger.debug("{}={}", key, value);
        context.put(key, value);
    }

    public void logAction(String action) {
        this.action = action;
        logger.debug("action={}", action);
    }

    public void logRequestId(String requestId) {
        this.requestId = requestId;
        logger.debug("requestId={}", requestId);
    }

    public void logResult(ActionResult result) {
        this.result = result;
        logger.debug("result={}", result);
    }

    public void save() {
        logContext("elapsedTime", String.valueOf(System.currentTimeMillis() - requestDate.getTime()));
        LoggerFactory.getLogger("action").info(buildActionLog());
    }

    private String buildActionLog() {
        StringBuilder actionLogBuilder = new StringBuilder();
        actionLogBuilder.append("ts=").append(DateUtil.toString(requestDate, LOG_DATE_FORMAT))
            .append(LOG_SPLITER)
            .append("requestId=").append(this.requestId != null ? this.requestId : "unknown")
            .append(LOG_SPLITER)
            .append("action=").append(this.action)
            .append(LOG_SPLITER);
        for (Map.Entry<String, String> entry : context.entrySet()) {
            actionLogBuilder.append(entry.getKey()).append("=").append(entry.getValue())
                .append(LOG_SPLITER);
        }
        return actionLogBuilder.toString();
    }

    public String getAction() {
        return action;
    }

    public String getRequestId() {
        return requestId;
    }

    public ActionResult getResult() {
        return result;
    }
}
