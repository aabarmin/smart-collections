package dev.abarmin.common.security.repository;

import dev.abarmin.common.security.entity.AuthType;
import dev.abarmin.common.security.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserEntityRepository extends CrudRepository<UserEntity, Integer> {
    Optional<UserEntity> findByUsernameAndAuthType(String username, AuthType authType);
}
