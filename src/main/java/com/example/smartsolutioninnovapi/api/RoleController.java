package com.example.smartsolutioninnovapi.api;

import com.example.smartsolutioninnovapi.domain.Role;
import com.example.smartsolutioninnovapi.services.RoleService;
import com.example.smartsolutioninnovapi.utils.bodiesForms.RoleToUserForm;
import com.example.smartsolutioninnovapi.utils.responses.CollectionResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
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

    @GetMapping("/roles/admin-roles")
    public ResponseEntity<CollectionResponse> getRoles(
            @RequestParam Optional<Integer> page,
            @RequestParam Optional<Integer> size,
            @RequestParam Optional<String> sortBy
    ) {
        return ResponseEntity.ok().body(roleService.getAdminRoles(page, size, sortBy));
    }

}

