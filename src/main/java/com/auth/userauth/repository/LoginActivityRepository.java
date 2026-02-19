package com.auth.userauth.repository;

import com.auth.userauth.entity.LoginActivity;
import com.auth.userauth.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LoginActivityRepository extends JpaRepository<LoginActivity, Long> {

    List<LoginActivity> findByUser(User user);

    long countByUser(User user);
}
