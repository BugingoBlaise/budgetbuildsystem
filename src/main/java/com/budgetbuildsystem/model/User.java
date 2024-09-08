package com.budgetbuildsystem.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.Set;
import java.util.UUID;


@RequiredArgsConstructor
@Data
@Entity
@Table(name = "users")
public class User  {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String username;
    private String password;
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<String> roles;
    // Relationships to specific user types
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Citizen citizen;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private RHA_Administrator administrator;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)

    private Local_Contractor contractor;
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Supplier supplier;

}
