package com.example.smartsolutioninnovapi.repositories;

import com.example.smartsolutioninnovapi.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
