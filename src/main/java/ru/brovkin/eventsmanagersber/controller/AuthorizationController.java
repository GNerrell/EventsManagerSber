package ru.brovkin.eventsmanagersber.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.brovkin.eventsmanagersber.exception.LuckOfDataException;
import ru.brovkin.eventsmanagersber.model.User;
import ru.brovkin.eventsmanagersber.service.UserService;

@Controller
@RequestMapping("/event/authorize")
public class AuthorizationController {

    private UserService userService;

    @Autowired
    public AuthorizationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String loginPageHtml() {
        return "login";
    }

    @GetMapping("/home")
    public String homePage() {
        return "home";
    }

    @GetMapping("/bad_login")
    public String badLogin() {
        return "bad_login";
    }
}
