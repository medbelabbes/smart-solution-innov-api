package com.example.smartsolutioninnovapi.services;

import com.example.smartsolutioninnovapi.domain.Role;
import com.example.smartsolutioninnovapi.domain.User;
import com.example.smartsolutioninnovapi.responses.CollectionResponse;
import com.example.smartsolutioninnovapi.responses.Response;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface UserService {
    Response saveUser(User user, User connectedAdmin);

    User getUser(String username);

    CollectionResponse getUsers(String query, Optional<Integer> page, Optional<Integer> size, Optional<String> sortBy);


}
