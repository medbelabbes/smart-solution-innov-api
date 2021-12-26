package com.example.smartsolutioninnovapi.api;

import com.example.smartsolutioninnovapi.domain.User;
import com.example.smartsolutioninnovapi.responses.CollectionResponse;
import com.example.smartsolutioninnovapi.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/users")
    public ResponseEntity<List<User>> getUsers(@RequestParam Optional<Integer> page,
                                         @RequestParam Optional<Integer> size,
                                         @RequestParam Optional<String> sortBy) {
        return ResponseEntity.ok().body(userService.getUsers());
    }
}
