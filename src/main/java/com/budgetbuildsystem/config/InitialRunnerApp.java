package com.budgetbuildsystem.config;

import com.budgetbuildsystem.dto.SignDto;
import com.budgetbuildsystem.model.Administrator;
import com.budgetbuildsystem.model.enums.AccountType;
import com.budgetbuildsystem.service.admin.IAdminService;
import com.budgetbuildsystem.service.user.IUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class InitialRunnerApp implements CommandLineRunner {

    private final IAdminService adminService;
    private final PasswordEncoder passwordEncoder;
    private final IUserService userService;

    @Override
    public void run(String... args) throws Exception {
        Page<Administrator> adminPage = userService.getAllAdminUsers(PageRequest.of(0, 1));

        if (adminPage.getTotalElements() == 0) {

            System.out.println("-------Initial setup running----------------");

            SignDto request = new SignDto();
            request.setUsername("admin@gmail.com");
            request.setPassword(passwordEncoder.encode("open123"));
            request.setUserType(AccountType.ADMIN);

            userService.signUpUser(request);

            log.info("Initial admin user created.");
            log.info("Email: " + request.getUsername());
            log.info("Password: adminPassword");
        } else {
            log.info("Admin user already exists. Skipping creation.");
        }

    }
}
