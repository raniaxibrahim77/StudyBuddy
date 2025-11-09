package com.example.studybuddy.users;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserRepository users;

    public UserController(UserRepository users) {
        this.users = users;
    }

    @GetMapping
    public List<User> all() {
        return users.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> one(@PathVariable Long id) {
        return users.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<User> create(@Valid @RequestBody User u) {
        return ResponseEntity.ok(users.save(u));
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id,
                                           @RequestBody Map<String, Object> updates) {
        return users.findById(id).map(u -> {
            if (updates.containsKey("name")) u.setName((String) updates.get("name"));
            if (updates.containsKey("email")) u.setEmail((String) updates.get("email"));
            if (updates.containsKey("major")) u.setMajor((String) updates.get("major"));
            if (updates.containsKey("availability")) u.setAvailability((String) updates.get("availability"));
            if (updates.containsKey("role")) {

                String roleStr = String.valueOf(updates.get("role"));
                u.setRole(User.Role.valueOf(roleStr));
            }
            return ResponseEntity.ok(users.save(u));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!users.existsById(id)) return ResponseEntity.notFound().build();
        users.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
