package dev.abarmin.common.security.controller;

import dev.abarmin.common.security.controller.converter.UserRegistrationConverter;
import dev.abarmin.common.security.controller.model.UserRegistration;
import dev.abarmin.common.security.entity.UserEntity;
import dev.abarmin.common.security.event.UserRegisteredEvent;
import dev.abarmin.common.security.repository.UserEntityRepository;
import dev.abarmin.common.security.service.UserEntityConverter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ApplicationEventPublisher;
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
    private final UserRegistrationConverter formConverter;
    private final UserEntityConverter entityConverter;
    private final ApplicationEventPublisher eventPublisher;

    @ModelAttribute("form")
    public UserRegistration userRegistration() {
        return new UserRegistration();
    }

    @GetMapping(REGISTRATION_ENDPOINT)
    public String registerForm(@ModelAttribute("form") UserRegistration form,
                               @RequestParam(value = "email", required = false) String email) {

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
        UserEntity saved = userRepository.save(formConverter.convert(form));
        eventPublisher.publishEvent(UserRegisteredEvent.builder()
                .userInfo(entityConverter.convert(saved))
                .build());

        return "user/registration_success";
    }

}
