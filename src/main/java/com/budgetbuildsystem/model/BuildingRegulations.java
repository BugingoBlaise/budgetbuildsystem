package com.budgetbuildsystem.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor
@Data
@Entity
@Table(name = "regulations")
public class BuildingRegulations {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String regulationTitle;
    private String regulationDetails;
    @Lob
    private byte[] regulationImage;

}
