package dev.abarmin.common.security.service;

import dev.abarmin.common.security.entity.AuthType;
import dev.abarmin.common.security.repository.UserEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserEntityRepository userRepository;
    private final UserInfoConverter detailsConverter;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmailAndAuthType(username, AuthType.REGISTERED)
                .map(detailsConverter::convert)
                .orElseThrow(() -> new UsernameNotFoundException(String.format(
                        "User with username %s not found",
                        username
                )));
    }
}
