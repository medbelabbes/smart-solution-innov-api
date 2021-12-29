package com.example.smartsolutioninnovapi.repositories;

import com.example.smartsolutioninnovapi.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);

    User findByEmail(String email);

    @Query("select DISTINCT u from User u left join u.roles r where (lower(u.name) like CONCAT('%',:query,'%') or lower(u.username) like CONCAT('%',:query,'%') or lower(u.email) like CONCAT('%',:query,'%'))  and r.name = 'ROLE_USER'")
    Page<User> findAll(@Param("query") String query, Pageable pageable);
}
