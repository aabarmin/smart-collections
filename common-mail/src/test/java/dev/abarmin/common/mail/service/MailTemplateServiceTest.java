package dev.abarmin.common.mail.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.abarmin.common.mail.model.MailTemplate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.assertj.core.api.Assertions.assertThat;

@SpringJUnitConfig(classes = {
        ObjectMapper.class,
        MailTemplateService.class
})
class MailTemplateServiceTest {
    @Autowired
    MailTemplateService uut;

    @Test
    void name() {
        MailTemplate mailTemplate = uut.loadTemplate("template1");
        assertThat(mailTemplate).isNotNull();

        MailTemplate updated = mailTemplate.replace("first", "ever");
        assertThat(updated.getTemplate()).contains("This is the first message ever");
    }
}