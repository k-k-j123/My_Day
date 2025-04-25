package com.example.Kaushik.My.Day.Controller;

import com.example.Kaushik.My.Day.Model.Users;
import com.example.Kaushik.My.Day.Service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/me")
    public ResponseEntity<Users> getUser(HttpServletRequest request) {
        Users user = userService.findUser(request);
        System.out.println("in controller "+ user.getUserId());
        if (user != null) {
            return ResponseEntity.ok(user);
        } 
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/allUser")
    public ResponseEntity<List<Users>> getAllUsers() {
        List<Users> users = userService.findAllUsers();
        return ResponseEntity.ok(users);
    }

    @PutMapping("/me")
    public ResponseEntity<String> updateUser(@RequestBody Users updatedUser, HttpServletRequest request) {
        boolean updated = userService.updateUser(updatedUser, request);
        if (updated) {
            return ResponseEntity.ok("User updated successfully.");
        } else {
            return ResponseEntity.badRequest().body("User not found.");
        }
    }

    @DeleteMapping("/me")
    public ResponseEntity<String> deleteUser(HttpServletRequest request) {
        boolean deleted = userService.deleteUser(request);
        if (deleted) {
            return ResponseEntity.ok("User deleted successfully.");
        } else {
            return ResponseEntity.badRequest().body("User not found.");
        }
    }
}
