package com.budgetbuildsystem.dto;

import com.budgetbuildsystem.model.Materials;
import com.budgetbuildsystem.model.Administrator;
import com.budgetbuildsystem.model.User;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class SupplierDto {
    private UUID id;
    private String supplierName;
    private String contactDetails;
    private List<Materials> materials;
    private User user;
    private Administrator administrator;
    private String userType;
    private String password;
}
