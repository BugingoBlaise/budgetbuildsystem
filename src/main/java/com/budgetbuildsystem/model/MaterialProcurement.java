package com.budgetbuildsystem.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor
@Data
@Entity
@Table(name = "materials")
public class MaterialProcurement {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String materialName;
    private String supplierDetails;
    private float price;

    // Relationships
    @ManyToOne
    @JoinColumn(name = "citizen_id")
    private Citizen citizen;

    @ManyToOne
    @JoinColumn(name = "supplier_id")
    private Supplier supplier;

}
