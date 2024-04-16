package dev.abarmin.common.security.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {
    public static final String LOGIN_ENDPOINT = "/login";

    @GetMapping(LOGIN_ENDPOINT)
    public String login() {
        return "user/login";
    }
}
