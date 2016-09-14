package com.yealink.ofweb.modules.immanager.request;

/**
 * Created by chenkl on 2016/8/24.
 */
public class LocaleBean {
    private String localeCode;
    private String timezonesId;
    private boolean isSync;
    private String dateTime;
    private String timeDomain;
    //夏令时是否开启
    private boolean dst;
    public LocaleBean() {
    }

    public String getLocaleCode() {
        return localeCode;
    }

    public void setLocaleCode(String localeCode) {
        this.localeCode = localeCode;
    }

    public String getTimezonesId() {
        return timezonesId;
    }

    public void setTimezonesId(String timezonesId) {
        this.timezonesId = timezonesId;
    }

    public boolean getIsSync() {
        return this.isSync;
    }

    public void setIsSync(boolean isSync) {
        this.isSync = isSync;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getTimeDomain() {
        return timeDomain;
    }

    public void setTimeDomain(String timeDomain) {
        this.timeDomain = timeDomain;
    }

    public boolean isDst() {
        return dst;
    }

    public void setDst(boolean dst) {
        this.dst = dst;
    }
}
