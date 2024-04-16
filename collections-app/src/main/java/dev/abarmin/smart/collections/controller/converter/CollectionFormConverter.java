package dev.abarmin.smart.collections.controller.converter;

import dev.abarmin.common.security.service.SessionService;
import dev.abarmin.smart.collections.controller.model.CollectionForm;
import dev.abarmin.smart.collections.entity.CollectionEntity;
import dev.abarmin.smart.collections.exception.NoCurrentUserException;
import dev.abarmin.smart.collections.repository.CollectionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class CollectionFormConverter {
    private final SessionService sessionService;
    private final CollectionRepository repository;

    public CollectionEntity convert(CollectionForm form) {
        final CollectionEntity entity;
        if (form.getId() == 0) {
            entity = new CollectionEntity();
        } else {
            entity = repository.findById(form.getId()).orElseThrow();
            entity.setUpdatedAt(LocalDateTime.now());
        }
        return update(form, entity);
    }

    private CollectionEntity update(CollectionForm form, CollectionEntity entity) {
        entity.setName(form.getName());
        entity.setUserId(
                AggregateReference.to(sessionService
                        .getCurrentUser()
                        .orElseThrow(NoCurrentUserException::new)
                        .getId())
        );
        return entity;
    }
}
