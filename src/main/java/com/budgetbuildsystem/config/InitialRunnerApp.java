package com.budgetbuildsystem.config;

import com.budgetbuildsystem.model.RHA_Administrator;
import com.budgetbuildsystem.model.User;
import com.budgetbuildsystem.model.enums.ERole;
import com.budgetbuildsystem.service.admin.IAdminService;
import com.budgetbuildsystem.service.user.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Set;

@Slf4j
@Component
public class InitialRunnerApp implements CommandLineRunner {
    @Autowired
    IUserService userService;
    @Autowired
    IAdminService adminService;
    PasswordEncoder passwordEncoder;

    @Autowired
    public InitialRunnerApp(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        Page<RHA_Administrator> adminPage = userService.getAllAdminUsers(PageRequest.of(0, 1));

        if (adminPage.getTotalElements() == 0) {
            System.out.println("-------Initial setup running----------------");
            User user = new User();
//        SignupRequest signupRequest=new SignupRequest();
            user.setUsername("admin@gmail.com");
            user.setPassword(passwordEncoder.encode(passwordEncoder.encode("open123")));

            user.setRoles(Set.of(String.valueOf(ERole.ADMIN)));
            RHA_Administrator administrator = new RHA_Administrator();
            administrator.setUser(user);
            user.setAdministrator(administrator);

            userService.saveUser(user);

            adminService.saveAdmin(administrator);

            log.info("Initial admin user created.");
            log.info("Email: " + user.getUsername());
            log.info("Password: adminPassword");
        } else {
            log.info("Admin user already exists. Skipping creation.");
        }

    }
}
