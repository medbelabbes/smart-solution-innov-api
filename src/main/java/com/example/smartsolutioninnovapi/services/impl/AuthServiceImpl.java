package com.example.smartsolutioninnovapi.services.impl;

import com.example.smartsolutioninnovapi.config.JwtUtil;
import com.example.smartsolutioninnovapi.domain.Role;
import com.example.smartsolutioninnovapi.domain.User;
import com.example.smartsolutioninnovapi.domain.enumeration.UserStatus;
import com.example.smartsolutioninnovapi.dto.UserDto;
import com.example.smartsolutioninnovapi.repositories.AuthRepository;
import com.example.smartsolutioninnovapi.repositories.RoleRepository;
import com.example.smartsolutioninnovapi.repositories.UserRepository;
import com.example.smartsolutioninnovapi.services.UserService;
import com.example.smartsolutioninnovapi.utils.Global;
import com.example.smartsolutioninnovapi.utils.responses.Response;
import com.example.smartsolutioninnovapi.services.AuthService;
import com.example.smartsolutioninnovapi.utils.Mapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.util.Date;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {
    private final AuthRepository authRepository;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserServiceImpl userServiceimpl;

    private final Global global = new Global();

    public Mapper mapper = new Mapper();

    @Override
    public Response register(User user) {
        log.info("Registering new user {} to the database", user.getName());
        Response response = new Response(false, "", null);
        try {
            User existedUsername = userRepository.findByUsername(user.getUsername());
            if (existedUsername == null) {
                User existedEmail = userRepository.findByEmail(user.getEmail());
                if (existedEmail == null) {
                    user.setPassword(global.hashPassword(user.getPassword()));
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
            if (e instanceof DataIntegrityViolationException) {
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
            if (currentUser == null) {
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

    @Override
    public Boolean login(UserDto userDto) throws Exception {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(userDto.getPassword().getBytes());
        byte[] digest = md.digest();
        String hash = DatatypeConverter
                .printHexBinary(digest).toUpperCase();
        System.out.println(userDto.getPassword());
        System.out.println(hash);
        boolean b = false;
        System.out.println(userDto.getUsername());
        User user = this.authRepository.findByUsernameAndPassword(userDto.getUsername().toLowerCase(), hash);
        if (user != null) {
            System.out.println(user.getId());
            b = true;
        }
        return b;
    }

    @Override
    public Response generateToken(UserDto userDto, JwtUtil jwtTokenUtil) {
        Response response = new Response();
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode node;

        try {
            if (userDto.getUsername().equals("") || userDto.getPassword().equals("")) {
                log.error("Code accés invalides 1");
                response.setStatus(false);
                response.setMessage("Codes d'accés invalides");
                response.setData(null);
            } else {
                if (this.login(userDto)) {
                    final UserDetails userDetails = userServiceimpl
                            .loadUserByUsername(userDto.getUsername().toLowerCase());
                    final String accessToken = jwtTokenUtil.generateToken(userDetails,
                            new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24));
                    final String refreshToken = jwtTokenUtil.generateToken(userDetails,
                            new Date(System.currentTimeMillis() + 1000L * 60 * 60 * 24 * 30));
                    response.setStatus(true);
                    response.setMessage("Connecté");

                    User user = this.authRepository.findByUsername(userDto.getUsername().toLowerCase());
                    if (user.getStatus().equals(UserStatus.VALIDATED)) {
                        node = objectMapper.createObjectNode();
                        node.put("accessToken", accessToken);
                        node.put("refreshToken", refreshToken);
                        node.put("expiresIn", 86400);
                        response.setData(node);
                    } else {
                        log.error("Compte " + user.getStatus().toString().toLowerCase() + " Échec d'accès, veuillez contacter votre administrateur pour plus d'informations");
                        response.setStatus(false);
                        response.setMessage("Compte " + user.getStatus().toString().toLowerCase() + " Échec d'accès, veuillez contacter votre administrateur pour plus d'informations");
                        response.setData(null);
                    }


                } else {
                    log.error("Code accés invalides 2");
                    response.setStatus(false);
                    response.setMessage("Codes d'accés invalides");
                    response.setData(null);
                }
            }


            return response;
        } catch (Exception e) {
            log.error("Code accés invalides Exception");
            response.setStatus(false);
            response.setMessage("Exception " + e.getMessage());
            response.setData(null);
            return response;
        }

    }


}
