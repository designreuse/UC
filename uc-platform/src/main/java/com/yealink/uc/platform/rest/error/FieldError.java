package com.yealink.uc.platform.rest.error;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * @author ChNan
 */
@XmlType
@XmlAccessorType(XmlAccessType.FIELD)
public class FieldError {
    @XmlElement(name = "field")
    private String field;

    @XmlElement(name = "errorMessage")
    private String errorMessage;

    public String getField() {
        return field;
    }

    public void setField(final String field) {
        this.field = field;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(final String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
