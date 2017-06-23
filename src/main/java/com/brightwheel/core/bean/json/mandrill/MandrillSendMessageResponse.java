package com.brightwheel.core.bean.json.mandrill;

/**
 * Created by JacksonGenerator on 6/19/17.
 */

import com.fasterxml.jackson.annotation.JsonProperty;


public class MandrillSendMessageResponse {
    @JsonProperty("reject_reason")
    private String rejectReason;
    @JsonProperty("_id")
    private String id;
    @JsonProperty("email")
    private String email;
    @JsonProperty("status")
    private String status;

    public String getRejectReason() {
        return rejectReason;
    }

    public void setRejectReason(String rejectReason) {
        this.rejectReason = rejectReason;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}