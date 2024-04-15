package dev.abarmin.common.security.service;

import dev.abarmin.common.security.repository.UserEntityRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GoogleAuthSuccessAuthHandler implements AuthenticationSuccessHandler {
    private final UserEntityRepository repository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication)  {

        // todo: check that user is not in the database and next create it
    }
}
