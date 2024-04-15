package dev.abarmin.common.security.controller.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class UserRegistration {
    @NotEmpty
    private String fullName;

    @Email
    @NotEmpty
    private String email;

    @NotEmpty
    private String password;
}
