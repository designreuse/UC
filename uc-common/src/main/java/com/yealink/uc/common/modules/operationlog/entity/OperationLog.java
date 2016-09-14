package com.yealink.uc.common.modules.operationlog.entity;

import java.util.Date;
import java.util.Map;

import com.yealink.uc.platform.annotations.Entity;

import org.springframework.format.annotation.DateTimeFormat;

/**
 * @author ChNan
 */
@Entity(name = "OperationLog")
public class OperationLog {
    private String _id;

    private String operation;

    private String operatorName;

    private String operatorIP;

    private String targetName;

    private boolean success;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date operationTime;

    private String resultDetail;

    public OperationLog() {
    }

    public OperationLog(String id, String operation, String operatorName,
                        String operatorIP, String targetName, boolean success,
                        Date operationTime, String resultDetail,String projectName) {
        this._id = id;
        this.operation = operation;
        this.operatorName = operatorName;
        this.operatorIP = operatorIP;
        this.targetName = targetName;
        this.success = success;
        this.operationTime = operationTime;
        this.resultDetail = resultDetail;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(final String id) {
        this._id = id;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(final String operation) {
        this.operation = operation;
    }

    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(final String operatorName) {
        this.operatorName = operatorName;
    }

    public String getOperatorIP() {
        return operatorIP;
    }

    public void setOperatorIP(final String operatorIP) {
        this.operatorIP = operatorIP;
    }

    public String getTargetName() {
        return targetName;
    }

    public void setTargetName(final String targetName) {
        this.targetName = targetName;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(final boolean success) {
        this.success = success;
    }

    public Date getOperationTime() {
        return operationTime;
    }

    public void setOperationTime(final Date operationTime) {
        this.operationTime = operationTime;
    }

    public String getResultDetail() {
        return resultDetail;
    }

    public void setResultDetail(final String resultDetail) {
        this.resultDetail = resultDetail;
    }

}
