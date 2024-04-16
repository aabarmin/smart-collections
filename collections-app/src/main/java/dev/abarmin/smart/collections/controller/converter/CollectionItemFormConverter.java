package dev.abarmin.smart.collections.controller.converter;

import dev.abarmin.smart.collections.controller.model.CollectionItemForm;
import dev.abarmin.smart.collections.entity.CollectionItemEntry;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CollectionItemFormConverter {
    public CollectionItemEntry convert(CollectionItemForm form) {
        return CollectionItemEntry.builder()
                .collectionId(AggregateReference.to(form.getCollectionId()))
                .album(form.getAlbum())
                .artist(form.getArtist())
                .build();
    }
}
