package dev.abarmin.common.security.controller;

import dev.abarmin.common.security.controller.converter.UserRegistrationConverter;
import dev.abarmin.common.security.controller.model.UserRegistration;
import dev.abarmin.common.security.repository.UserEntityRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class RegistrationController {
    public static final String REGISTRATION_ENDPOINT = "/register";

    private final UserEntityRepository userRepository;
    private final UserRegistrationConverter converter;

    @ModelAttribute("form")
    public UserRegistration userRegistration() {
        return new UserRegistration();
    }

    @GetMapping(REGISTRATION_ENDPOINT)
    public String registerForm(@ModelAttribute("form") UserRegistration form,
                               @RequestParam("email") String email) {

        if (StringUtils.isNoneEmpty(email)) {
            form.setEmail(email);
        }
        return "user/registration_form";
    }

    @PostMapping(REGISTRATION_ENDPOINT)
    public String tryRegister(@Valid @ModelAttribute("form") UserRegistration form,
                              BindingResult bindingResult) {

        if (userRepository.findByEmail(form.getEmail()).isPresent()) {
            bindingResult.addError(new FieldError(
                    "form",
                    "email",
                    "User with this email already exists"));
        }

        if (bindingResult.hasErrors()) {
            return "user/registration_form";
        }

        // create a new user
        userRepository.save(converter.convert(form));
        return "user/registration_success";
    }

}
