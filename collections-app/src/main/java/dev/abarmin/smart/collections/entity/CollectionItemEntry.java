package dev.abarmin.smart.collections.entity;

import dev.abarmin.common.upload.entity.FileEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table("COLLECTION_ITEMS")
@Builder(toBuilder = true)
public class CollectionItemEntry {
    @Id
    @Column("ID")
    private int id;

    @Column("COLLECTION_ID")
    private AggregateReference<CollectionEntity, Integer> collectionId;

    @Column("ARTIST")
    private String artist;

    @Column("ALBUM")
    private String album;

    @Column("COVER_FILE_ID")
    private AggregateReference<FileEntity, Integer> coverFileId;

    @Column("DELETED")
    @Builder.Default
    private boolean deleted = false;

    @CreatedDate
    @Column("CREATED_AT")
    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();

    @LastModifiedDate
    @Column("UPDATED_AT")
    private LocalDateTime updatedAt;
}
