package com.budgetbuildsystem.dto;

import com.budgetbuildsystem.model.Supplier;
import lombok.Data;

@Data

public class MaterialDto {
    private String materialName;
    private String supplierDetails;
    private float price;
    private String imagePath;
    private int quantity;

    private Supplier supplier;


}
