package com.example.smartsolutioninnovapi.services;

import com.example.smartsolutioninnovapi.domain.Role;
import com.example.smartsolutioninnovapi.utils.responses.CollectionResponse;

import java.util.Optional;

public interface RoleService {

    Role saveRole(Role role);
    void addRoleToUser(String username, String roleName);

    CollectionResponse getAdminRoles(Optional<Integer> page, Optional<Integer> size, Optional<String> sortBy);
    CollectionResponse getUserRoles(Optional<Integer> page, Optional<Integer> size, Optional<String> sortBy);

}
