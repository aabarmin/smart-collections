package dev.abarmin.common.mail.repository;

import dev.abarmin.common.mail.entity.EnvelopeEntity;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;

public interface EnvelopeRepository extends CrudRepository<EnvelopeEntity, Integer> {
    @Query("select * from OUTGOING_EMAIL mail where mail.STATUS != 'SENT'")
    Collection<EnvelopeEntity> readyForResent();
}
