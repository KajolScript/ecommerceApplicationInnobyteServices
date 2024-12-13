package com.internship.ecommerce.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.internship.ecommerce.modal.User;
import com.internship.ecommerce.repo.UserRepository;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User registerUser(User user, String role) {
        if (role == null || role.isEmpty()) {
            role = "USER";  // Default to "USER" if no role is provided
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(role.toUpperCase());  // Ensure role is in uppercase
        return userRepository.save(user);
    }
    
    public User updateUser(String id, User updatedUser) {
        Optional<User> optionalUser = userRepository.findById(id);

        if (optionalUser.isEmpty()) {
            throw new IllegalArgumentException("User not found with ID: " + id);
        }

        User existingUser = optionalUser.get();

        // Update only allowed fields
        if (updatedUser.getUsername() != null) {
            existingUser.setUsername(updatedUser.getUsername());
        }
        if (updatedUser.getEmail() != null) {
            existingUser.setEmail(updatedUser.getEmail());
        }
        if (updatedUser.getAddress() != null) {
            existingUser.setAddress(updatedUser.getAddress());
        }

        return userRepository.save(existingUser);
    }

    public User findById(String id) {
        Optional<User> userOptional = userRepository.findById(id);  // Assuming you're using MongoDB

        return userOptional.orElse(null);  // Return user or null if not found
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public boolean checkPassword(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

}
