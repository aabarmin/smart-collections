package dev.abarmin.common.security.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class LoginController {
    public static final String LOGIN_ENDPOINT = "/login";
    public static final String LOGGED_ENDPOINT = "/logged";

    @GetMapping(LOGIN_ENDPOINT)
    public String login() {
        return "user/login";
    }

    @RequestMapping(method = {RequestMethod.GET, RequestMethod.POST}, value = LOGGED_ENDPOINT)
    public RedirectView redirectOnFormLogin() {
        return new RedirectView("/my");
    }
}
