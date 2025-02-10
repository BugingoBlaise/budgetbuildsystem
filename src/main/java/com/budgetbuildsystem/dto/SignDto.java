package com.budgetbuildsystem.dto;

import com.budgetbuildsystem.model.enums.AccountType;
import lombok.Data;

@Data
public class SignDto {

    private String username;
    private String password;
    private String email;
    private String phoneNumber;
    private AccountType userType;


    private String firstName;
    private String lastName;
    private String address;


    private String supplierName;
    private String companyName;
    private int tinNumber;
    private String contactDetails;
    private String licenseNumber;


    private String  profilePic;
}
