package ru.kata.spring.boot_security.demo.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.kata.spring.boot_security.demo.model.User;

@Controller
@RequestMapping("/user")
public class UserController {

    // Доступно для USER и ADMIN
    @GetMapping
    public String userProfile(Authentication authentication, Model model) {
        // Получаем текущего пользователя
        User user = (User) authentication.getPrincipal();
        model.addAttribute("user", user);
        return "user/profile"; // /templates/user/profile.html
    }
}