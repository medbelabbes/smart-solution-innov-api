package com.example.smartsolutioninnovapi.services.impl;

import com.example.smartsolutioninnovapi.domain.Role;
import com.example.smartsolutioninnovapi.domain.User;
import com.example.smartsolutioninnovapi.domain.enumeration.UserStatus;
import com.example.smartsolutioninnovapi.dto.UserDto;
import com.example.smartsolutioninnovapi.repositories.RoleRepository;
import com.example.smartsolutioninnovapi.repositories.UserRepository;
import com.example.smartsolutioninnovapi.utils.Global;
import com.example.smartsolutioninnovapi.utils.responses.CollectionResponse;
import com.example.smartsolutioninnovapi.utils.responses.Response;
import com.example.smartsolutioninnovapi.services.UserService;
import com.example.smartsolutioninnovapi.utils.Mapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
    private final Global global = new Global();

    public Mapper mapper = new Mapper();


    @Override
    public Response saveUser(User user, User connectedAdmin) {
        log.info("Saving new user {} to the database", user.getName());
        Response response = new Response(false, "", null);
        try {
            user.setPassword(global.hashPassword(user.getPassword()));
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
    public Response updateUser(UserDto userDto, User connectedAdmin) {
        log.info("Updating  user {} to the database", userDto.getName());
        Response response = new Response(false, "", null);
        try {
            User user = userRepository.findAdminById(userDto.getId());
            if(user == null) {
                response.setMessage("User not found");
                response.setData(null);
            } else {
                user.setId(userDto.getId());
                user.setName(userDto.getName());
                user.setUsername(userDto.getUsername());
                user.setEmail(userDto.getEmail());
                user.setStatus(userDto.getStatus());
                ArrayList<Role> roles = new ArrayList<>();
                userDto.getRoles().forEach(role -> {
                    roles.add(new Role(role.getId(), role.getName()));
                });
                user.setRoles(roles);
                user.setLastModifiedBy(connectedAdmin.getId());
                user.setLastModifiedDate(new Date());
                user.setTasks(new ArrayList<>());
                userRepository.save(user);
                response.setStatus(true);
                response.setMessage("User updated successfully");
                response.setData(mapper.mapUserToDto(user));
            }
            return response;
        } catch (Exception e) {
            System.out.println("exception");
            response.setMessage("Exception: " + e.getMessage());
            response.setData(null);
            return response;
        }
    }

    @Override
    public Response deleteUser(UserDto userDto) {
        return null;
    }

    @Override
    public Response getUserById(long id) {
        log.info("Get User by id");
        Response response = new Response(false, "", null);
        try {
            User user = userRepository.findAdminById(id);
            if(user == null) {
                response.setMessage("Not found");
            } else {
                response.setStatus(true);
                response.setMessage("User fetched successfully");
                response.setData(mapper.mapUserToDto(user));
            }
            return response;
        } catch (Exception e) {
            response.setMessage("Exception: " + e.getMessage());
            response.setData(null);
            return response;
        }
    }

    @Override
    public User getUserByUsername(String username) {
        return  userRepository.findByUsername(username);
    }

    @Override
    public Response getUser(String username) {
        log.info("Fetching user {}", username);
        Response response = new Response(false, "", null);
        try {
            User user = userRepository.findByUsername(username);
            if(user == null) {
                response.setMessage("User  not found");
                response.setData(null);
            } else {
                response.setStatus(true);
                response.setMessage("User fetched successfully");
                response.setData(mapper.mapUserToDto(user));
            }
            return response;
        } catch (Exception e) {
            response.setMessage("Exception: " + e.getMessage());
            response.setData(null);
            return response;
        }
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
            response.setMessage(usersPage.getContent().size() > 0 ? "Users fetched successfully" : "Users list empty");
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
    public Response updatePassword(UserDto userDto) throws Exception {
        return null;
    }

    @Override
    public boolean changeUserStatus(long id, UserStatus userStatus) throws Exception {
        return false;
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
