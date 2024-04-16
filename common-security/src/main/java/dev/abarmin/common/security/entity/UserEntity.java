package dev.abarmin.common.security.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@Builder
@Table("USERS")
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {
    @Id
    @Column("ID")
    private int id;

    @Column("EMAIL")
    private String email;

    @Column("FULL_NAME")
    private String fullName;

    @Column("PASSWORD")
    private String password;

    @Column("ACTIVATED")
    private boolean activated;

    @Column("AUTH_TYPE")
    private AuthType authType;

    @MappedCollection(idColumn = "USER_ID")
    private Set<UserAuthorityEntity> authorities;

    @CreatedDate
    @Column("CREATED_AT")
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column("UPDATED_AT")
    private LocalDateTime updatedAt;
}
