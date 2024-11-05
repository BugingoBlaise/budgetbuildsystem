package com.budgetbuildsystem.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.Date;
import java.util.UUID;

@RequiredArgsConstructor
@Data
@Entity
@Table(name = "regulations")
public class Regulations {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String regulationTitle;
    @Column(length = 5000)
    private String regulationDetails;
    private String regulationImagePath;
    private Date date;

}
