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
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private AuthenticationService authService;
    @Autowired
    private IUserRepository userRepository;


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
            System.out.println("Authentication successful for user: " + loginRequest.getUsername());
            return ResponseEntity.ok(authService.authenticate(loginRequest));
        } catch (BadCredentialsException e) {
            System.out.println("Authentication failed - Bad credentials for username: " + loginRequest.getUsername());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new AuthResponse(null, null, null, "Invalid username or password"));
        } catch (UsernameNotFoundException e) {
            System.out.println("Authentication failed - Username not found: " + loginRequest.getUsername());
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new AuthResponse(null, null, null, "User not found"));
        } catch (Exception ex) {
            System.out.println("Authentication failed with unexpected error: " + ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new AuthResponse(null, null, null, "An error occurred during authentication"));
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
