package com.brightwheel.core.bean.json.mandrill;

/**
 * Created by JacksonGenerator on 6/19/17.
 */

import com.fasterxml.jackson.annotation.JsonProperty;


public class ToItem {
    @JsonProperty("name")
    private String name;
    @JsonProperty("type")
    private String type;
    @JsonProperty("email")
    private String email;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}