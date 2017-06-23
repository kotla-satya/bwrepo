package com.brightwheel.core.bean.json.mandrill;

/**
 * Created by JacksonGenerator on 6/19/17.
 */

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;


public class Message {
    @JsonProperty("headers")
    private Headers headers;
    @JsonProperty("from_email")
    private String fromEmail;
    @JsonProperty("subject")
    private String subject;
    @JsonProperty("html")
    private String html;
    @JsonProperty("text")
    private String text;
    @JsonProperty("to")
    private List<ToItem> to;
    @JsonProperty("from_name")
    private String fromName;

    public Headers getHeaders() {
        return headers;
    }

    public void setHeaders(Headers headers) {
        this.headers = headers;
    }

    public String getFromEmail() {
        return fromEmail;
    }

    public void setFromEmail(String fromEmail) {
        this.fromEmail = fromEmail;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getHtml() {
        return html;
    }

    public void setHtml(String html) {
        this.html = html;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<ToItem> getTo() {
        return to;
    }

    public void setTo(List<ToItem> to) {
        this.to = to;
    }

    public String getFromName() {
        return fromName;
    }

    public void setFromName(String fromName) {
        this.fromName = fromName;
    }
}