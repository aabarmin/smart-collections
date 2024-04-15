package dev.abarmin.common.security.repository;

import dev.abarmin.common.security.entity.AuthType;
import dev.abarmin.common.security.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserEntityRepository extends CrudRepository<UserEntity, Integer> {
    Optional<UserEntity> findByEmailAndAuthType(String email, AuthType authType);
    Optional<UserEntity> findByEmail(String email);
}
