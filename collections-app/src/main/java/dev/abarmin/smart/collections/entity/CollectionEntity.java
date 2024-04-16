package dev.abarmin.smart.collections.entity;

import dev.abarmin.common.security.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("COLLECTIONS")
public class CollectionEntity {
    @Id
    @Column("ID")
    private int id;

    @Column("USER_ID")
    private AggregateReference<UserEntity, Integer> userId;

    @Column("NAME")
    private String name;

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
