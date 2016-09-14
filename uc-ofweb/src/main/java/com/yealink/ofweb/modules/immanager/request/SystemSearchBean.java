package com.yealink.ofweb.modules.immanager.request;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * Created by chenkl on 2016/8/16.
 */
public class SystemSearchBean {
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }
}
