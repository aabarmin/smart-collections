package dev.abarmin.common.security.service;

import dev.abarmin.common.security.domain.UserInfo;
import dev.abarmin.common.security.entity.AuthType;
import dev.abarmin.common.security.repository.UserEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserEntityRepository userRepository;
    private final UserInfoConverter detailsConverter;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return loadUser(username, AuthType.REGISTERED)
                .orElseThrow(() -> new UsernameNotFoundException(String.format(
                        "User with username %s not found",
                        username
                )));
    }

    public Optional<UserInfo> loadUser(String email, AuthType authType) {
        return userRepository.findByEmailAndAuthType(email, authType)
                .map(detailsConverter::convert);
    }
}
