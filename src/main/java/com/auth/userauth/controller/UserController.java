package com.auth.userauth.controller;

import com.auth.userauth.entity.User;
import com.auth.userauth.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.auth.userauth.entity.LoginActivity;
import com.auth.userauth.repository.LoginActivityRepository;
import java.time.LocalDateTime;
import java.util.List;


@Controller
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private LoginActivityRepository loginActivityRepository;

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

        String encodedPassword = passwordEncoder.encode(password);
        userRepository.save(new User(username, encodedPassword,"USER"));

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
                            Model model,
                            HttpSession session) {

        return userRepository.findByUsername(username)
                .map(user -> {
                    if (passwordEncoder.matches(password, user.getPassword())) {

                        session.setAttribute("loggedInUser", username);

                        // Save login activity
                        LoginActivity activity = new LoginActivity(user, LocalDateTime.now());
                        loginActivityRepository.save(activity);

                        if ("ADMIN".equals(user.getRole())) {
                            return "redirect:/admin";
                        } else {
                            return "redirect:/dashboard";
                        }

                    }
                    else {
                        model.addAttribute("message", "Invalid password!");
                        return "login";
                    }
                })
                .orElseGet(() -> {
                    model.addAttribute("message", "User not found!");
                    return "login";
                });
    }

    @GetMapping("/dashboard")
    public String showDashboard(HttpSession session, Model model) {

        String username = (String) session.getAttribute("loggedInUser");

        if (username == null) {
            return "redirect:/login-page";
        }

        User user = userRepository.findByUsername(username).orElse(null);

        if (user != null) {

            long loginCount = loginActivityRepository.countByUser(user);
            List<LoginActivity> activities = loginActivityRepository.findByUser(user);

            model.addAttribute("username", username);
            model.addAttribute("loginCount", loginCount);
            model.addAttribute("activities", activities);
        }

        return "dashboard";
    }


    @PostMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login-page";
    }

    @GetMapping("/admin")
    public String showAdmin(HttpSession session, Model model) {

        String username = (String) session.getAttribute("loggedInUser");

        if (username == null) {
            return "redirect:/login-page";
        }

        User user = userRepository.findByUsername(username).orElse(null);

        if (user == null || !"ADMIN".equals(user.getRole())) {
            return "redirect:/dashboard";
        }

        model.addAttribute("users", userRepository.findAll());

        return "admin";
    }


}
