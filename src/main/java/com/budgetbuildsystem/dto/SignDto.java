package com.budgetbuildsystem.dto;

import com.budgetbuildsystem.model.enums.AccountType;
import lombok.Data;

@Data
public class SignDto {
    //General
    private String username;
    private String password;
    private String email;
    private String phoneNumber;
    private AccountType userType;

    //fields for citizen
    private String firstName;
    private String lastName;
    private String address;

    //fields for supplier
    private String supplierName;
    private String companyName;
    private int tinNumber;
    private String contactDetails;
    private String licenseNumber;

    //fields for contractor
    private String  profilePic;
}
