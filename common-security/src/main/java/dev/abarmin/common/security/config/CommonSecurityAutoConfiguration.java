package dev.abarmin.common.security.config;

import dev.abarmin.common.security.repository.UserEntityRepository;
import dev.abarmin.common.security.service.CustomUserDetailsService;
import dev.abarmin.common.security.service.GoogleAuthSuccessAuthHandler;
import dev.abarmin.common.security.service.UserInfoConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;

import static dev.abarmin.common.security.controller.LoginController.LOGIN_ENDPOINT;
import static dev.abarmin.common.security.controller.RegistrationController.REGISTRATION_ENDPOINT;

@EnableWebSecurity
@AutoConfiguration
@ComponentScan("dev.abarmin.common.security")
@EnableJdbcRepositories("dev.abarmin.common.security.repository")
public class CommonSecurityAutoConfiguration {
    @Autowired
    private GoogleAuthSuccessAuthHandler successAuthHandler;

    @Bean
    public SecurityFilterChain basicSecurityFilterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(authorise -> authorise
                        .requestMatchers("/webjars/**").permitAll()
                        .requestMatchers(REGISTRATION_ENDPOINT).permitAll()
                        .requestMatchers("/error").permitAll()
                        .anyRequest().authenticated())
                .httpBasic(Customizer.withDefaults())
                .formLogin(login -> login
                        .loginPage(LOGIN_ENDPOINT)
                        .permitAll())
                .oauth2Login(login -> login
                        .successHandler(successAuthHandler)
                        .loginPage(LOGIN_ENDPOINT))
                .build();
    }

    @Bean
    public UserDetailsService userDetailsService(
            UserEntityRepository userRepository,
            UserInfoConverter userConverter) {
        return new CustomUserDetailsService(userRepository, userConverter);
    }
}
