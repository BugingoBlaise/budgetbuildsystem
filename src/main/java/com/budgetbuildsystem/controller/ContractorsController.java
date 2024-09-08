package com.budgetbuildsystem.controller;

import com.budgetbuildsystem.service.contractor.IContractorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/contractors")
public class ContractorsController {
    @Autowired
    private IContractorService contractorsService;

    @GetMapping("/all")
    public ResponseEntity<?> getAllContractors() {
        try {
            return ResponseEntity.status(HttpStatus.FOUND).body(contractorsService.findAllContractors());
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }
}
