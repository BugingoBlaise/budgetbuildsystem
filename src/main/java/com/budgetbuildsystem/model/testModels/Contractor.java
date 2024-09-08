package com.budgetbuildsystem.model.testModels;

import jakarta.persistence.Entity;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Contractor extends Account {
    private String licenceNumber;
    private String companyName;
}
