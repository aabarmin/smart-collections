package dev.abarmin.common.security.service;

import dev.abarmin.common.security.entity.AuthType;
import dev.abarmin.common.security.entity.UserEntity;
import dev.abarmin.common.security.event.UserRegisteredEvent;
import dev.abarmin.common.security.repository.UserEntityRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GoogleAuthSuccessAuthHandler extends SavedRequestAwareAuthenticationSuccessHandler {
    private final UserEntityRepository repository;
    private final UserEntityConverter entityConverter;
    private final ApplicationEventPublisher eventPublisher;

    @Override
    @SneakyThrows
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) {

        super.onAuthenticationSuccess(request, response, authentication);
        if (authentication instanceof OAuth2AuthenticationToken token) {
            OAuth2User principal = token.getPrincipal();
            String email = principal.getAttribute("email");
            String fullName = principal.getAttribute("name");

            boolean isKnownUser = repository.findByEmailAndAuthType(email, AuthType.GOOGLE_OAUTH2).isPresent();
            if (!isKnownUser) {
                UserEntity saved = repository.save(UserEntity.builder()
                        .email(email)
                        .fullName(fullName)
                        .authType(AuthType.GOOGLE_OAUTH2)
                        .activated(true)
                        .build());

                eventPublisher.publishEvent(UserRegisteredEvent.builder()
                        .userInfo(entityConverter.convert(saved))
                        .build());
            }
        }
    }
}
