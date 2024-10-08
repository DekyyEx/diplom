package ru.yurfff.vladbook.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.yurfff.vladbook.model.User;
import ru.yurfff.vladbook.service.UserService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.findAll();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        Optional<User> user = userService.findById(id);
        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Обновление пользователя по ID
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User user) {
        if (user == null) {
            return ResponseEntity.badRequest().build(); // Возвращаем 400, если пользователь в запросе null
        }

        if (!userService.existsById(id)) {
            return ResponseEntity.notFound().build(); // Возвращаем 404, если пользователя с таким id нет
        }

        User updatedUser = userService.update(id, user);
        return ResponseEntity.ok(updatedUser);
    }

    // Удаление пользователя по ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        if (!userService.existsById(id)) {
            return ResponseEntity.notFound().build(); // Возвращаем 404, если пользователя с таким id нет
        }
        userService.deleteById(id);
        return ResponseEntity.noContent().build(); // Возвращаем 204 (No Content) после удаления
    }
}
