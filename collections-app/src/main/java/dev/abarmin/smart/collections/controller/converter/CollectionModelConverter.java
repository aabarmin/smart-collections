package dev.abarmin.smart.collections.controller.converter;

import dev.abarmin.smart.collections.controller.model.CollectionItemModel;
import dev.abarmin.smart.collections.controller.model.CollectionModel;
import dev.abarmin.smart.collections.entity.CollectionEntity;
import dev.abarmin.smart.collections.entity.CollectionItemEntry;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CollectionModelConverter {
    private static final String NO_COVER = "/img/no-cover.jpg";

    public CollectionModel convert(CollectionEntity collection) {
        return CollectionModel.builder()
                .id(collection.getId())
                .name(collection.getName())
                .albums(collection.getCollectionItems().stream()
                        .map(this::convert)
                        .toList())
                .build();
    }

    private CollectionItemModel convert(CollectionItemEntry item) {
        return CollectionItemModel.builder()
                .id(item.getId())
                .artist(item.getArtist())
                .album(item.getAlbum())
                .cover(NO_COVER)
                .build();
    }
}
