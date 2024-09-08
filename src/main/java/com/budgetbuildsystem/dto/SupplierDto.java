package com.budgetbuildsystem.dto;

import com.budgetbuildsystem.model.MaterialProcurement;
import com.budgetbuildsystem.model.RHA_Administrator;
import com.budgetbuildsystem.model.User;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class SupplierDto {
    private UUID id;
    private String supplierName;
    private String contactDetails;
    private List<MaterialProcurement> materials;
    private User user;
    private RHA_Administrator administrator;
    private String userType;
    private String password;
}
