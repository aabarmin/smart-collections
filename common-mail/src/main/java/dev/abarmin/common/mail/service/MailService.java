package dev.abarmin.common.mail.service;

import dev.abarmin.common.mail.converter.EnvelopeConverter;
import dev.abarmin.common.mail.entity.EnvelopeEntity;
import dev.abarmin.common.mail.entity.EnvelopeStatus;
import dev.abarmin.common.mail.model.Envelope;
import dev.abarmin.common.mail.repository.EnvelopeRepository;
import jakarta.mail.internet.MimeMessage;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collection;

@Slf4j
@Service
@RequiredArgsConstructor
public class MailService {
    private final JavaMailSender mailSender;
    private final EnvelopeConverter converter;
    private final EnvelopeRepository envelopeRepository;

    public void send(@NonNull Envelope envelope) {
        EnvelopeEntity entity = converter.convert(envelope);
        EnvelopeEntity savedEntity = envelopeRepository.save(entity);
        try {
            savedEntity.setSendAttemptAt(LocalDateTime.now());
            trySend(envelope);
            savedEntity.setStatus(EnvelopeStatus.SENT);
            savedEntity.setSentAt(LocalDateTime.now());
        } catch (Exception e) {
            savedEntity.setSendingError(ExceptionUtils.getStackTrace(e));
            log.warn("Can't send email with id {} due to the error, saved to the db", savedEntity.getId());
        } finally {
            envelopeRepository.save(savedEntity);
        }
    }

    @Scheduled(fixedDelay = 60_000)
    public void reSendMail() {
        log.info("Starting email resend");
        Collection<EnvelopeEntity> forResent = envelopeRepository.readyForResent();
        log.info("Found {} email to resent", forResent.size());
        for (EnvelopeEntity envelopeEntity : forResent) {
            try {
                envelopeEntity.setSendAttemptNo(envelopeEntity.getSendAttemptNo() + 1);
                envelopeEntity.setSendAttemptAt(LocalDateTime.now());
                envelopeEntity.setUpdatedAt(LocalDateTime.now());
                trySend(converter.convert(envelopeEntity));
                envelopeEntity.setStatus(EnvelopeStatus.SENT);
                envelopeEntity.setSentAt(LocalDateTime.now());
            } catch (Exception e) {
                envelopeEntity.setSendingError(ExceptionUtils.getStackTrace(e));
                log.warn("Can't send email with id {} due to the error", envelopeEntity.getId());
            } finally {
                envelopeRepository.save(envelopeEntity);
            }
        }

        log.info("Email resend completed");
    }

    private void trySend(Envelope envelope) throws Exception {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setTo(envelope.getTo());
        helper.setFrom(envelope.getFrom());
        helper.setSubject(envelope.getSubject());
        helper.setText(envelope.getContent(), true);

        mailSender.send(message);
    }
}
