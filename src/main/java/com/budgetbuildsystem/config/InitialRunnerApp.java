package com.budgetbuildsystem.config;

import com.budgetbuildsystem.dto.SignDto;
import com.budgetbuildsystem.model.Administrator;
import com.budgetbuildsystem.model.User;
import com.budgetbuildsystem.model.enums.AccountType;
import com.budgetbuildsystem.repository.IUserRepository;
import com.budgetbuildsystem.service.admin.IAdminService;
import com.budgetbuildsystem.service.user.IUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Slf4j
@Component
@RequiredArgsConstructor
public class InitialRunnerApp implements CommandLineRunner {
    private final IAdminService adminService;
    private final PasswordEncoder passwordEncoder;
    private final IUserService userService;
    private final IUserRepository repository;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        Page<Administrator> adminPage = userService.getAllAdminUsers(PageRequest.of(0, 1));

        if (adminPage.getTotalElements() == 0) {
            log.info("-------Initial setup running----------------");

            SignDto request = new SignDto();
            request.setUsername("super.admin");
            request.setEmail("admin@gmail.com");
            request.setPassword("open123"); // Store raw password in DTO
            request.setUserType(AccountType.ADMIN);

            // Create and setup User entity
            User user = new User();
            user.setUsername(request.getUsername());
            user.setPassword(passwordEncoder.encode(request.getPassword())); // Encode password for User

            Set<String> roles = new HashSet<>();
            roles.add("ROLE_" + request.getUserType().toString().toUpperCase());
            user.setRoles(roles);

            // Create and setup Administrator entity
            Administrator admin = new Administrator();
            admin.setUsername(request.getUsername());
            admin.setPassword(passwordEncoder.encode(request.getPassword())); // Encode password for Admin
            admin.setUser(user); // Set the user reference before saving

            // Save the admin which will cascade to save the user
            adminService.saveAdmin(admin);

            log.info("Initial admin user created successfully");
            log.info("Username: {}", request.getUsername());
            log.info("Password: {}", request.getPassword());
        } else {
            log.info("Admin user already exists. Skipping creation.");
        }
    }

}
