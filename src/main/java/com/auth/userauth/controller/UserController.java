package com.auth.userauth.controller;

import com.auth.userauth.entity.User;
import com.auth.userauth.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/register-page")
    public String showRegisterPage() {
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@RequestParam String username,
                               @RequestParam String password,
                               Model model) {

        if (userRepository.findByUsername(username).isPresent()) {
            model.addAttribute("message", "Username already exists!");
            return "register";
        }

        userRepository.save(new User(username, password));
        model.addAttribute("message", "Registration successful!");
        return "register";
    }

    @GetMapping("/login-page")
    public String showLoginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String loginUser(@RequestParam String username,
                            @RequestParam String password,
                            Model model) {

        return userRepository.findByUsername(username)
                .map(user -> {
                    if (user.getPassword().equals(password)) {
                        model.addAttribute("message", "Login successful!");
                    } else {
                        model.addAttribute("message", "Invalid password!");
                    }
                    return "login";
                })
                .orElseGet(() -> {
                    model.addAttribute("message", "User not found!");
                    return "login";
                });
    }
}
