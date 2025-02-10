package com.budgetbuildsystem.controller;

import com.budgetbuildsystem.dto.AdminDashData;
import com.budgetbuildsystem.service.citizen.ICitizenService;
import com.budgetbuildsystem.service.contractor.IContractorService;
import com.budgetbuildsystem.service.loan.ILoanService;
import com.budgetbuildsystem.service.materials.IMaterialService;
import com.budgetbuildsystem.service.regulations.IRegulationsService;
import com.budgetbuildsystem.service.supplier.ISupplierService;
import com.budgetbuildsystem.service.user.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/dashboard")
public class DashboardController {
    private final IUserService userService;
    private final ICitizenService citizenService;
    private final ILoanService loanService;
    private final IMaterialService materialService;
    private final IContractorService contractorService;
    private final ISupplierService supplierService;
    private final IRegulationsService regulationService;

    @GetMapping
    public AdminDashData getDashboardData() {
        long totalUsers = userService.getTotalUsers();
        long totalCitizens = citizenService.getTotalCitizens();
        long totalContractors = contractorService.countContractors();
        long totalSuppliers = supplierService.getTotalSuppliers();
        long totalMaterials = materialService.getTotalMaterials();
        long totalLoans = loanService.getTotalLoans();
        long totalRegulations = regulationService.getTotalRegulations();
        double averageContractorRating = contractorService.getAverageContractorRating();

        return new AdminDashData(
                totalUsers,
                totalCitizens,
                totalContractors,
                totalSuppliers,
                totalMaterials,
                totalLoans,
                totalRegulations,
                averageContractorRating

        );
    }
}




