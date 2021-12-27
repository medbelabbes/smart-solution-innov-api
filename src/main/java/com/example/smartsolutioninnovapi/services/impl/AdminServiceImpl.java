package com.example.smartsolutioninnovapi.services.impl;

import com.example.smartsolutioninnovapi.domain.User;
import com.example.smartsolutioninnovapi.domain.enumeration.UserStatus;
import com.example.smartsolutioninnovapi.dto.UserDto;
import com.example.smartsolutioninnovapi.services.AdminService;
import com.example.smartsolutioninnovapi.utils.responses.CollectionResponse;
import com.example.smartsolutioninnovapi.utils.responses.Response;

import java.util.Optional;

public class AdminServiceImpl implements AdminService {
    @Override
    public Response saveAdmin(User user, User connectedAdmin) {
        return null;
    }

    @Override
    public Response updatedAdmin(UserDto userDto, User connectedAdmin) {
        return null;
    }

    @Override
    public Response deleteAdmin(UserDto userDto, User connectedAdmin) {
        return null;
    }

    @Override
    public Response getAdminById(long id) {
        return null;
    }

    @Override
    public Response getAdminByUsername(String username) {
        return null;
    }

    @Override
    public CollectionResponse getAdmins(String query, Optional<Integer> page, Optional<Integer> size, Optional<String> sortBy) {
        return null;
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
