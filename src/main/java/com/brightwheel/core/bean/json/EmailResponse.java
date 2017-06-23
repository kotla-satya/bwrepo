package com.brightwheel.core.bean.json;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;


public class EmailResponse {

    //Reference id to indicate the transaction
    @JsonProperty(value = "reference_id")
    private String referenceId;
    @JsonProperty(value = "service_provider_name", defaultValue = " ")
    private String serviceUsed;
    @JsonProperty(value = "service_provider_reference_id", defaultValue = " ")
    private String serviceRefernceId;
    //Indicates status of the transaction
    @JsonProperty(value = "success")
    private boolean isSuccess;
    @JsonProperty(value = "service_provider_message", defaultValue = " ")
    private String message;
    //For validation errors or system errors which are not service provider specific
    @JsonProperty(value = "errors", defaultValue = " ")
    private String error;
    @JsonIgnore
    private int httpStatus;

    public String getReferenceId() {
        return referenceId;
    }

    public void setReferenceId(String referenceId) {
        this.referenceId = referenceId;
    }

    public String getServiceUsed() {
        return serviceUsed;
    }

    public void setServiceUsed(String serviceUsed) {
        this.serviceUsed = serviceUsed;
    }

    public String getServiceRefernceId() {
        return serviceRefernceId;
    }

    public void setServiceRefernceId(String serviceRefernceId) {
        this.serviceRefernceId = serviceRefernceId;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public int getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(int httpStatus) {
        this.httpStatus = httpStatus;
    }
}
