package com.example.smartsolutioninnovapi;

import com.example.smartsolutioninnovapi.domain.Role;
import com.example.smartsolutioninnovapi.domain.User;
import com.example.smartsolutioninnovapi.services.RoleService;
import com.example.smartsolutioninnovapi.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;

@SpringBootApplication
public class SmartSolutionInnovApiApplication implements CommandLineRunner {

    @Autowired
    private  UserService userService;

    @Autowired
    private  RoleService roleService;

    public static void main(String[] args) {
        SpringApplication.run(SmartSolutionInnovApiApplication.class, args);
    }


    @Override
    public void run(String... args) throws Exception {
        roleService.saveRole(new Role(null, "ROLE_USER"));
        roleService.saveRole(new Role(null, "ROLE_MANAGER"));
        roleService.saveRole(new Role(null, "ROLE_ADMIN"));
        roleService.saveRole(new Role(null, "ROLE_SUPER_ADMIN"));

        userService.saveUser(new User(null, "Mohamed Abdelillah", "medbelabbes", "123456", new ArrayList<>()));
        userService.saveUser(new User(null, "Djamel Eddine", "djamel", "123456", new ArrayList<>()));
        userService.saveUser(new User(null, "Abdelhamid Kamel", "hamid", "123456", new ArrayList<>()));
        userService.saveUser(new User(null, "Salim", "salim", "123456", new ArrayList<>()));

        roleService.addRoleToUser("medbelabbes", "ROLE_SUPER_ADMIN");
        roleService.addRoleToUser("djamel", "ROLE_ADMIN");
        roleService.addRoleToUser("hamid", "ROLE_USER");
        roleService.addRoleToUser("salim", "ROLE_USER");
    }
}
