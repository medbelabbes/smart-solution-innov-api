package com.example.smartsolutioninnovapi.services;

import com.example.smartsolutioninnovapi.domain.User;
import com.example.smartsolutioninnovapi.domain.enumeration.UserStatus;
import com.example.smartsolutioninnovapi.dto.UserDto;
import com.example.smartsolutioninnovapi.utils.responses.CollectionResponse;
import com.example.smartsolutioninnovapi.utils.responses.Response;

import java.util.Optional;

public interface AdminService {

    Response saveAdmin(User user, User connectedAdmin);

    Response updateAdmin(UserDto userDto, User connectedAdmin);

    Response deleteAdmin(Long id, User connectedAdmin);


    Response getAdminById(long id);


    Response getAdminByUsername(String username);


    CollectionResponse getAdmins(String query, String role, Optional<Integer> page, Optional<Integer> size, Optional<String> sortBy);


    Response updatePassword(UserDto userDto) throws Exception;

    boolean changeUserStatus(long id, UserStatus userStatus) throws Exception;


}
