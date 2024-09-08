package com.budgetbuildsystem.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.UUID;
@RequiredArgsConstructor
@Data
@Entity
public class Citizen {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
//    private String username;
    /*  @Enumerated(EnumType.STRING)
    private EGender gender;*/
    private String address;
    private String password;
    // Relationships
    @OneToMany(mappedBy = "citizen")
    private List<MaterialProcurement> procurements;

    @OneToMany(mappedBy = "citizen")
    private List<LoanAndFinancing> loans;

    @OneToMany(mappedBy = "citizen")
    private List<ContractorRecommendation> recommendations;
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
}
