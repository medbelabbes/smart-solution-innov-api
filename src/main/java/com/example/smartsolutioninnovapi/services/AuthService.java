package com.example.smartsolutioninnovapi.services;

import com.example.smartsolutioninnovapi.domain.User;
import com.example.smartsolutioninnovapi.responses.Response;

public interface AuthService {
    Response register(User user);

}
