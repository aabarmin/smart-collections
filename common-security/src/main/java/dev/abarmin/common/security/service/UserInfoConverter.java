package dev.abarmin.common.security.service;

import dev.abarmin.common.security.domain.UserAuthority;
import dev.abarmin.common.security.domain.UserInfo;
import dev.abarmin.common.security.entity.UserAuthorityEntity;
import dev.abarmin.common.security.entity.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class UserInfoConverter {
    public UserInfo convert(UserEntity entity) {
        return UserInfo.builder()
                .activated(entity.isActivated())
                .username(entity.getEmail())
                .password(entity.getPassword())
                .authorities(entity.getAuthorities().stream().map(this::convert).toList())
                .build();
    }

    private UserAuthority convert(UserAuthorityEntity entity) {
        return UserAuthority.builder()
                .authority(entity.getAuthority())
                .build();
    }
}
