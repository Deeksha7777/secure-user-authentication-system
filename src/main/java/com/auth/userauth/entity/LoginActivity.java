package com.auth.userauth.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "login_activity")
public class LoginActivity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime loginTime;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public LoginActivity() {}

    public LoginActivity(User user, LocalDateTime loginTime) {
        this.user = user;
        this.loginTime = loginTime;
    }

    public Long getId() { return id; }

    public LocalDateTime getLoginTime() { return loginTime; }

    public User getUser() { return user; }
}
