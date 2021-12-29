package com.example.smartsolutioninnovapi.services.impl;

import com.example.smartsolutioninnovapi.domain.Role;
import com.example.smartsolutioninnovapi.domain.User;
import com.example.smartsolutioninnovapi.dto.UserDto;
import com.example.smartsolutioninnovapi.repositories.RoleRepository;
import com.example.smartsolutioninnovapi.repositories.UserRepository;
import com.example.smartsolutioninnovapi.services.RoleService;
import com.example.smartsolutioninnovapi.utils.responses.CollectionResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;

    @Override
    public Role saveRole(Role role) {
        log.info("Saving new role {} to the database", role.getName());
        return roleRepository.save(role);
    }

    @Override
    public void addRoleToUser(String username, String roleName) {
        log.info("Adding role {} to user {}", roleName, username);
        User user = userRepository.findByUsername(username);
        Role role = roleRepository.findByName(roleName);
        user.getRoles().add(role);
    }

    @Override
    public CollectionResponse getAdminRoles(Optional<Integer> page, Optional<Integer> size, Optional<String> sortBy) {
        CollectionResponse response = new CollectionResponse(false, "", new ArrayList<>(), 0);
        try {
            Page<Role> rolesPage = this.roleRepository.findAdminRoles(
                    PageRequest.of(page.orElse(0),
                            size.orElse(50),
                            Sort.Direction.ASC, sortBy.orElse("id")));
            response.setStatus(true);
            response.setMessage(rolesPage.getContent().size() > 0 ? "Roles fetched successfully" : "Roles list empty");

            response.setData(rolesPage.getContent());
            response.setCount((int) rolesPage.getTotalElements());
            return response;
        } catch (Exception e) {
            response.setMessage("Exception: " + e.getMessage());
            return response;
        }

    }
}
