package com.budgetbuildsystem.controller;

import com.budgetbuildsystem.model.Loan;
import com.budgetbuildsystem.service.fileService.FileService;
import com.budgetbuildsystem.service.loan.ILoanService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/loans")
@Slf4j
public class LoanController {
    @Autowired
    private ILoanService loanService;
    @Autowired
    FileService fileService;

    @GetMapping
    public List<Loan> loanList() {
        return loanService.getAllLoans();
    }

    @PostMapping("/saveLoan")
    public ResponseEntity<?> saveLoan(@RequestParam("LoanName") String LoanName, @RequestParam("description") String description, @RequestParam("interestRate") double interestRate, @RequestParam("link") String link, @RequestParam(value = "loanImage", required = false) MultipartFile loanImage) {
        try {
            Loan newloan = new Loan();
            newloan.setDescription(description);
            newloan.setName(LoanName);
            newloan.setInterestRate(interestRate);
            newloan.setLink(link);
            if (loanImage != null && !loanImage.isEmpty()) {
                String filename = fileService.storeFile(loanImage);
                newloan.setImagePath(filename);
            }
            Loan savedLoan = loanService.saveLoan(newloan);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedLoan);
        } catch (IOException e) {
            log.error("Error saving loan: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PutMapping("/updateLoan/{id}")
    public ResponseEntity<?> updateLoan(@PathVariable UUID id, @RequestParam("name") String name, @RequestParam("description") String description, @RequestParam("interestRate") double interestRate, @RequestParam("link") String link, @RequestParam(value = "imagePath", required = false) MultipartFile imagePath) {
        try {
            Optional<Loan> existingLoan = loanService.getLoanById(id);
            if (existingLoan.isPresent()) {
                Loan loanToUpdate = existingLoan.get();
                loanToUpdate.setName(name);
                loanToUpdate.setDescription(description);
                loanToUpdate.setInterestRate(interestRate);
                loanToUpdate.setLink(link);
                if (imagePath != null && !imagePath.isEmpty()) {
                    // Delete old file if it exists
                    if (loanToUpdate.getImagePath() != null) {
                        fileService.deleteFile(loanToUpdate.getImagePath());
                    }
                    // Store new file
                    String fileName = fileService.storeFile(imagePath);
                    loanToUpdate.setImagePath(fileName);
                }
                Loan updatedLoan = loanService.saveLoan(loanToUpdate);
                return ResponseEntity.ok(updatedLoan);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        } catch (IOException e) {
            log.error("Error saving loan: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Could not upload file: " + e.getMessage());
        }
    }

    @DeleteMapping("/deleteLoan/{id}")
    public ResponseEntity<?> deleteLoan(@PathVariable UUID id) {
        try {
            Optional<Loan> existingLoan = loanService.getLoanById(id);
            if (existingLoan.isPresent()) {
                // Delete the associated file if it exists
                if (existingLoan.get().getImagePath() != null) {
                    fileService.deleteFile(existingLoan.get().getImagePath());
                }
                loanService.deleteLoanById(id);
                return ResponseEntity.ok().build();
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Could not delete Entity: " + e.getMessage());
        }
    }

    @GetMapping("/image/{imageName}")
    public ResponseEntity<Resource> getImage(@PathVariable String imageName) throws IOException {
        Resource file = fileService.loadFileAsResource(imageName);
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG) // or the appropriate media type
                .body(file);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Loan>> searchLoan(@RequestParam("loanName") String loanName) {
        log.info("Searching for loans with name: {}", loanName);
        List<Loan> searchedLoan = loanService.searchLoanByName(loanName);
        return ResponseEntity.ok(searchedLoan);
    }
}
