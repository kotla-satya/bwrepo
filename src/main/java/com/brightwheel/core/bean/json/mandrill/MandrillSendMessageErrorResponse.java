package com.brightwheel.core.bean.json.mandrill;

/**
 * Created by JacksonGenerator on 6/19/17.
 */

import com.fasterxml.jackson.annotation.JsonProperty;


public class MandrillSendMessageErrorResponse {
    @JsonProperty("code")
    private Integer code;
    @JsonProperty("name")
    private String name;
    @JsonProperty("message")
    private String message;
    @JsonProperty("status")
    private String status;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}