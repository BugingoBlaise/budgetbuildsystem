package com.budgetbuildsystem.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.UUID;
@RequiredArgsConstructor
@Data
@Entity
@Table(name = "loans")
public class LoanAndFinancing {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String loanOptions;
    private float interestRate;

    // Relationships
    @ManyToOne
    @JoinColumn(name = "citizen_id")
    private Citizen citizen;


}
