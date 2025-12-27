package com.payment.online.paymentsystem.service;

import com.payment.online.paymentsystem.entity.User;
import com.payment.online.paymentsystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserserviceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;  // BCryptPasswordEncoder

    @Override
    public User registerUser(User user) {
        // 1. Password encrypt karo
        String encryptedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encryptedPassword);

        // 2. Default role set karo
        user.setRole("USER");

        // 3. Database mein save karo
        return userRepository.save(user);
    }

    @Override
    public User login(String email, String password) {
        // 1. Email se user find karo
        User user = userRepository.findByEmail(email);

        if (user != null) {
            // 2. Password match karo (BCrypt comparison)
            boolean isPasswordMatch = passwordEncoder.matches(password, user.getPassword());
            if (isPasswordMatch) {
                return user;
            }
        }
        return null;
    }

    @Override
    public boolean validateUser(String email, String password) {
        User user = userRepository.findByEmail(email);
        if (user != null) {
            return passwordEncoder.matches(password, user.getPassword());
        }
        return false;
    }
}