package com.brightwheel.core.bean.json.mailgun;

/**
 * Created by JacksonGenerator on 6/19/17.
 */

import com.fasterxml.jackson.annotation.JsonProperty;


public class MailgunMessagesResponse {
    @JsonProperty("id")
    private String id;
    @JsonProperty("message")
    private String message;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}