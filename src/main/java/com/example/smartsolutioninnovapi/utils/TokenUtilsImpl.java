package com.example.smartsolutioninnovapi.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.smartsolutioninnovapi.config.JwtUtil;
import com.example.smartsolutioninnovapi.domain.User;
import com.example.smartsolutioninnovapi.repositories.UserRepository;
import com.example.smartsolutioninnovapi.services.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Service
@RequiredArgsConstructor
@Slf4j
public class TokenUtilsImpl implements TokenUtils {


    private final UserRepository userRepository;
    private final UserServiceImpl userServiceImpl;

    @Autowired
    private JwtUtil jwtUtil;


    @Override
    public User getCurrentUser(HttpServletRequest request) {
        System.out.println("here");
        final String authorizationHeader = request.getHeader("Authorization");
        String username;
        String jwt;

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwt = authorizationHeader.substring(7);
            username = jwtUtil.extractUsername(jwt);
            log.info("username from jwt is {}", username);
            return userRepository.findByUsername(username);
        } else {
            return null;
        }
    }
}
