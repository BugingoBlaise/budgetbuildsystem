package com.budgetbuildsystem.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor
@Data
@Entity
@Table(name = "materials")
public class Materials {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String materialName;
    private String supplierDetails;
    private float price;
    @ManyToOne
    @JoinColumn(name = "supplier_id")
    private Supplier supplier;

}
