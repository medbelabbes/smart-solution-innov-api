package com.example.smartsolutioninnovapi.api;

import com.example.smartsolutioninnovapi.domain.User;
import com.example.smartsolutioninnovapi.dto.UserDto;
import com.example.smartsolutioninnovapi.utils.responses.CollectionResponse;
import com.example.smartsolutioninnovapi.utils.responses.Response;
import com.example.smartsolutioninnovapi.services.UserService;
import com.example.smartsolutioninnovapi.utils.TokenUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.util.*;


@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class UserController {
    private final UserService userService;
    private final TokenUtils tokenUtils;

    @GetMapping("/users")
    public ResponseEntity<CollectionResponse> getUsers(
            @RequestParam String query,
            @RequestParam Optional<Integer> page,
            @RequestParam Optional<Integer> size,
            @RequestParam Optional<String> sortBy
    ) {
        return ResponseEntity.ok().body(userService.getUsers(query, page, size, sortBy));
    }


    @GetMapping("/users/get/{id}")
    public ResponseEntity<Response> getById(
            @PathVariable("id")  Long id
    ) {
        return ResponseEntity.ok().body(userService.getUserById(id));
    }

    @PostMapping("/user/save")
    public ResponseEntity<Response> saveUser(@RequestBody User user, HttpServletRequest request) {
        User currentUser = tokenUtils.getCurrentUser(request);
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("api/user/save").toUriString());
        return ResponseEntity.created(uri).body(userService.saveUser(user, currentUser));
    }

    @PatchMapping("/user/edit")
    public ResponseEntity<Response> editAdmin(@RequestBody UserDto admin, HttpServletRequest request) {
        User currentUser = tokenUtils.getCurrentUser(request);
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("api/user/edit").toUriString());
        return ResponseEntity.created(uri).body(userService.updateUser(admin, currentUser));
    }



}
