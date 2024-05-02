package dev.abarmin.smart.collections.service;

import dev.abarmin.common.mail.model.Envelope;
import dev.abarmin.common.mail.model.MailTemplate;
import dev.abarmin.common.mail.service.MailService;
import dev.abarmin.common.mail.service.MailTemplateService;
import dev.abarmin.common.security.event.UserRegisteredEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserRegistrationListener {
    private final MailTemplateService templateService;
    private final MailService mailService;

    @EventListener(UserRegisteredEvent.class)
    public void onUserRegistration(UserRegisteredEvent event) {
        MailTemplate template = templateService.loadTemplate("user-welcome")
                .replace("fullName", event.getUserInfo().getFullName());

        mailService.send(Envelope.builder()
                .content(template.getTemplate())
                .from("robot@robot.com")
                .subject(template.getSubject())
                .to(event.getUserInfo().getUsername())
                .build());
    }
}
