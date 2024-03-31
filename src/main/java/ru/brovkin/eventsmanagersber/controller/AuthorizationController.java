package ru.brovkin.eventsmanagersber.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.brovkin.eventsmanagersber.model.Role;
import ru.brovkin.eventsmanagersber.model.User;
import ru.brovkin.eventsmanagersber.service.RoleService;
import ru.brovkin.eventsmanagersber.service.UserService;

import java.util.List;

@Controller
@RequestMapping("/event/authorize")
public class AuthorizationController {

    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public AuthorizationController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/login")
    public String loginPageHtml() {
        return "login";
    }

    @GetMapping("/registration")
    public String registrationPageHtml(Model model) {
        List<Role> roles = roleService.getAllRoles();
        model.addAttribute("roles", roles);
        model.addAttribute("user", new User());
        return "registration";
    }

    @PostMapping("/registration")
    public String addUserToSystem(@ModelAttribute("user") User user, Model model) {
        try {
            userService.addUser(user);
            return "redirect:login";
        } catch (DataIntegrityViolationException e) {
            model.addAttribute("message", "Логин: " + user.getUsername() + " уже существует!");
            return "registration";
        }
    }

}
