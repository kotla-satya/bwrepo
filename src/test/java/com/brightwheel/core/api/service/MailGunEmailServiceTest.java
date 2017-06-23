package com.brightwheel.core.api.service;


import com.brightwheel.core.bean.json.EmailRequest;
import com.brightwheel.core.bean.json.EmailResponse;
import com.brightwheel.core.service.HttpClientService;
import com.brightwheel.core.service.MailGunEmailService;
import org.apache.http.*;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicHttpResponse;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.message.BasicStatusLine;
import org.apache.http.params.HttpParams;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import sun.misc.BASE64Encoder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MailGunEmailServiceTest {

    @MockBean
    private HttpClientService httpClientService;
    @MockBean
    private HttpResponse httpResponse;
    @MockBean
    private StatusLine statusLine;

    @Value("${mailgun.url}")
    private String mailGunUrl;

    @MockBean
    private HttpEntity reqHttpEntity;

    @Autowired
    private MailGunEmailService mailGunEmailService;

    @Test
    public void sendTestEmail() throws Exception {

        String stringEntity ="{\"id\":\"12231\", \"message\":\"Queued. Thank you.\"}";
        HttpEntity httpEntity = new StringEntity(stringEntity);

        StatusLine httpStatusLine = new BasicStatusLine(HttpVersion.HTTP_1_1, 200, "");
        this.httpResponse.setStatusLine(httpStatusLine);
        this.httpResponse.setStatusCode(200);
        this.httpResponse.setEntity(httpEntity);

        EmailRequest emailRequest = new EmailRequest();
        emailRequest.setToName("sk");
        emailRequest.setToEmailAddress("kotla.satya@gmail.com");
        emailRequest.setFromName("sk1");
        emailRequest.setFromEmailAddress("kotla.satya+2@gmail.com");
        emailRequest.setEmailBody("helo email body");
        emailRequest.setEmailSubject("hello test subject");

        //TODO: Not working as expected, need to fix this
        given(this.httpClientService.post(anyString(), any(Header[].class), any(HttpEntity.class))).willReturn(httpResponse);
        EmailResponse emailResponse = mailGunEmailService.sendEmail(emailRequest);
        assertThat(emailResponse.getHttpStatus()).isEqualTo(200);
        //assertThat(emailResponse.getMessage()).isEqualTo("Queued. Thank you.");
    }
}
