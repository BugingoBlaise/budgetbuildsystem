package com.budgetbuildsystem.controller;

import com.budgetbuildsystem.dto.SignupRequest;
import com.budgetbuildsystem.service.signup.ISignUpService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/auth/signup")
public class SignupController {
    private final ISignUpService signupService;

    public SignupController(ISignUpService signupService) {
        this.signupService = signupService;
    }

    @PostMapping
    public ResponseEntity<String> signupUser(@RequestBody SignupRequest signupRequest
//                                             @RequestBody SupplierDto supplierDto
                                             ) {
        try {
            switch (signupRequest.getUserType().toUpperCase()) {
                case "CITIZEN":
                    signupService.signupCitizen(signupRequest);
                    break;
                case "CONTRACTOR":
                    signupService.signupContractor(signupRequest);
                    break;

                    case "SUPPLIER":
                    signupService.signupSupplier(signupRequest);
                    break;

                case "ADMINISTRATOR":
                    signupService.signupAdministrator(signupRequest);
                    log.info("------ADMIN CONTROLLER REACHED-------");
                    break;

                default:

                return new ResponseEntity<>("Invalid user type", HttpStatus.BAD_REQUEST);
            }
            return  ResponseEntity.status(HttpStatus.CREATED).body("User created successfully");
        } catch (Exception e) {
            return new ResponseEntity<>("An error occurred during registration: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
