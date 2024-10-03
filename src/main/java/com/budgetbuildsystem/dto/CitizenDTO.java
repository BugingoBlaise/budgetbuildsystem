package com.budgetbuildsystem.dto;

import com.budgetbuildsystem.model.Citizen;

import java.util.UUID;

public class CitizenDTO {
    private UUID id;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String address;

    public CitizenDTO(Citizen citizen) {
        this.id = citizen.getId();
        this.firstName = citizen.getFirstName();
        this.lastName = citizen.getLastName();
        this.email = citizen.getEmail();
        this.phoneNumber = citizen.getPhoneNumber();
        this.address = citizen.getAddress();
    }
}
