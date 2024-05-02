package dev.abarmin.common.mail.service;

import dev.abarmin.common.mail.model.Envelope;
import jakarta.mail.internet.MimeMessage;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailService {
    private final JavaMailSender mailSender;

    @SneakyThrows
    public void send(@NonNull Envelope envelope) {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setTo(envelope.getTo());
        helper.setFrom(envelope.getFrom());
        helper.setSubject(envelope.getSubject());
        helper.setText(envelope.getContent(), true);

        mailSender.send(message);
    }
}
