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
    private static final String NO_COVER = "/img/private/no-cover.jpg";

    public CollectionModel convert(CollectionEntity collection) {
        return CollectionModel.builder()
                .id(collection.getId())
                .name(collection.getName())
                .albums(collection.getCollectionItems().stream()
                        .filter(album -> !album.isDeleted())
                        .map(this::convert)
                        .toList())
                .build();
    }

    private CollectionItemModel convert(CollectionItemEntry item) {
        return CollectionItemModel.builder()
                .id(item.getId())
                .artist(item.getArtist())
                .album(item.getAlbum())
                .cover(getCover(item))
                .build();
    }

    private String getCover(CollectionItemEntry itemEntry) {
        if (itemEntry.getCoverFileId() == null) {
            return NO_COVER;
        }
        return String.format(
                "/images/%s/thumbnail",
                itemEntry.getCoverFileId().getId()
        );
    }
}
