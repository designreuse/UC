package com.yealink.ofweb.modules.immanager.request;

import java.util.List;
import java.util.Map;

/**
 * Created by chenkl on 2016/8/18.
 * 负责监控相关属性配置的类，包括cpu和内存报警阈值设置，数据库保存cpu和内存的天数设置等
 */
public class MonitorProperties {
    //cpu报警阈值
    private double cpuAlarmValue;
    //memory报警阈值
    private double memoryAlarmValue;
    //cpu使用率阈值持续时间
    private int cpuDuration;
    //内存使用率阈值持续时间
    private int memoryDuration;
    //数据库中系统监控数据保存的天数，此处以秒为单位，默认30天
    private long systemDataSaveSecond = 30*24*60*60;
    //数据库中Openfire包统计数据保存天数的设置参数，以秒为单位，默认30天
    private long ofDataSaveSecond = 30*24*60*60;
    //被通知人的用户id数组
    private List<String> userIds;

    private boolean isEmailForCpu;

    private boolean isIMForCpu;

    private boolean isEmailForMemory;

    private boolean isIMForMemory;

    public MonitorProperties() {
        systemDataSaveSecond= 30*24*60*60;
        ofDataSaveSecond = 30*24*60*60;
        cpuAlarmValue = 95;
        memoryAlarmValue = 95;
        cpuDuration =60*5;
        memoryDuration =60*5;
    }

    public double getCpuAlarmValue() {
        return cpuAlarmValue;
    }

    public void setCpuAlarmValue(double cpuAlarmValue) {
        this.cpuAlarmValue = cpuAlarmValue;
    }

    public double getMemoryAlarmValue() {
        return memoryAlarmValue;
    }

    public void setMemoryAlarmValue(double memoryAlarmValue) {
        this.memoryAlarmValue = memoryAlarmValue;
    }

    public int getCpuDuration() {
        return cpuDuration;
    }

    public void setCpuDuration(int cpuDuration) {
        this.cpuDuration = cpuDuration;
    }

    public int getMemoryDuration() {
        return memoryDuration;
    }

    public void setMemoryDuration(int memoryDuration) {
        this.memoryDuration = memoryDuration;
    }

    public long getSystemDataSaveSecond() {
        return systemDataSaveSecond;
    }

    public void setSystemDataSaveSecond(long systemDataSaveSecond) {
        this.systemDataSaveSecond = systemDataSaveSecond;
    }

    public long getOfDataSaveSecond() {
        return ofDataSaveSecond;
    }

    public void setOfDataSaveSecond(long ofDataSaveSecond) {
        this.ofDataSaveSecond = ofDataSaveSecond;
    }

    public List<String> getUserIds() {
        return userIds;
    }

    public void setUserIds(List<String> userIds) {
        this.userIds = userIds;
    }


    public boolean getIsEmailForCpu() {
        return isEmailForCpu;
    }

    public void setIsEmailForCpu(boolean emailForCpu) {
        isEmailForCpu = emailForCpu;
    }

    public boolean getIsIMForCpu() {
        return isIMForCpu;
    }

    public void setIsIMForCpu(boolean IMForCpu) {
        isIMForCpu = IMForCpu;
    }

    public boolean getIsEmailForMemory() {
        return isEmailForMemory;
    }

    public void setIsEmailForMemory(boolean emailForMemory) {
        isEmailForMemory = emailForMemory;
    }

    public boolean getIsIMForMemory() {
        return isIMForMemory;
    }

    public void setIsIMForMemory(boolean IMForMemory) {
        isIMForMemory = IMForMemory;
    }
}
