package com.internship.ecommerce.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.internship.ecommerce.modal.LoginRequest;
import com.internship.ecommerce.modal.LoginResponse;
import com.internship.ecommerce.modal.User;
import com.internship.ecommerce.security.JwtUtil;
import com.internship.ecommerce.service.UserService;

@RestController
@RequestMapping("/login")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try {
            Optional<User> userOpt = userService.findByUsername(loginRequest.getUsername());
            if (userOpt.isEmpty()) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
            }

            User user = userOpt.get();
            if (!userService.checkPassword(loginRequest.getPassword(), user.getPassword())) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
            }
            if (!userService.checkPassword(loginRequest.getPassword(), user.getPassword())) {
                System.out.println("Password mismatch for username: " + loginRequest.getUsername());
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
            }

            // Generate JWT token
            String token = jwtUtil.generateToken(user.getId());
            return ResponseEntity.ok(new LoginResponse(token));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Login failed: " + e.getMessage());
        }
    }
}

