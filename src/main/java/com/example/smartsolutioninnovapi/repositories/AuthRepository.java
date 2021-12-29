package com.example.smartsolutioninnovapi.repositories;

import com.example.smartsolutioninnovapi.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthRepository extends JpaRepository<User, Long> {

    @Query("SELECT u FROM User u WHERE lower(u.username) = :username AND u.password = :password")
    User findByUsernameAndPassword(@Param("username") String username, @Param("password") String password);

    User findByUsername(String username);
}
