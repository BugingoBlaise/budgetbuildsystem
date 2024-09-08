package com.budgetbuildsystem.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Data
@Entity
@Table(name = "contractors")
public class Local_Contractor {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String companyName;
    private String email;
    private String phoneNumber;
    public String username;
    private String password;
    private String contactDetails;
    private String licenseNumber;
    private String address;
    private Double averageRating;
    @Lob
    private byte[] profilePic;
    @OneToMany(mappedBy = "contractor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ContractorRecommendation> reviews;
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne
    private RHA_Administrator administrators;
}
