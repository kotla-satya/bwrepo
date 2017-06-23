package com.brightwheel.core.bean.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * Created by satya on 6/16/17.
 */
@JsonPropertyOrder({"to", "to_name", "from", "from_name", "subject", "body"})
public class EmailRequest {

    @JsonProperty(value = "to", required = true, defaultValue = "")
    private String toEmailAddress;

    @JsonProperty(value = "to_name", required = true, defaultValue = "")
    private String toName;

    @JsonProperty(value = "from", required = true, defaultValue = "")
    private String fromEmailAddress;

    @JsonProperty(value = "from_name", required = true, defaultValue = "")
    private String fromName;

    @JsonProperty(value = "subject", required = true, defaultValue = "")
    private String emailSubject;

    @JsonProperty(value = "body", required = true, defaultValue = "")
    private String emailBody;

    public String getToEmailAddress() {
        return toEmailAddress;
    }

    public void setToEmailAddress(String toEmailAddress) {
        this.toEmailAddress = toEmailAddress;
    }

    public String getToName() {
        return toName;
    }

    public void setToName(String toName) {
        this.toName = toName;
    }

    public String getFromEmailAddress() {
        return fromEmailAddress;
    }

    public void setFromEmailAddress(String fromEmailAddress) {
        this.fromEmailAddress = fromEmailAddress;
    }

    public String getFromName() {
        return fromName;
    }

    public void setFromName(String fromName) {
        this.fromName = fromName;
    }

    public String getEmailSubject() {
        return emailSubject;
    }

    public void setEmailSubject(String emailSubject) {
        this.emailSubject = emailSubject;
    }

    public String getEmailBody() {
        return emailBody;
    }

    public void setEmailBody(String emailBody) {
        this.emailBody = emailBody;
    }
}
