package dev.abarmin.common.security.controller.converter;

import dev.abarmin.common.security.controller.model.UserRegistration;
import dev.abarmin.common.security.entity.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class UserRegistrationConverter {
    public UserEntity convert(UserRegistration registration) {
        throw new UnsupportedOperationException();
    }
}
