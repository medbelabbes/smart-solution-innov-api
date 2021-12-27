package com.example.smartsolutioninnovapi.utils;

import com.example.smartsolutioninnovapi.domain.Role;
import com.example.smartsolutioninnovapi.domain.User;
import com.example.smartsolutioninnovapi.dto.RoleDto;
import com.example.smartsolutioninnovapi.dto.UserDto;

import java.util.ArrayList;

public class Mapper {

    public UserDto mapUserToDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setName(user.getName());
        userDto.setUsername(user.getUsername());
        userDto.setEmail(user.getEmail());
        userDto.setStatus(user.getStatus());
        ArrayList<RoleDto> roles = new ArrayList<>();
        user.getRoles().forEach(role -> {
            roles.add(new RoleDto(role.getId(), role.getName()));
        });
        userDto.setRoles(roles);
        userDto.setCreatedBy(user.getCreatedBy());
        userDto.setCreationDate(user.getCreationDate());
        userDto.setLastModifiedBy(user.getLastModifiedBy());
        userDto.setLastModifiedDate(user.getLastModifiedDate());
        return userDto;
    }

    public RoleDto mapRoleToDto(Role role) {
        RoleDto roleDto = new RoleDto();
        roleDto.setId(role.getId());
        roleDto.setName(role.getName());
        return roleDto;
    }

}
