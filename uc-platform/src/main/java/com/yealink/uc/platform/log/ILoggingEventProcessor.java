package com.yealink.uc.platform.log;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.spi.ILoggingEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.yealink.uc.platform.log.action.ActionLog;

import org.apache.commons.lang3.RandomStringUtils;

public class ILoggingEventProcessor {
    private boolean hold = true;
    private Writer writer;
    List<ILoggingEvent> events = new ArrayList<>();
    private String logFolder;
    private ActionLog actionLog = new ActionLog();

    public ILoggingEventProcessor(String logFolder) {
        this.logFolder = logFolder;
    }

    public void process(ILoggingEvent event) {
        if (hold) {
            events.add(event);
            if (needFlush(event)) { // flushTraceLogs and write to file until level greater equal than error, then always write to file.
                flushTraceLogs();
                hold = false;
            }
        } else {
            write(event);
        }
    }

    public boolean needFlush(ILoggingEvent event) {
        return event.getLevel().isGreaterOrEqual(Level.WARN);
    }

    public void flushTraceLogs() {
        if (writer == null)
            createWriter();
        for (ILoggingEvent event : events) {
            write(event);
        }
        events.clear(); // clear events when error | warning happened ,write to log directly next time.
    }

    private void write(ILoggingEvent event) {
        try {
            writer.write(FilterMessagePattern.get().doLayout(event));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void createWriter() {
        try {
            File logFile = generateLogFolder();
            actionLog.logContext("log_path", logFile.getPath());
            createParentFolder(logFile);
            writer = new BufferedWriter(new FileWriter(logFile));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createParentFolder(final File logFile) {
        logFile.getParentFile().mkdirs();
    }

    private File generateLogFolder() {
        String sequence = RandomStringUtils.randomAlphabetic(5);
        return new File(String.format("%1$s/%2$tY/%2$tm/%2$td/%3$s/%2$tk-%2$tM-%2$tS-%2$tL.%4$s.%5$s.log",
            logFolder,
            new Date(),
            actionLog.getAction() == null ? "unknown" : actionLog.getAction(),
            actionLog.getRequestId() == null ? "unknown" : actionLog.getRequestId(),
            sequence));
    }

    public void cleanup() {
        if (logFolder != null && writer != null) {
            try {
                writer.flush();
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public ActionLog getActionLog() {
        return actionLog;
    }
}
