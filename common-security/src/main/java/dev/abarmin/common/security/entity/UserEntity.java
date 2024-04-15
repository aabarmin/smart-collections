package dev.abarmin.common.security.entity;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;
import java.util.Collection;

@Data
@Table("USERS")
public class UserEntity {
    @Id
    @Column("ID")
    private int id;

    @Column("USERNAME")
    private String username;

    @Column("PASSWORD")
    private String password;

    @Column("ACTIVATED")
    private boolean activated;

    @Column("AUTH_TYPE")
    private AuthType authType;

    @MappedCollection(idColumn = "USER_ID")
    private Collection<UserAuthorityEntity> authorities;

    @CreatedDate
    @Column("CREATED_AT")
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column("UPDATED_AT")
    private LocalDateTime updatedAt;
}
