package com.internship.ecommerce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.internship.ecommerce.modal.User;
import com.internship.ecommerce.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody User user, @RequestParam(required = false) String role) {
        User registeredUser = userService.registerUser(user, role);
        return ResponseEntity.ok(registeredUser);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable("id") String id) {
        User user = userService.findById(id);

        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<?> updateUserProfile(@PathVariable("id") String id, @RequestBody User updatedUser) {
        try {
            User user = userService.updateUser(id, updatedUser);
            return ResponseEntity.ok(user);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build(); // If user is not found
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error updating user: " + e.getMessage());
        }
    }
}
    
    


