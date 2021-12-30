package com.example.smartsolutioninnovapi.services.impl;

import com.example.smartsolutioninnovapi.domain.Role;
import com.example.smartsolutioninnovapi.domain.User;
import com.example.smartsolutioninnovapi.domain.enumeration.UserStatus;
import com.example.smartsolutioninnovapi.dto.RoleDto;
import com.example.smartsolutioninnovapi.dto.UserDto;
import com.example.smartsolutioninnovapi.repositories.AdminRepository;
import com.example.smartsolutioninnovapi.repositories.UserRepository;
import com.example.smartsolutioninnovapi.services.AdminService;
import com.example.smartsolutioninnovapi.utils.Global;
import com.example.smartsolutioninnovapi.utils.Mapper;
import com.example.smartsolutioninnovapi.utils.responses.CollectionResponse;
import com.example.smartsolutioninnovapi.utils.responses.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class AdminServiceImpl implements AdminService {

    private final AdminRepository adminRepository;
    private final UserRepository userRepository;
    private final Global global = new Global();

    public Mapper mapper = new Mapper();


    @Override
    public Response saveAdmin(User admin, User connectedAdmin) {
        log.info("Saving new user {} to the database", admin.getName());
        Response response = new Response(false, "", null);
        try {
            admin.setPassword(global.hashPassword(admin.getPassword()));
            admin.setCreationDate(new Date());
            User savedUser = adminRepository.save(admin);
            if (connectedAdmin != null) {
                admin.setCreatedBy(connectedAdmin.getId());
            }
            response.setStatus(true);
            response.setMessage("Admin added successfully");
            response.setData(mapper.mapUserToDto(savedUser));
            return response;
        } catch (Exception e) {
            response.setMessage("Exception: " + e.getMessage());
            response.setData(null);
            return response;
        }
    }

    @Override
    public Response updateAdmin(UserDto userDto, User connectedAdmin) {
        log.info("Updating  admin {} to the database", userDto.getName());
        Response response = new Response(false, "", null);
        try {
            User user = adminRepository.findAdminById(userDto.getId());
            if (user == null) {
                response.setMessage("Admin not found");
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
                adminRepository.save(user);
                response.setStatus(true);
                response.setMessage("Admin updated successfully");
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
    public Response deleteAdmin(Long id, User connectedAdmin) {
        log.info("Delete admin admin");
        Response response = new Response(false, "", null);
        try {
            User admin = adminRepository.findAdminById(id);
            if (admin == null) {
                response.setMessage("Not found");
            } else {
                List<User> users = userRepository.findUserByAdmin(admin.getId());
                if (users.size() > 0) {
                    users.forEach(u -> {
                        u.setCreatedBy(null);
                        if(u.getLastModifiedBy() == admin.getId()) {
                            u.setLastModifiedBy(null);
                        }
                        userRepository.save(u);
                    });
                }
                adminRepository.delete(admin);
                response.setStatus(true);
                response.setMessage("Admin deleted");
                response.setData(null);
            }
            return response;
        } catch (Exception e) {
            response.setMessage("Exception: " + e.getMessage());
            response.setData(null);
            return response;
        }
    }

    @Override
    public Response getAdminById(long id) {
        log.info("Get admin by id");
        Response response = new Response(false, "", null);
        try {
            User user = adminRepository.findAdminById(id);
            if (user == null) {
                response.setMessage("Not found");
            } else {
                response.setStatus(true);
                response.setMessage("Admin fetched successfully");
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
    public Response getAdminByUsername(String username) {
        return null;
    }

    @Override
    public CollectionResponse getAdmins(String query, String role, Optional<Integer> page, Optional<Integer> size, Optional<String> sortBy) {
        CollectionResponse response = new CollectionResponse(false, "", new ArrayList<>(), 0);
        try {
            String searchQuery = query.toLowerCase();
            Page<User> adminsPage = this.adminRepository.findAll(
                    searchQuery,
                    role,
                    PageRequest.of(page.orElse(0),
                            size.orElse(50),
                            Sort.Direction.DESC, sortBy.orElse("creationDate")));
            response.setStatus(true);
            response.setMessage(adminsPage.getContent().size() > 0 ? "Admins fetched successfully" : "Admins list empty");
            ArrayList<UserDto> admins = new ArrayList<>();
            adminsPage.getContent().forEach(admin -> {
                admins.add(mapper.mapUserToDto(admin));
            });
            response.setData(admins);
            response.setCount((int) adminsPage.getTotalElements());
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
}
