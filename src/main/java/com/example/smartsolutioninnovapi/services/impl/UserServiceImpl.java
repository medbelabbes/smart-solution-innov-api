package com.example.smartsolutioninnovapi.services.impl;

import com.example.smartsolutioninnovapi.domain.Role;
import com.example.smartsolutioninnovapi.domain.User;
import com.example.smartsolutioninnovapi.dto.UserDto;
import com.example.smartsolutioninnovapi.repositories.RoleRepository;
import com.example.smartsolutioninnovapi.repositories.UserRepository;
import com.example.smartsolutioninnovapi.responses.CollectionResponse;
import com.example.smartsolutioninnovapi.responses.Response;
import com.example.smartsolutioninnovapi.services.UserService;
import com.example.smartsolutioninnovapi.utils.Mapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public Mapper mapper = new Mapper();


    @Override
    public Response saveUser(User user, User connectedAdmin) {
        log.info("Saving new user {} to the database", user.getName());
        Response response = new Response(false, "", null);
        try {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            Role role = roleRepository.findByName("ROLE_USER");
            if (role == null) {
                response.setMessage("Role not found");
                response.setData(null);
            } else {
                user.getRoles().add(role);
                user.setCreationDate(new Date());
                User savedUser = userRepository.save(user);
                if (connectedAdmin != null) {
                    user.setCreatedBy(connectedAdmin.getId());
                }
                response.setStatus(true);
                response.setMessage("User added successfully");
                response.setData(mapper.mapUserToDto(savedUser));
            }

            return response;
        } catch (Exception e) {
            response.setMessage("Exception: " + e.getMessage());
            response.setData(null);
            return response;
        }

    }

    @Override
    public User getUser(String username) {
        log.info("Fetching user {}", username);
        return userRepository.findByUsername(username);
    }

    @Override
    public CollectionResponse getUsers(String query, Optional<Integer> page, Optional<Integer> size, Optional<String> sortBy) {
        CollectionResponse response = new CollectionResponse(false, "", new ArrayList<>(), 0);
        try {
            String searchQuery = query.toLowerCase();
            Page<User> usersPage = this.userRepository.findAll(
                    searchQuery,
                    PageRequest.of(page.orElse(0),
                            size.orElse(50),
                            Sort.Direction.DESC, sortBy.orElse("creationDate")));
            response.setStatus(true);
            response.setMessage(usersPage.getContent().size() == 0 ? "Users fetched successfully" : "Users list empty");
            ArrayList<UserDto> users = new ArrayList<>();
            usersPage.getContent().forEach(user -> {
                users.add(mapper.mapUserToDto(user));
            });
            response.setData(users);
            response.setCount((int) usersPage.getTotalElements());
            return response;
        } catch (Exception e) {
            response.setMessage("Exception: " + e.getMessage());
            return response;
        }

    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            log.error("User not found in the database");
            throw new UsernameNotFoundException("User not found in the database");
        } else {
            log.info("User found in the database: {}", username);
        }
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        user.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        });
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                authorities
        );
    }
}
