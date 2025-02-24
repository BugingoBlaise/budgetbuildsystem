package com.budgetbuildsystem.controller;


import com.budgetbuildsystem.dto.AuthResponse;
import com.budgetbuildsystem.dto.LoginRequest;
import com.budgetbuildsystem.dto.SignDto;
import com.budgetbuildsystem.model.User;
import com.budgetbuildsystem.repository.IUserRepository;
import com.budgetbuildsystem.service.authentication.AuthenticationService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private AuthenticationService authService;
    @Autowired
    private IUserRepository userRepository;

    /*@PostMapping("/signup")
    public ResponseEntity<AuthResponse> signUp(@RequestBody SignDto signDto) {
        try {
            return ResponseEntity.ok(authService.register(signDto));
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }*/


    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@RequestBody SignDto signDto) {
        try {
            AuthResponse response = authService.register(signDto);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException ex) {
            // Handle specific exceptions (e.g., duplicate user)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        } catch (Exception ex) {
            // Handle generic exceptions
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred during registration.");
        }
    }


    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest loginRequest) {
        try {
            return ResponseEntity.ok(authService.authenticate(loginRequest));
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request) {
        return ResponseEntity.ok("Logged out successfully");
    }

    @GetMapping("/me")
    public ResponseEntity<?> getCurrentUser() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication instanceof AnonymousAuthenticationToken) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body("Authentication required to add materials");
            }
            String currentUserName = authentication.getName();
            User user = userRepository.findByUsername(currentUserName)
                    .orElseThrow(() -> new IllegalStateException("User not found"));
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error fetching user details");
        }
    }
}
