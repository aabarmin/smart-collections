package dev.abarmin.common.mail.converter;

import dev.abarmin.common.mail.entity.EnvelopeEntity;
import dev.abarmin.common.mail.model.Envelope;
import org.springframework.stereotype.Component;

@Component
public class EnvelopeConverter {
    public EnvelopeEntity convert(Envelope envelope) {
        return EnvelopeEntity.builder()
                .from(envelope.getFrom())
                .to(envelope.getTo())
                .subject(envelope.getSubject())
                .content(envelope.getContent())
                .build();
    }

    public Envelope convert(EnvelopeEntity entity) {
        return Envelope.builder()
                .from(entity.getFrom())
                .to(entity.getTo())
                .subject(entity.getSubject())
                .content(entity.getContent())
                .build();
    }
}
