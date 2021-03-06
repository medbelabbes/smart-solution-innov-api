package com.example.smartsolutioninnovapi.services;

import com.example.smartsolutioninnovapi.domain.User;
import com.example.smartsolutioninnovapi.domain.enumeration.UserStatus;
import com.example.smartsolutioninnovapi.dto.UserDto;
import com.example.smartsolutioninnovapi.utils.responses.CollectionResponse;
import com.example.smartsolutioninnovapi.utils.responses.Response;

import java.util.Optional;

public interface UserService {
    Response saveUser(User user, User connectedAdmin);

    Response updateUser(UserDto userDto, User connectedAdmin);


    Response deleteUser(UserDto userDto);


    Response getUserById(long id);


    User getUserByUsername(String username);

    Response getUser(String username);

    CollectionResponse getUsers(String query, Optional<Integer> page, Optional<Integer> size, Optional<String> sortBy);


    Response updatePassword(UserDto userDto) throws Exception;

    boolean changeUserStatus(long id, UserStatus userStatus) throws Exception;


}
