package dev.abarmin.common.security.service;

import dev.abarmin.common.security.domain.UserInfo;
import dev.abarmin.common.security.entity.AuthType;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SessionService {
    private final CustomUserDetailsService userDetailsService;

    public Optional<UserInfo> getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof UsernamePasswordAuthenticationToken token) {
            if (token.getPrincipal() instanceof UserInfo userInfo) {
                return Optional.of(userInfo);
            }
            throw new UnsupportedOperationException("Unsupported principal type: " + token.getPrincipal().getClass());
        } else if (authentication instanceof OAuth2AuthenticationToken token) {
            String email = token.getPrincipal().getAttribute("email");
            return userDetailsService.loadUser(email, AuthType.GOOGLE_OAUTH2);
        }
        return Optional.empty();
    }
}
