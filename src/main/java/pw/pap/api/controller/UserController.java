package pw.pap.api.controller;

import java.util.List;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import pw.pap.api.dto.UserAndEmailDTO;
import pw.pap.model.Project;
import pw.pap.model.User;
import pw.pap.service.UserService;
import pw.pap.api.dto.UserAndPasswordDTO;


@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<User> loginUser(@RequestBody UserAndPasswordDTO userAndPasswordDTO) {
        try {
            User user = userService.login(userAndPasswordDTO.getName(), userAndPasswordDTO.getPassword());
            return ResponseEntity.ok(user);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }

    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody UserAndPasswordDTO userAndPasswordDTO) {
        try {
            User user = userService.register(userAndPasswordDTO.getName(), userAndPasswordDTO.getPassword());
            return ResponseEntity.status(HttpStatus.CREATED).body(user);
        } catch (EntityExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        }
    }

    @PostMapping("/googleLogin")
    public ResponseEntity<User> googleLoginUser(@RequestBody UserAndEmailDTO userAndEmailDTO) {
        User user = userService.googleLogin(userAndEmailDTO.getName(), userAndEmailDTO.getEmail());
        return ResponseEntity.ok(user);
    }

    @GetMapping("/{userId}/projects")
    public ResponseEntity<Page<Project>> getMemberProjectsWithPaging(@PathVariable Long userId,
                                                                     @RequestParam(defaultValue =  "0") int page,
                                                                     @RequestParam(defaultValue = "5") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Project> memberProjects = userService.getMemberProjectsWithPaging(userId, pageable);
        return new ResponseEntity<>(memberProjects, HttpStatus.OK);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<User> getUser(@PathVariable Long userId) {
        User user = userService.getUserById(userId);
        if (user != null) {
            return new ResponseEntity<>(user, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/update/{userId}")
    public ResponseEntity<User> updateUser(@PathVariable Long userId, @RequestBody User updatedUser) {
        try {
            User updated = userService.updateUser(userId, updatedUser);
            return new ResponseEntity<>(updated, HttpStatus.OK);
        } catch (EntityExistsException e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {
        try {
            userService.deleteUser(userId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
