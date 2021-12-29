package com.example.smartsolutioninnovapi.services;

import com.example.smartsolutioninnovapi.config.JwtUtil;
import com.example.smartsolutioninnovapi.domain.User;
import com.example.smartsolutioninnovapi.dto.UserDto;
import com.example.smartsolutioninnovapi.utils.responses.Response;

import java.security.NoSuchAlgorithmException;

public interface AuthService {
    Response register(User user);

    Response getCurrentUser(User currentUser);

    Boolean login (UserDto userDto) throws NoSuchAlgorithmException, Exception;

    Response generateToken(UserDto userDto, JwtUtil jwtTokenUtil);


}
