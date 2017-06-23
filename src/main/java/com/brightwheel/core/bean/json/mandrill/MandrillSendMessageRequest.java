package com.brightwheel.core.bean.json.mandrill;

/**
 * Created by JacksonGenerator on 6/19/17.
 */

import com.fasterxml.jackson.annotation.JsonProperty;


public class MandrillSendMessageRequest {
    @JsonProperty("async")
    private Boolean async;
    @JsonProperty("ip_pool")
    private String ipPool;
    @JsonProperty("message")
    private Message message;
    @JsonProperty("key")
    private String key;

    public Boolean getAsync() {
        return async;
    }

    public void setAsync(Boolean async) {
        this.async = async;
    }

    public String getIpPool() {
        return ipPool;
    }

    public void setIpPool(String ipPool) {
        this.ipPool = ipPool;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}