package com.budgetbuildsystem.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@RequiredArgsConstructor
@Data
@Entity
@Table(name = "contractors")
public class Contractor {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String companyName;
    private String email;
    private String phoneNumber;
    public  String username;
    private String password;
    private String contactDetails;
    private String licenseNumber;
    private String address;
    private float averageRating;
    @Lob
    private byte[] profilePic;
    @OneToMany(mappedBy = "contractor", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Recommendation> review=new HashSet<>(0);
    @OneToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;
 }
