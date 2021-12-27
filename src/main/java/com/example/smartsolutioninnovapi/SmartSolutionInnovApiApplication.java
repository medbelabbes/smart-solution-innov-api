package com.example.smartsolutioninnovapi;

import com.example.smartsolutioninnovapi.domain.Role;
import com.example.smartsolutioninnovapi.domain.User;
import com.example.smartsolutioninnovapi.domain.enumeration.UserStatus;
import com.example.smartsolutioninnovapi.services.RoleService;
import com.example.smartsolutioninnovapi.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.Date;

@SpringBootApplication
public class SmartSolutionInnovApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(SmartSolutionInnovApiApplication.class, args);
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    CommandLineRunner run(UserService userService, RoleService roleService) {
        return args -> {
            roleService.saveRole(new Role(null, "ROLE_USER"));
            roleService.saveRole(new Role(null, "ROLE_MANAGER"));
            roleService.saveRole(new Role(null, "ROLE_ADMIN"));
            roleService.saveRole(new Role(null, "ROLE_SUPER_ADMIN"));

            userService.saveUser(new User(null,
                    "Mohamed Abdelillah",
                    "medbelabbes",
                    "med.abdelillah.belabbes@gmail.com",
                    UserStatus.PENDING,
                    "123456",
                    new ArrayList<>(),
                    null,
                    new Date(),
                    null,
                    null
            ), null);
            userService.saveUser(new User(null, "Djamel Eddine", "djamel",
                    "djamel@gmail.com",
                    UserStatus.PENDING,
                    "123456", new ArrayList<>(),
                    null,
                    new Date(),
                    null,
                    null), null);
            userService.saveUser(new User(null, "Abdelhamid Kamel", "hamid",
                    "hamid@gmail.com",
                    UserStatus.PENDING, "123456", new ArrayList<>(),
                    null,
                    new Date(),
                    null,
                    null), null);
            userService.saveUser(new User(null, "Salim", "salim",
                    "salim@gmail.com",
                    UserStatus.PENDING, "123456", new ArrayList<>(),
                    null,
                    new Date(),
                    null,
                    null), null);

            roleService.addRoleToUser("medbelabbes", "ROLE_SUPER_ADMIN");
            roleService.addRoleToUser("djamel", "ROLE_ADMIN");
            roleService.addRoleToUser("hamid", "ROLE_USER");
            roleService.addRoleToUser("salim", "ROLE_USER");
        };
    }


}
