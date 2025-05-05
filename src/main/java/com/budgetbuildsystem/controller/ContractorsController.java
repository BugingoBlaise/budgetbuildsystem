package com.budgetbuildsystem.controller;

import com.budgetbuildsystem.service.contractor.IContractorService;
import com.budgetbuildsystem.service.user.IUserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;


@RestController
@RequestMapping("/api/contractors")
public class ContractorsController {
    private final IUserService userService;
    private final IContractorService contractorsService;
    public ContractorsController(IContractorService contractorsService,IUserService userService) {
        this.contractorsService = contractorsService;
        this.userService=userService;
    }

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

    @GetMapping("/findContractor/{id}")
    public ResponseEntity<?>getContractor(@PathVariable("id") UUID id){
        try {
            return ResponseEntity.ok(userService.findById(id));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
