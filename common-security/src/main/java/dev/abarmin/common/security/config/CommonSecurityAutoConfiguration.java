package dev.abarmin.common.security.config;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import static dev.abarmin.common.security.controller.LoginController.LOGIN_ENDPOINT;

@EnableWebSecurity
@AutoConfiguration
@ComponentScan("dev.abarmin.common.security")
public class CommonSecurityAutoConfiguration {
    @Bean
    public SecurityFilterChain basicSecurityFilterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(authorise -> authorise
                        .requestMatchers("/webjars/**").permitAll()
                        .anyRequest().authenticated())
                .httpBasic(Customizer.withDefaults())
                .formLogin(login -> login
                        .loginPage(LOGIN_ENDPOINT)
                        .permitAll())
                .oauth2Login(login -> login
                        .loginPage(LOGIN_ENDPOINT))
                .build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        final UserDetails user = User.withDefaultPasswordEncoder()
                .username("test")
                .password("test")
                .build();
        return new InMemoryUserDetailsManager(user);
    }
}
