package com.example.smartsolutioninnovapi.api;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.smartsolutioninnovapi.domain.Role;
import com.example.smartsolutioninnovapi.domain.User;
import com.example.smartsolutioninnovapi.responses.CollectionResponse;
import com.example.smartsolutioninnovapi.responses.Response;
import com.example.smartsolutioninnovapi.services.UserService;
import com.example.smartsolutioninnovapi.utils.TokenUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.util.*;


@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
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


    @PostMapping("/user/save")
    public ResponseEntity<Response> saveUser(@RequestBody User user, HttpServletRequest request) {
        System.out.println("here1");
        User currentUser = tokenUtils.getCurrentUser(request);
        System.out.println(currentUser.getId());
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("api/user/save").toUriString());
        return ResponseEntity.created(uri).body(userService.saveUser(user, currentUser));
    }


}
