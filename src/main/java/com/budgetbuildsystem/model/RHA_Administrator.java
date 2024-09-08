package com.budgetbuildsystem.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.UUID;
@RequiredArgsConstructor
@Data
@Entity
@Table(name = "admin")
public class RHA_Administrator {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String username;
    private String password;
    @OneToMany(mappedBy = "administrators")
    private List<Local_Contractor> contractor;
    @OneToMany(mappedBy = "administrator")
    private List<Supplier> suppliers;
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
}
