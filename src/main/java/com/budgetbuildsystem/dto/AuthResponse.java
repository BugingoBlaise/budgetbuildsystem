package com.budgetbuildsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class AuthResponse {
    private String username;
    private String role;
    private String token;
}
