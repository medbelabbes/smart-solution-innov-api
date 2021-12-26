package com.example.smartsolutioninnovapi.repositories;

import com.example.smartsolutioninnovapi.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
}
