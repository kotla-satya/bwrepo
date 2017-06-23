package com.brightwheel.core.service;

import com.brightwheel.core.EmailServiceTypes;
import com.brightwheel.core.bean.json.EmailRequest;
import com.brightwheel.core.bean.json.EmailResponse;
import com.brightwheel.core.bean.json.mailgun.MailgunMessagesResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.*;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.utils.HttpClientUtils;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import sun.misc.BASE64Encoder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Email Service for sending through MailGun
 */
@Service("mailGunEmailService")
public class MailGunEmailService extends BaseHttpEmailService {

    Logger logger = LoggerFactory.getLogger(MailGunEmailService.class);

    @Value("${mailgun.url}")
    private String mailGunUrl;
    @Value("${mailgun.user}")
    private String mailGunUser;
    @Value("${mailgun.password}")
    private String mailGunPwd;
    @Autowired
    private HttpClientService httpClientService;

    @Override
    public EmailResponse sendEmail(EmailRequest emailRequest) {
        logger.trace("Start of method sendEmail()");
        List<String> errors = validateEmailRequest(emailRequest);
        EmailResponse emailResponse = new EmailResponse();
        emailResponse.setServiceUsed(EmailServiceTypes.MAIL_GUN.getType());
        if(errors.size() > 0) {
            populateValidationErrors(emailResponse, errors);
            return emailResponse;
        }
        try {
            Header[] headers = getHeaders();
            HttpEntity httpEntity = getHttpEntity(emailRequest);
            HttpResponse response = httpClientService.post(mailGunUrl, headers, httpEntity);
            HttpEntity responseEntity = response.getEntity();
            ObjectMapper objectMapper = new ObjectMapper();
            int statusCode = response.getStatusLine().getStatusCode();
            MailgunMessagesResponse mailgunResponse =
                    objectMapper.readValue(responseEntity.getContent(), MailgunMessagesResponse.class);
            if( statusCode == 200){
                emailResponse.setSuccess(true);
                emailResponse.setServiceRefernceId(mailgunResponse.getId());
                emailResponse.setMessage(mailgunResponse.getMessage());
            }else {
                emailResponse.setSuccess(false);
                emailResponse.setMessage(mailgunResponse.getMessage());
            }
            HttpClientUtils.closeQuietly(response);
        } catch (IOException e) {
            logger.error("IOException occured while processing Mailgun response : ", e);
            populateExceptionDetails(emailResponse, e);
        }catch (Exception e){
            logger.error("Exception occured while processing Mandrill response : ", e);
            populateExceptionDetails(emailResponse, e);
        }
        logger.trace("Exiting method sendEmail()");
        return emailResponse;
    }

    protected Header[] getHeaders(){
        Header[] headers = new Header[2];
        headers[0] = new BasicHeader("Content-Type", "application/x-www-form-urlencoded");
        String credentials = mailGunUser+":"+mailGunPwd;
        BASE64Encoder encoder = new BASE64Encoder();
        headers[1] = new BasicHeader("Authorization", "Basic "+encoder.encode(credentials.getBytes()));
        return headers;
    }

    protected HttpEntity getHttpEntity(EmailRequest emailRequest) throws Exception{
        String from = emailRequest.getFromName() + " <"+ emailRequest.getFromEmailAddress()+">";
        String to = emailRequest.getToName() + " <"+emailRequest.getToEmailAddress()+">";
        String subject = emailRequest.getEmailSubject();
        String body = emailRequest.getEmailBody();
        List<NameValuePair> nameValuePairs = new ArrayList<>(4);
        nameValuePairs.add(new BasicNameValuePair("from", from));
        nameValuePairs.add(new BasicNameValuePair("to", to));
        nameValuePairs.add(new BasicNameValuePair("subject", subject));
        nameValuePairs.add(new BasicNameValuePair("text", body));
        HttpEntity httpEntity = new UrlEncodedFormEntity(() -> nameValuePairs.iterator());
        return httpEntity;
    }
}
