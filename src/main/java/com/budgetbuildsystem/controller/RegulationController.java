package com.budgetbuildsystem.controller;

import com.budgetbuildsystem.model.Regulations;
import com.budgetbuildsystem.service.fileService.FileService;
import com.budgetbuildsystem.service.regulations.IRegulationsService;
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
@RequestMapping("/api/regulations")
public class RegulationController {
    @Autowired
    private IRegulationsService buildingRegulationsService;
    @Autowired
    private FileService fileService;


    @GetMapping
    public List<Regulations> getAllRegulations() {
        return buildingRegulationsService.getAllRegulations();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Regulations> getRegulationById(@PathVariable UUID id) {
        Optional<Regulations> regulation = buildingRegulationsService.getRegulationById(id);
        return regulation.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/saveReg")
    public ResponseEntity<?> saveRegulation(
            @RequestParam("regulationTitle") String regulationTitle,
            @RequestParam("regulationDetails") String regulationDetails,
            @RequestParam(value = "regulationImage", required = true) MultipartFile regulationImage) {
        try {
            Regulations newRegulation = new Regulations();
            newRegulation.setRegulationTitle(regulationTitle);
            newRegulation.setRegulationDetails(regulationDetails);

            if (regulationImage != null && !regulationImage.isEmpty()) {
                String fileName = fileService.storeFile(regulationImage);
                newRegulation.setRegulationImagePath(fileName);
            }

            Regulations savedRegulation = buildingRegulationsService.saveRegulation(newRegulation);
            return ResponseEntity.ok(savedRegulation);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Could not upload file: " + e.getMessage());
        }
    }

    @PutMapping("/updateReg/{id}")
    public ResponseEntity<?> updateRegulation(
            @PathVariable UUID id,
            @RequestParam("regulationTitle") String regulationTitle,
            @RequestParam("regulationDetails") String regulationDetails,
            @RequestParam(value = "regulationImage", required = false) MultipartFile regulationImage) {
        try {
            Optional<Regulations> existingRegulation = buildingRegulationsService.getRegulationById(id);

            if (existingRegulation.isPresent()) {
                Regulations regulationToUpdate = existingRegulation.get();
                regulationToUpdate.setRegulationTitle(regulationTitle);
                regulationToUpdate.setRegulationDetails(regulationDetails);

                if (regulationImage != null && !regulationImage.isEmpty()) {
                    // Delete old file if it exists
                    if (regulationToUpdate.getRegulationImagePath() != null) {
                        fileService.deleteFile(regulationToUpdate.getRegulationImagePath());
                    }

                    // Store new file
                    String fileName = fileService.storeFile(regulationImage);
                    regulationToUpdate.setRegulationImagePath(fileName);
                }
                Regulations updatedRegulation = buildingRegulationsService.saveRegulation(regulationToUpdate);
                return ResponseEntity.ok(updatedRegulation);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Could not upload file: " + e.getMessage());
        }
    }

    @DeleteMapping("/deleteReg/{id}")
    public ResponseEntity<?> deleteRegulation(@PathVariable UUID id) {
        try {
            Optional<Regulations> regulation = buildingRegulationsService.getRegulationById(id);
            if (regulation.isPresent()) {
                // Delete the associated file if it exists
                if (regulation.get().getRegulationImagePath() != null) {
                    fileService.deleteFile(regulation.get().getRegulationImagePath());
                }
                buildingRegulationsService.deleteRegulationById(id);
                return ResponseEntity.ok().build();
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Could not delete file: " + e.getMessage());
        }
    }
    @GetMapping("/image/{imageName}")
    public ResponseEntity<Resource> getImage(@PathVariable String imageName) throws IOException {
        Resource file = fileService.loadFileAsResource(imageName);
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG) // or the appropriate media type
                .body(file);
    }
}