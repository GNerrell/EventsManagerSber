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

/**
 * Класс для упавления событиями, связанными с авторизацией и регистрацией пользователя
 */
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

    /**
     *
     * @return перенос на страницу авторизации
     */
    @GetMapping("/login")
    public String loginPageHtml() {
        return "login";
    }

    /**
     *
     * @param model - передача на форму нового пользователя и всех доступных ролей
     * @return перенос на страницу регистрации
     */
    @GetMapping("/registration")
    public String registrationPageHtml(Model model) {
        List<Role> roles = roleService.getAllRoles();
        model.addAttribute("roles", roles);
        model.addAttribute("user", new User());
        return "registration";
    }

    /**
     * Добавление пользователя в базу данных
     * @param user - пользователь добавляемый в базу даннных
     * @param model - передача сообщения о существующем пользователе
     * @return возрат на страницу авторизации при успешной регистрации и остаться на странице регистрации в другом случае
     */
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
