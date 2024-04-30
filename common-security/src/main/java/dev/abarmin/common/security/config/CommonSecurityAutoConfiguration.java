package dev.abarmin.common.security.config;

import dev.abarmin.common.security.repository.UserEntityRepository;
import dev.abarmin.common.security.service.CustomUserDetailsService;
import dev.abarmin.common.security.service.GoogleAuthSuccessAuthHandler;
import dev.abarmin.common.security.service.UserInfoConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import static dev.abarmin.common.security.controller.LoginController.LOGGED_ENDPOINT;
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
    @Order(1)
    @Profile("local")
    public SecurityFilterChain h2ConsoleSecurityFilterChain(HttpSecurity http) throws Exception {
        http
                .securityMatcher(AntPathRequestMatcher.antMatcher("/h2-console/**"))
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(AntPathRequestMatcher.antMatcher("/h2-console/**")).permitAll()
                )
                .headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable))
                .csrf(csrf -> csrf.ignoringRequestMatchers(AntPathRequestMatcher.antMatcher("/h2-console/**")));
        return http.build();
    }

    @Bean
    @Order(2)
    public SecurityFilterChain basicSecurityFilterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(authorise -> authorise
                        .requestMatchers("/webjars/**").permitAll()
                        .requestMatchers(REGISTRATION_ENDPOINT).permitAll()
                        .requestMatchers("/error").permitAll()
                        .requestMatchers("/").permitAll()
                        .requestMatchers("/public/**").permitAll()
                        .requestMatchers("/img/public/**").permitAll()
                        .anyRequest().authenticated())
                .httpBasic(Customizer.withDefaults())
                .formLogin(login -> login
                        .loginPage(LOGIN_ENDPOINT)
                        .successForwardUrl(LOGGED_ENDPOINT)
                        .permitAll())
                .oauth2Login(login -> login
                        .successHandler(successAuthHandler)
                        .defaultSuccessUrl(LOGGED_ENDPOINT)
                        .loginPage(LOGIN_ENDPOINT))
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public CustomUserDetailsService userDetailsService(
            UserEntityRepository userRepository,
            UserInfoConverter userConverter) {
        return new CustomUserDetailsService(userRepository, userConverter);
    }
}
