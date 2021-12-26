package com.example.smartsolutioninnovapi.services;

import com.example.smartsolutioninnovapi.domain.Role;

public interface RoleService {

    Role saveRole(Role role);
    void addRoleToUser(String username, String roleName);

}
