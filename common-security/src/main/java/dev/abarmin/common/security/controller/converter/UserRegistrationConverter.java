package dev.abarmin.common.security.controller.converter;

import dev.abarmin.common.security.controller.model.UserRegistration;
import dev.abarmin.common.security.entity.AuthType;
import dev.abarmin.common.security.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserRegistrationConverter {
    private final PasswordEncoder passwordEncoder;

    public UserEntity convert(UserRegistration registration) {
        return UserEntity.builder()
                .fullName(registration.getFullName())
                .email(registration.getEmail())
                .activated(true) // activated by default for now
                .authType(AuthType.REGISTERED)
                .password(passwordEncoder.encode(registration.getPassword()))
                .build();
    }
}
