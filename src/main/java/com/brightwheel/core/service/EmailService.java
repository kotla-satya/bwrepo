package com.brightwheel.core.service;

import com.brightwheel.core.bean.json.EmailRequest;
import com.brightwheel.core.bean.json.EmailResponse;

/**
 * Any Emailservice provider should implement this class
 */
public interface EmailService {

    EmailResponse sendEmail(EmailRequest request);
}
