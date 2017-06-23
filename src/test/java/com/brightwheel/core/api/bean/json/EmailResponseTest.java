package com.brightwheel.core.api.bean.json;

import com.brightwheel.core.bean.json.EmailResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@JsonTest
public class EmailResponseTest {

    @Autowired
    private JacksonTester<com.brightwheel.core.bean.json.EmailResponse> json;

    @Test
    public void testDeSerialize() throws Exception{
        EmailResponse emailResponse = new EmailResponse();
        emailResponse.setHttpStatus(200);
        emailResponse.setServiceUsed("mailgun");
        emailResponse.setSuccess(true);
        assertThat(this.json.write(emailResponse)).hasJsonPathStringValue("@.service_provider_name");
        assertThat(this.json.write(emailResponse)).hasJsonPathBooleanValue("@.success");
        assertThat(this.json.write(emailResponse)).hasEmptyJsonPathValue("@.errors");
        assertThat(this.json.write(emailResponse)).doesNotHaveJsonPathValue("@.httpStatus");
        assertThat(this.json.write(emailResponse)).extractingJsonPathBooleanValue("@.success")
                .isEqualTo(true);
    }
}
