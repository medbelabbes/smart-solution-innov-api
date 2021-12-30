package com.example.smartsolutioninnovapi.repositories;

import com.example.smartsolutioninnovapi.domain.Role;
import com.example.smartsolutioninnovapi.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);

    @Query("select r from Role r  where r.name in ('ROLE_ADMIN', 'ROLE_SUPER_ADMIN')")
    Page<Role> findAdminRoles(Pageable pageable);

    @Query("select r from Role r  where r.name = 'ROLE_USER'")
    Page<Role> findUserRoles(Pageable pageable);
}
