package com.brightwheel.core;

/**
 * Available Email service providers, any new ones should be added here
 */
public enum EmailServiceTypes {

    MAIL_GUN("mailgun"),
    MANDRILL("mandrill");

    String type;

    EmailServiceTypes(String type){
        this.type = type;
    }


    public String getType() {
        return type;
    }


}
