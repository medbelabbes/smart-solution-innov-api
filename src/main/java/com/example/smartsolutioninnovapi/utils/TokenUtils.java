package com.example.smartsolutioninnovapi.utils;

import com.example.smartsolutioninnovapi.domain.User;

import javax.servlet.http.HttpServletRequest;

public interface TokenUtils {
    User getCurrentUser(HttpServletRequest request);
}
