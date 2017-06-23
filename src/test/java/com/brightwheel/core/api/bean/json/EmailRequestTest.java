package com.brightwheel.core.api.bean.json;

import com.brightwheel.core.bean.json.EmailRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@JsonTest
public class EmailRequestTest {

    @Autowired
    private JacksonTester<EmailRequest> reqJson;

    @Test
    public void testSerialize() throws Exception{
        String request = "{\n" +
                "\"to\": \"kotla.satya@gmail.com\",\n" +
                "\"to_name\": \"sk1\",\n" +
                "\"from\": \"kotla.satya+2@gmail.com\",\n" +
                "\"from_name\": \"sk\",\n" +
                "\"subject\": \"helloo test\",\n" +
                "\"body\": \"hello test\"\n" +
                "\n" +
                "}";
        EmailRequest emailRequest = new EmailRequest();
        emailRequest.setToName("sk1");
        emailRequest.setToEmailAddress("kotla.satya@gmail.com");
        emailRequest.setFromName("sk");
        emailRequest.setFromEmailAddress("kotla.satya+2@gmail.com");
        emailRequest.setEmailSubject("helloo test");
        emailRequest.setEmailBody("hello test");
        assertThat(this.reqJson.parse(request).getObject().getToName()).isEqualTo("sk1");
        assertThat(this.reqJson.parse(request).getObject().getEmailBody()).isEqualTo("hello test");
    }
}
