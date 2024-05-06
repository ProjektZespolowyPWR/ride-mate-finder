package com.ridematefinder.userController;

import com.ridematefinder.service.UserService;
import com.ridematefinder.sql.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<?> addUser(@RequestBody User user) {
        try{
            User newUser = userService.addUser(user);
            return new ResponseEntity<>(newUser, HttpStatus.CREATED);
        } catch (IllegalStateException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    @GetMapping("/{email}")
    public ResponseEntity<?> getUser(@PathVariable String email) {
        try {
            Optional<User> user = userService.getUser(email);
            return user
                    .map(ResponseEntity::ok)
                    .orElseGet(() -> ResponseEntity.notFound().build());
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }
}
