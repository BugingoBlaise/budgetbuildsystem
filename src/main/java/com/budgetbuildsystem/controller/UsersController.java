package com.budgetbuildsystem.controller;

import com.budgetbuildsystem.model.Supplier;
import com.budgetbuildsystem.model.User;
import com.budgetbuildsystem.repository.ISupplierRepository;
import com.budgetbuildsystem.repository.IUserRepository;
import com.budgetbuildsystem.service.user.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/users")
@Slf4j
public class UsersController {
    @Autowired
    private IUserService iUserService;
    @Autowired
    private IUserRepository userRepository;
    @Autowired
    private ISupplierRepository supplierRepository;

    @GetMapping
    public ResponseEntity<?> getAllUsers() {
        try {
            return ResponseEntity.ok(iUserService.findAll());
        } catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{Id}")
    public ResponseEntity<?> findUserById(@PathVariable UUID Id) {
        try {
            return ResponseEntity.ok(iUserService.findById(Id));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/deleteAccount/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable UUID id) {
        try {
            log.info("Deleting user with id: {}", id);
            iUserService.deleteUser(id);
            return ResponseEntity.status(HttpStatus.OK).body("DELETED SUCCESSFULLY");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Could not delete Entity: " + e.getMessage());
        }
    }

    @GetMapping("/loadContractors")
    public ResponseEntity<?> loadContractors() {
        try {
            return ResponseEntity.ok(iUserService.getContractors());
        } catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/loadSuppliers")
    public ResponseEntity<?> getSuppliers() {
        try {
            return ResponseEntity.ok(iUserService.loadSuppliers());
        } catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @GetMapping("/me")
    public ResponseEntity<?> getCurrentUser(Authentication authentication) {
        if (authentication == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("No authenticated user");
        }
        try {
            // Find user by username from authentication
            User currentUser = userRepository.findByUsername(authentication.getName())
                    .orElseThrow(() -> new RuntimeException("User not found"));
            // Find associated supplier (if exists)
            Supplier supplier = supplierRepository.findByUser(currentUser)
                    .orElse(null);

            // Create a DTO to return minimal user and supplier information
            Map<String, Object> userDetails = new HashMap<>();
            userDetails.put("username", currentUser.getUsername());
            userDetails.put("roles", currentUser.getRoles());
            if (supplier != null) {
                userDetails.put("supplierId", supplier.getId());
            }
            return ResponseEntity.ok(userDetails);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error fetching user details");
        }
    }
}
