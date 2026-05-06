package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;
import ru.kata.spring.boot_security.demo.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Controller
@RequestMapping("/admin")
public class AdminController {

    private UserService userService;

    private RoleRepository roleRepository;

    private PasswordEncoder passwordEncoder;
   @Autowired
       public AdminController(UserService userService, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }




/*    @GetMapping("/authenticated")
    public String pageOfAuthdUsers(Principal principal) {
        return "web service logged"+principal.getName();
    }*/
/*
    @GetMapping("/user1")
    public String user1(Principal principal) {
        return "web service user"+principal.getName();
    }*/

    @GetMapping("/new")
    public String newUser(Model model) {
        model.addAttribute("user", new User());
        return "admin/new";
    }

    @PostMapping("/new")
    public String add(@ModelAttribute("user") User user,
                      @RequestParam(value = "roleIds", required = false) List<Long> roleIds) {

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Устанавливаем роли через репозиторий
        Set<Role> roles = new HashSet<>();
        if (roleIds != null) {
            for (Long roleId : roleIds) {
                roleRepository.findById(roleId).ifPresent(roles::add);
            }
        }
        user.setRoles(roles);
        userService.saveUser(user);
        return "redirect:/admin/users";
    }

    @GetMapping("/users")
    public String listOfUsers(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "admin/users";
    }

    @GetMapping()
    public String adminInfo(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "admin/users";
    }


    @GetMapping("/edit")
    public String edit(Model model, @RequestParam("id") long id) {
        model.addAttribute("user", userService.getUserById(id));
        return "admin/edit";
    }

    @PatchMapping("/edit")
    public String update(@ModelAttribute("user") User userUpdated,
                         @RequestParam(value = "roleIds", required = false) List<Long> roleIds) {

        userUpdated.setPassword(passwordEncoder.encode(userUpdated.getPassword()));

        // Устанавливаем роли через репозиторий
        Set<Role> roles = new HashSet<>();
        if (roleIds != null) {
            for (Long roleId : roleIds) {
                roleRepository.findById(roleId).ifPresent(roles::add);
            }
        }
        userUpdated.setRoles(roles);
        userService.updateUser(userUpdated);
        return "redirect:/admin/users";
    }

    @DeleteMapping("/delete")
    public String delete(@RequestParam("id") long id) {
        userService.removeUserById(id);
        return "redirect:/admin/users";
    }

}
