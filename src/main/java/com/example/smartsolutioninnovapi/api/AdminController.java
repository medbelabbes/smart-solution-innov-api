package com.example.smartsolutioninnovapi.api;


import com.example.smartsolutioninnovapi.domain.User;
import com.example.smartsolutioninnovapi.dto.UserDto;
import com.example.smartsolutioninnovapi.services.AdminService;
import com.example.smartsolutioninnovapi.utils.TokenUtils;
import com.example.smartsolutioninnovapi.utils.responses.CollectionResponse;
import com.example.smartsolutioninnovapi.utils.responses.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AdminController {
    private final AdminService adminService;
    private final TokenUtils tokenUtils;


    @GetMapping("/admins")
    public ResponseEntity<CollectionResponse> getAdmins(
            @RequestParam String query,
            @RequestParam String role,
            @RequestParam Optional<Integer> page,
            @RequestParam Optional<Integer> size,
            @RequestParam Optional<String> sortBy
    ) {
        return ResponseEntity.ok().body(adminService.getAdmins(query, role, page, size, sortBy));
    }

    @GetMapping("/admins/get/{id}")
    public ResponseEntity<Response> getById(
            @PathVariable("id") Long id
    ) {
        return ResponseEntity.ok().body(adminService.getAdminById(id));
    }

    @PostMapping("/admin/save")
    public ResponseEntity<Response> saveAdmin(@RequestBody User admin, HttpServletRequest request) {
        User currentUser = tokenUtils.getCurrentUser(request);
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("api/admin/save").toUriString());
        return ResponseEntity.created(uri).body(adminService.saveAdmin(admin, currentUser));
    }

    @PatchMapping("/admin/edit")
    public ResponseEntity<Response> editAdmin(@RequestBody UserDto admin, HttpServletRequest request) {
        User currentUser = tokenUtils.getCurrentUser(request);
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("api/admin/edit").toUriString());
        return ResponseEntity.created(uri).body(adminService.updateAdmin(admin, currentUser));
    }

    @DeleteMapping("/admin/delete/{id}")
    public ResponseEntity<Response> deleteAdmin(@PathVariable("id") Long id, HttpServletRequest request) {
        User currentUser = tokenUtils.getCurrentUser(request);
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("api/admin/delete/" + id).toUriString());
        return ResponseEntity.created(uri).body(adminService.deleteAdmin(id, currentUser));
    }


}
