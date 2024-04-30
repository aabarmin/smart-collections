package dev.abarmin.smart.collections.controller.converter;

import dev.abarmin.common.upload.entity.FileEntity;
import dev.abarmin.smart.collections.controller.model.CollectionItemForm;
import dev.abarmin.smart.collections.entity.CollectionItemEntry;
import dev.abarmin.smart.collections.repository.CollectionItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CollectionItemFormConverter {
    private final CollectionItemRepository repository;

    public CollectionItemEntry convert(CollectionItemForm form) {
        final CollectionItemEntry entry;
        if (form.getId() == 0) {
            entry = new CollectionItemEntry();
        } else {
            entry = repository.findById(form.getId()).orElseThrow();
        }

        return entry.toBuilder()
                .collectionId(AggregateReference.to(form.getCollectionId()))
                .album(form.getAlbum())
                .artist(form.getArtist())
                .coverFileId(Optional.ofNullable(form.getCoverId())
                        .map(this::referenceTo)
                        .orElse(null))
                .build();
    }

    public CollectionItemForm convert(CollectionItemEntry entity) {
        return CollectionItemForm.builder()
                .id(entity.getId())
                .collectionId(entity.getCollectionId().getId())
                .album(entity.getAlbum())
                .artist(entity.getArtist())
                .coverId(Optional.ofNullable(entity.getCoverFileId())
                        .map(AggregateReference::getId)
                        .orElse(0))
                .build();
    }

    private AggregateReference<FileEntity, Integer> referenceTo(int fileId) {
        return AggregateReference.to(fileId);
    }
}
