package com.example.smartsolutioninnovapi.api;


import com.example.smartsolutioninnovapi.domain.User;
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
        System.out.println("role is " + role);
        return ResponseEntity.ok().body(adminService.getAdmins(query, role, page, size, sortBy));
    }

    @PostMapping("/admin/save")
    public ResponseEntity<Response> saveAdmin(@RequestBody User admin, HttpServletRequest request) {
        User currentUser = tokenUtils.getCurrentUser(request);
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("api/admin/save").toUriString());
        return ResponseEntity.created(uri).body(adminService.saveAdmin(admin, currentUser));
    }


}
