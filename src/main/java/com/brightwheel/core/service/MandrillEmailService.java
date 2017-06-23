package com.brightwheel.core.service;

import com.brightwheel.core.EmailServiceTypes;
import com.brightwheel.core.bean.json.EmailRequest;
import com.brightwheel.core.bean.json.EmailResponse;
import com.brightwheel.core.bean.json.mandrill.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.client.utils.HttpClientUtils;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicHeader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Email Service for sending through Mandrill
 */
@Service("mandrillEmailService")
public class MandrillEmailService extends BaseHttpEmailService {

    Logger logger = LoggerFactory.getLogger(MandrillEmailService.class);

    @Value("${mandrill.url}")
    private String mandrillUrl;
    @Value("${mandrill.key}")
    private String mandrillKey;
    @Autowired
    private HttpClientService httpClientService;

    /**
     *
     * @param emailRequest
     * @return
     */
    @Override
    public EmailResponse sendEmail(EmailRequest emailRequest) {
        List<String> errors = validateEmailRequest(emailRequest);
        EmailResponse emailResponse = new EmailResponse();
        emailResponse.setServiceUsed(EmailServiceTypes.MANDRILL.getType());
        if(errors.size() > 0) {
            populateValidationErrors(emailResponse, errors);
            return emailResponse;
        }
        try {
            Header[] headers = getHeaders();
            ObjectMapper objectMapper = new ObjectMapper();
            HttpEntity emailRequestJsonEntity = getHttpEntity(emailRequest);
            HttpResponse response = httpClientService.post(mandrillUrl, headers, emailRequestJsonEntity);
            HttpEntity responseEntity = response.getEntity();
            int statusCode = response.getStatusLine().getStatusCode();
            if( statusCode == 200){
                //Array is returned.
                MandrillSendMessageResponse[] sendMessageResponses =
                        objectMapper.readValue(responseEntity.getContent(), MandrillSendMessageResponse[].class);
                //Get the first one as only one email is in the to.
                MandrillSendMessageResponse sendMessageResponse = sendMessageResponses[0];
                emailResponse.setSuccess(true);
                emailResponse.setServiceRefernceId(sendMessageResponse.getId());
                StringBuffer msg = new StringBuffer();
                msg.append("Status :"+ sendMessageResponse.getStatus());
                if(sendMessageResponse.getRejectReason() != null && !sendMessageResponse.getRejectReason().equals("")){
                    emailResponse.setSuccess(false); //as reject reason is present
                    msg.append(" Reject Reason : "+ sendMessageResponse.getRejectReason());
                    emailResponse.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
                }
                emailResponse.setMessage(msg.toString());
            }else { //not sure when this is returned
                MandrillSendMessageErrorResponse sendMessageErrorResponse =
                        objectMapper.readValue(responseEntity.getContent(), MandrillSendMessageErrorResponse.class);
                emailResponse.setSuccess(false);
                emailResponse.setMessage(sendMessageErrorResponse.getMessage());
            }
            HttpClientUtils.closeQuietly(response);
        } catch (IOException e) {
            logger.error("IOException occured while processing Mandrill response : ", e);
            populateExceptionDetails(emailResponse, e);
        }catch (Exception e){
            logger.error("Exception occured while processing Mandrill response : ", e);
            populateExceptionDetails(emailResponse, e);
        }
        logger.trace("Exiting method sendEmail()");
        return emailResponse;
    }
    protected Header[] getHeaders(){
        Header[] headers = new Header[1];
        headers[0] = new BasicHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON);
        return headers;
    }

    protected HttpEntity getHttpEntity(EmailRequest emailRequest) throws Exception{
        MandrillSendMessageRequest mandrillRequest = new MandrillSendMessageRequest();
        mandrillRequest.setKey(mandrillKey);
        Message mandrillEmailMsg = new Message();
        mandrillEmailMsg.setFromName(emailRequest.getFromName());
        mandrillEmailMsg.setFromEmail(emailRequest.getFromEmailAddress());
        List<ToItem> toAddresses = new ArrayList<>();
        ToItem toAddress = new ToItem();
        toAddress.setName(emailRequest.getToName());
        toAddress.setEmail(emailRequest.getToEmailAddress());
        toAddresses.add(toAddress);
        mandrillEmailMsg.setTo(toAddresses);
        mandrillEmailMsg.setSubject(emailRequest.getEmailBody());
        mandrillEmailMsg.setText(emailRequest.getEmailBody());
        mandrillRequest.setMessage(mandrillEmailMsg);
        ObjectMapper objectMapper = new ObjectMapper();
        String mandrillJson = objectMapper.writeValueAsString(mandrillRequest);
        StringEntity emailRequestJsonEntity = new StringEntity(mandrillJson);
        return emailRequestJsonEntity;
    }
}
