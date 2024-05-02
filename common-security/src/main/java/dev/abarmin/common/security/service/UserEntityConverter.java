package dev.abarmin.common.security.service;

import dev.abarmin.common.security.domain.UserInfo;
import dev.abarmin.common.security.entity.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class UserEntityConverter {
    public UserInfo convert(UserEntity entity) {
        return UserInfo.builder()
                .id(entity.getId())
                .username(entity.getEmail())
                .fullName(entity.getFullName())
                .build();
    }
}
