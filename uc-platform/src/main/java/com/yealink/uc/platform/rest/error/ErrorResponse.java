package com.yealink.uc.platform.rest.error;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author ChNan
 */
@XmlRootElement(name = "error")
@XmlAccessorType(XmlAccessType.FIELD)
public class ErrorResponse {
    @XmlElement(name = "exceptionClass")
    private String exceptionClass;

    @XmlElement(name = "message")
    private String message;

    @XmlElement(name = "exceptionTrace")
    private String exceptionTrace;

    @XmlElement(name = "statusCode")
    private int statusCode;

    @XmlElementWrapper(name = "fieldErrors")
    @XmlElement(name = "fieldError")
    private List<FieldError> fieldErrors = new ArrayList<>();

    public List<FieldError> getFieldErrors() {
        return fieldErrors;
    }

    public void setFieldErrors(final List<FieldError> fieldErrors) {
        this.fieldErrors = fieldErrors;
    }

    public String getExceptionClass() {
        return exceptionClass;
    }

    public void setExceptionClass(final String exceptionClass) {
        this.exceptionClass = exceptionClass;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(final String message) {
        this.message = message;
    }

    public String getExceptionTrace() {
        return exceptionTrace;
    }

    public void setExceptionTrace(final String exceptionTrace) {
        this.exceptionTrace = exceptionTrace;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(final int statusCode) {
        this.statusCode = statusCode;
    }


}
