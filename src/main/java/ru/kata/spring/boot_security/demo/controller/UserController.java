package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;
import java.security.Principal;
import java.util.List;
import java.util.Set;

@Controller
public class UserController {
    private UserService userService;
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping({"", "/", "/index"})
    public String index( Model model, Principal principal){
        boolean isAuthenticated;
        if(principal == null) {
            isAuthenticated = false;
        } else {
            isAuthenticated = true;
        }
        model.addAttribute("isAuthenticated", isAuthenticated);
        return "index";
    }

    @GetMapping("/user/{id}")
    public String UserForm(@PathVariable("id") Long id, Model model, @AuthenticationPrincipal(expression = "username") String username) {
        if(userService.findUserByEmail(username).getRoles().stream().allMatch(r -> r.getName().equals("ROLE_USER"))) {
            id = userService.findUserByEmail(username).getId();
        }
        User user = userService.findById(id).get();
        model.addAttribute("user", user);
        return "user";
    }

    @GetMapping("/admin")
    public String findAll(Model model){
        List<User> users = userService.findAll();
        model.addAttribute("users", users);
        return "admin";
    }
    @GetMapping("/admin/user-create")
    public String createUserForm(User user, Model model){
       List<Role> roles = userService.getRoleRepository().findAll();
       model.addAttribute("roles", roles);
        return "admin/user-create";
    }

    @PostMapping("/admin/user-create")
    public String createUser(User user){
        if(user.getRoles().size() == 0) {
            user.setRoles(Set.of(userService.getRoleRepository().findRoleByName("ROLE_USER")));
        }
        userService.save(user);
        return "redirect:/admin";
    }

    @PostMapping("/admin/user-delete/{id}")
    public String deleteUser(@PathVariable("id") Long id){
        userService.deleteById(id);
        return "redirect:/admin";
    }

    @GetMapping("admin/user-update/{id}")
    public String updateUserForm(@PathVariable("id") Long id, Model model){
        User user = userService.findById(id).get();
        model.addAttribute("user", user);
        List<Role> roles = userService.getRoleRepository().findAll();
        model.addAttribute("roles", roles);
        return "admin/user-update";
    }

    @PostMapping("admin/user-update")
    public String updateUser(User user){
        if(user.getRoles().size() == 0) {
            user.setRoles(Set.of(userService.getRoleRepository().findRoleByName("ROLE_USER")));
        }
        userService.save(user);
        return "redirect:/admin";
    }
}