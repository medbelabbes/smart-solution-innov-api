package com.example.smartsolutioninnovapi.utils.bodiesForms;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleToUserForm {
    private String username;
    private String roleName;
}
