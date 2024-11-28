package com.budgetbuildsystem.controller;

import com.budgetbuildsystem.service.contractor.IContractorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/contractors")
public class ContractorsController {
    private final IContractorService contractorsService;

    @GetMapping
    public ResponseEntity<?> getAllContractors() {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(contractorsService.findAllContractors());
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @GetMapping("/{contractorId}")
    public ResponseEntity<?> getContractorById(UUID contractorId) {
        try {
            return ResponseEntity.ok(contractorsService.getContractorById(contractorId));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
