package com.budgetbuildsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@AllArgsConstructor
@Getter
@Setter
public class AuthResponse {
    private UUID id;
    private String username;
    private String role;
    private String token;
    public AuthResponse(Object o, Object o1, Object o2, Object o3, String invalidUsernameOrPassword) {
    }
}
