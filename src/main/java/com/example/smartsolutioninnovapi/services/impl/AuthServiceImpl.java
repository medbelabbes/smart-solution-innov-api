package com.example.smartsolutioninnovapi.services.impl;

import com.example.smartsolutioninnovapi.domain.Role;
import com.example.smartsolutioninnovapi.domain.User;
import com.example.smartsolutioninnovapi.repositories.RoleRepository;
import com.example.smartsolutioninnovapi.repositories.UserRepository;
import com.example.smartsolutioninnovapi.responses.Response;
import com.example.smartsolutioninnovapi.services.AuthService;
import com.example.smartsolutioninnovapi.utils.Mapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public Mapper mapper = new Mapper();

    @Override
    public Response register(User user) {
        log.info("Registering new user {} to the database", user.getName());
        Response response = new Response(false, "", null);
        try {
            User existedUsername = userRepository.findByUsername(user.getUsername());
            if(existedUsername == null) {
                User existedEmail = userRepository.findByEmail(user.getEmail());
                if(existedEmail == null) {
                    user.setPassword(passwordEncoder.encode(user.getPassword()));
                    Role role = roleRepository.findByName("ROLE_USER");
                    if (role == null) {
                        response.setMessage("Role not found");
                        response.setData(null);
                    } else {
                        user.getRoles().add(role);
                        user.setCreationDate(new Date());
                        User savedUser = userRepository.save(user);
                        response.setStatus(true);
                        response.setMessage("User added successfully");
                        response.setData(mapper.mapUserToDto(savedUser));
                    }
                } else {
                    response.setMessage("Email already in use");
                }

            } else {
                response.setMessage("Username already in use");
            }


            return response;
        } catch (Exception e) {
            if(e instanceof DataIntegrityViolationException) {
                System.out.println("hhhhhhh");
            }
            response.setMessage("Exception: " + e.getMessage());
            response.setData(null);
            return response;
        }
    }

    @Override
    public Response getCurrentUser(User currentUser) {
        log.info("Get current user");
        Response response = new Response(false, "", null);
        try {
            if(currentUser == null) {
                response.setMessage("User not found");
                response.setData(null);
            } else {
                response.setStatus(true);
                response.setMessage("User fetched successfully");
                response.setData(mapper.mapUserToDto(currentUser));
            }
            return response;
        } catch (Exception e) {
            response.setMessage("Exception: " + e.getMessage());
            response.setData(null);
            return response;
        }
    }

}
