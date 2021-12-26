package com.example.smartsolutioninnovapi.api;

import com.example.smartsolutioninnovapi.domain.Role;
import com.example.smartsolutioninnovapi.domain.User;
import com.example.smartsolutioninnovapi.services.RoleService;
import com.example.smartsolutioninnovapi.services.UserService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class RoleController {
    private final RoleService roleService;

    @PostMapping("/role/save")
    public ResponseEntity<Role> saveUser(@RequestBody Role role) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("api/role/save").toUriString());
        return ResponseEntity.created(uri).body(roleService.saveRole(role));
    }

    @PostMapping("/role/addToUser")
    public ResponseEntity<?> addToUser(@RequestBody RoleToUserForm form) {
        roleService.addRoleToUser(form.getUsername(), form.getRoleName());
        return ResponseEntity.ok().build();
    }
}


@Data
class RoleToUserForm {
    private String username;
    private String roleName;
}
