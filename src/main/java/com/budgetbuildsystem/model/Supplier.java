package com.budgetbuildsystem.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Data
@Entity
public class Supplier {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String supplierName;
    private String companyName;
    private String address;
    private String email;
    private String phoneNumber;
    private String username;
    private String password;
    private int tinNumber;
    // Relationships
    @OneToMany(mappedBy = "supplier")
    private List<Materials> materials;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;
    @ManyToOne
    private Administrator administrator;
}
