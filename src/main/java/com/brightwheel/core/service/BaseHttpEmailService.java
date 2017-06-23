package com.brightwheel.core.service;


import com.brightwheel.core.bean.json.EmailRequest;
import com.brightwheel.core.bean.json.EmailResponse;
import com.brightwheel.core.util.EmailServiceUtil;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;


import java.util.ArrayList;
import java.util.List;

/**
 * Base class for sending emails through HTTP requests. This also
 * has commonly used methods like validation of email request
 */
public abstract class BaseHttpEmailService implements EmailService{

    Logger logger = LoggerFactory.getLogger(BaseHttpEmailService.class);

    protected abstract Header[] getHeaders();

    protected abstract HttpEntity getHttpEntity(EmailRequest emailRequest) throws Exception;


    protected List<String> validateEmailRequest(EmailRequest emailRequest){
        List<String> errors = new ArrayList<>(6);
        if(emailRequest.getFromName() == null || emailRequest.getFromName().equals("")){
            errors.add("From Name is Required");
        }
        if(emailRequest.getFromEmailAddress() == null || emailRequest.getFromEmailAddress().equals("")){
            errors.add("From Email Address is Required");
        }else if(!EmailServiceUtil.isValidEmail(emailRequest.getFromEmailAddress())){
            errors.add("From email address not valid");
        }
        if(emailRequest.getToName() == null || emailRequest.getToName().equals("")){
            errors.add("To Name is Required");
        }
        if(emailRequest.getToEmailAddress() == null || emailRequest.getToEmailAddress().equals("")){
            errors.add("To Email Address is Required");
        }else if(!EmailServiceUtil.isValidEmail(emailRequest.getToEmailAddress())){
            errors.add("To email address not valid");
        }
        if(emailRequest.getEmailSubject() == null || emailRequest.getEmailSubject().equals("")){
            errors.add("Email Subject is Required");
        }
        if(emailRequest.getEmailBody() == null || emailRequest.getEmailBody().equals("")){
            errors.add("Email Body is Required");
        }
        return errors;
    }


    protected void populateValidationErrors(EmailResponse emailResponse, List<String> errors){
        logger.error("Validation Errors Present in the EmailRequest");
        emailResponse.setSuccess(false);
        StringBuffer errorMsg = new StringBuffer();
        for(String error: errors){
            errorMsg.append(error);
            errorMsg.append(". ");
        }
        emailResponse.setError(errorMsg.toString());
        emailResponse.setHttpStatus(HttpStatus.BAD_REQUEST.value());
    }

    protected void populateExceptionDetails(EmailResponse emailResponse, Exception exception){
        emailResponse.setSuccess(false);
        emailResponse.setError("Error occurred while processing. "+exception.getMessage());
        emailResponse.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
    }
}
