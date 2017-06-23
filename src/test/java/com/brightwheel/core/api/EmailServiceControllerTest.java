package com.brightwheel.core.api;

import com.brightwheel.core.bean.json.EmailRequest;
import com.brightwheel.core.bean.json.EmailResponse;
import com.brightwheel.core.service.MailGunEmailService;
import com.brightwheel.core.service.MandrillEmailService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@WebMvcTest(EmailServiceController.class)
public class EmailServiceControllerTest {

    @MockBean
    private EmailRequest emailRequest;

    //private String emailServicePreferred;
    @MockBean
    private MailGunEmailService mailGunEmailService;
    @MockBean
    private MandrillEmailService mandrillEmailService;

    @Autowired
    private MockMvc mvc;

    @Test
    public void testSendEmail() throws Exception{
        this.mvc.perform(
            post("/email").accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"));
        EmailRequest emailRequest = new EmailRequest();
        EmailResponse emailResponse = new EmailResponse();
        given(this.mailGunEmailService.sendEmail(emailRequest)).willReturn(emailResponse);
        given(this.mandrillEmailService.sendEmail(emailRequest)).willReturn(emailResponse);
        String reqJson = "{\n" +
                "\"to\": \"kotla.satya@gmail.com\",\n" +
                "\"to_name\": \"sk1\",\n" +
                "\"from\": \"kotla.satya+2@gmail.com\",\n" +
                "\"from_name\": \"sk\",\n" +
                "\"subject\": \"helloo test\",\n" +
                "\"body\": \"hello test\"\n" +
                "\n" +
                "}";
        this.mvc.perform(
                post("/email").content(reqJson).accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetServices() throws Exception{
        String json = "[\"mailgun\", \"mandrill\" ]";
        this.mvc.perform(get("/serviceProviders").accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(content().json(json));
    }


}
