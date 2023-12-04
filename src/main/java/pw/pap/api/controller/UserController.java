package pw.pap.api.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pw.pap.api.model.User;
import org.springframework.web.bind.annotation.RestController;
import pw.pap.service.UserService;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000") // Enables CORS for all endpoints in this controller
@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/all")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{userId}")
    public User getUser(@PathVariable Long userId) {
        return userService.getUserById(userId);
    }

    @PostMapping("/add")
    public User addUser(@RequestBody User user) {
        return userService.addUser(user);
    }

    @PutMapping("/update/{userId}")
    public User updateUser(@PathVariable Long userId, @RequestBody User updatedUser) {
        return userService.updateUser(userId, updatedUser);
    }

    @DeleteMapping("/delete/{userId}")
    public void deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
    }
}