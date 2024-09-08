package com.budgetbuildsystem.dto;

import lombok.Data;

@Data
public class SignupRequest {
    private String username;
    private String password;
    private String email;
    private String phoneNumber;
    private String companyName;
    private String department;
    private String licenseNumber;
    private String userType;
    private String supplierDetails;
}
