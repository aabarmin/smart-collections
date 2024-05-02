package dev.abarmin.common.mail.service;

import dev.abarmin.common.mail.config.CommonMailAutoConfiguration;
import dev.abarmin.common.mail.model.Envelope;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mail.MailSenderAutoConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringJUnitConfig(classes = {
        CommonMailAutoConfiguration.class,
        MailSenderAutoConfiguration.class
})
@TestPropertySource(properties = {
        "spring.mail.host=localhost",
        "spring.mail.port=1025"
})
@Disabled
class MailServiceTest {
    @Autowired
    MailService mailService;

    @Test
    void check_starts() {
        assertThat(mailService).isNotNull();
        mailService.send(Envelope.builder()
                .to("test@test.com")
                .from("robot@test.com")
                .subject("Hello")
                .content("<h1>Hello, World!</h1>")
                .build());
    }
}