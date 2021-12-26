package com.example.smartsolutioninnovapi.services.impl;

import com.example.smartsolutioninnovapi.domain.Role;
import com.example.smartsolutioninnovapi.domain.User;
import com.example.smartsolutioninnovapi.repositories.RoleRepository;
import com.example.smartsolutioninnovapi.repositories.UserRepository;
import com.example.smartsolutioninnovapi.services.RoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

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
}
