package com.budgetbuildsystem.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@RequiredArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "materials")
public class Materials {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String materialName;
    private String materialDetails;
    private float price;
    private int quantity;
    private String imagePath;
    private Date postedDate;
    @ManyToOne
    @JoinColumn(name = "supplier_id")
    private Supplier supplier;

}
