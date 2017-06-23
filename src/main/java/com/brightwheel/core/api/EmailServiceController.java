package com.brightwheel.core.api;

import com.brightwheel.core.EmailServiceTypes;
import com.brightwheel.core.bean.json.EmailRequest;
import com.brightwheel.core.bean.json.EmailResponse;
import com.brightwheel.core.service.HttpClientService;
import com.brightwheel.core.service.MailGunEmailService;
import com.brightwheel.core.service.MandrillEmailService;
import com.brightwheel.core.util.EmailServiceUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.ws.rs.core.MediaType;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Entry class for handling api calls
 */


@RestController(value = "EmailService")
public class EmailServiceController {

    Logger logger = LoggerFactory.getLogger(EmailServiceController.class);

    @Value("${email.service.preferred}")
    private String emailServicePreferred;
    @Autowired
    private MailGunEmailService mailGunEmailService;
    @Autowired
    private MandrillEmailService mandrillEmailService;
    private static AtomicLong referenceId = new AtomicLong(12121l);

    @RequestMapping(value = "/email",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON,
            produces = MediaType.APPLICATION_JSON)
    public ResponseEntity<EmailResponse> sendEmail(@RequestBody EmailRequest request){
        logger.info("/email request. Service Preference : "+emailServicePreferred+ " Reference Id:"+referenceId);
        ResponseEntity<EmailResponse> responseEntity = null;
        EmailResponse emailResponse = null;
        //Removes <br> tags and then any html tags <..> or <.../>.
        // Does not format anchor tags, ampersands etc
        String formattedBody = request.getEmailBody().replaceAll("\\<.?br>", "\n");
        formattedBody = formattedBody.replaceAll("\\<.*?>"," ");
        request.setEmailBody(formattedBody.trim());
        logger.trace("formatted body :"+ formattedBody);
        if(emailServicePreferred.equals(EmailServiceTypes.MAIL_GUN.getType())){
            emailResponse = mailGunEmailService.sendEmail(request);
        }else if(emailServicePreferred.equals(EmailServiceTypes.MANDRILL.getType())){
            emailResponse = mandrillEmailService.sendEmail(request);
        }else {
            emailResponse = new EmailResponse();//Should not reach here as preference should be present
            emailResponse.setError("EmailService provider not found");
            emailResponse.setHttpStatus(500);
        }
        int statusCode = emailResponse.getHttpStatus() != 0 ? emailResponse.getHttpStatus() : 201;
        emailResponse.setReferenceId(referenceId.getAndIncrement()+"");
        responseEntity = new ResponseEntity<>(emailResponse, HttpStatus.valueOf(statusCode));
        return responseEntity;
    }

    /**
     *
     * @return Different Email Service providers that are supported
     */
    @RequestMapping(value = "serviceProviders",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON)
    public List<String> getEmailServiceTypes(){
        List<String> types = new ArrayList<>();
        for(EmailServiceTypes svcType : EmailServiceTypes.values()){
            types.add(svcType.getType());
        }
        return types;
    }

    /**
     * To handle invalid api endpoints
     * @return
     */
    @RequestMapping(value = "*",
            produces = MediaType.APPLICATION_JSON)
    public ResponseEntity<String> allFallback() {
        ResponseEntity<String> responseEntity =
                new ResponseEntity<>(
                        "{\"error\": \"Oh ho, no service found with requested url\"}",
                        HttpStatus.valueOf(HttpStatus.NOT_FOUND.value()));
        return responseEntity;

    }
}
