package com.budgetbuildsystem.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Getter
@Setter
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
    @OneToMany(mappedBy = "supplier")
    @JsonManagedReference // This manages the back reference
    private List<Materials> materials;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

}
