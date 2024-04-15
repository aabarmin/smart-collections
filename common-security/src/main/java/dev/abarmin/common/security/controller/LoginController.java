package dev.abarmin.common.security.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginController {
    public static final String LOGIN_ENDPOINT = "/login";
    @GetMapping(LOGIN_ENDPOINT)
    public ModelAndView login(ModelAndView modelAndView) {
        modelAndView.setViewName("user/login");
        return modelAndView;
    }
}
