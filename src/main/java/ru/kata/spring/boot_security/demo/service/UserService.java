package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetailsService;


import org.springframework.security.crypto.password.PasswordEncoder;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;

import java.util.List;
import java.util.Optional;

public interface UserService extends UserDetailsService{
    User findUserByEmail(String email);
    List<User> findAll();
    Optional<User> findById(Long id);
    void saveUser(User user);
    void deleteById(Long id);
    RoleRepository getRoleRepository();
    void setPasswordEncoder(PasswordEncoder passwordEncoder);
    PasswordEncoder getPasswordEncoder();
}
