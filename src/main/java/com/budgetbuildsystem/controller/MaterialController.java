package com.budgetbuildsystem.controller;

import com.budgetbuildsystem.model.Materials;
import com.budgetbuildsystem.model.Supplier;
import com.budgetbuildsystem.model.User;
import com.budgetbuildsystem.repository.ISupplierRepository;
import com.budgetbuildsystem.repository.IUserRepository;
import com.budgetbuildsystem.service.fileService.FileService;
import com.budgetbuildsystem.service.materials.IMaterialService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@Slf4j
@RequestMapping("/api/materials")
public class MaterialController {
    @Autowired
    private FileService fileService;
    @Autowired
    private IMaterialService materialService;
    @Autowired
    private ISupplierRepository supplierRepository;
    @Autowired
    private IUserRepository userRepository;


    @GetMapping("/all")
    public List<Materials>findAllMaterials(){
        return
            materialService.findAllMaterials();
    }
    @GetMapping
    public List<Materials> getAllMaterials() {
        return materialService.findAllMaterials();
    }

    @PostMapping("/saveMaterial")
    public ResponseEntity<?> addMaterial(
            @RequestParam("materialName") String materialName,
            @RequestParam("materialDetails") String materialDetails,
            @RequestParam("price") float price,
            @RequestParam(value = "imagePath", required = true) MultipartFile imagePath,
            Authentication authentication) {

        try {
            // Get current user from JWT authentication
            User user = userRepository.findByUsername(authentication.getName())
                    .orElseThrow(() -> new IllegalStateException("User not found"));

            // Check if user is a supplier
            if (!user.getRoles().contains("SUPPLIER")) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body("Only suppliers can add materials");
            }

            // Get supplier profile
            Supplier currentSupplier = supplierRepository.findByUser(user)
                    .orElseThrow(() -> new IllegalStateException("Supplier profile not found"));

            Materials newMaterial = new Materials();
            newMaterial.setMaterialName(materialName);
            newMaterial.setMaterialDetails(materialDetails);
            newMaterial.setPrice(price);
            newMaterial.setPostedDate(new Date());
            newMaterial.setSupplier(currentSupplier);

            // Handle image upload
            if (imagePath != null && !imagePath.isEmpty()) {
                String fileName = fileService.storeFile(imagePath);
                newMaterial.setImagePath(fileName);
            }

            Materials savedMaterial = materialService.addMaterial(newMaterial);
            return ResponseEntity.ok(savedMaterial);

        } catch (IOException e) {
            log.error("Failed to upload file", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Could not upload file: " + e.getMessage());
        } catch (Exception e) {
            log.error("Failed to save material", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error saving material: " + e.getMessage());
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteMaterial(@PathVariable UUID id) {
        try {
            Optional<Materials> materialsOptional = materialService.findMaterialById(id);

            if (materialsOptional.isPresent()) {
                // Delete the associated file if it exists
                if (materialsOptional.get().getImagePath() != null) {
                    fileService.deleteFile(materialsOptional.get().getImagePath());
                }
                materialService.deleteMaterial(id);

                return ResponseEntity.ok().build();
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Could not delete file: " + e.getMessage());
        }
    }


    @PutMapping("/updateMaterial/{id}")
    public ResponseEntity<?> updateMaterial(
            @PathVariable UUID id,
            @RequestParam("materialName") String materialName,
            @RequestParam("materialDetails") String materialDetails,
            @RequestParam("price") float price,
            @RequestParam(value = "imagePath", required = false) MultipartFile imagePath
    ) {
        try {
            Optional<Materials> existingMaterial = materialService.findMaterialById(id);

            if (existingMaterial.isPresent()) {
                Materials materialToUpdate = existingMaterial.get();
                materialToUpdate.setMaterialName(materialName);
                materialToUpdate.setMaterialDetails(materialDetails);

                materialToUpdate.setPrice(price);
//                Supplier supplier = (Supplier) session.getAttribute("currentUser");

//                if (supplier != null) materialToUpdate.setSupplier(supplier);

                if (imagePath != null && !imagePath.isEmpty()) {
                    // Delete old file if it exists
                    if (materialToUpdate.getImagePath() != null) {
                        fileService.deleteFile(materialToUpdate.getImagePath());
                    }

                    // Store new file
                    String fileName = fileService.storeFile(imagePath);
                    materialToUpdate.setImagePath(fileName);
                }

                Materials updatedMaterial = materialService.updateMaterial(materialToUpdate);
                return ResponseEntity.ok(updatedMaterial);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Could not upload file: " + e.getMessage());
        } catch (Exception e) {
            log.error("Failed to update material", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error updating material: " + e.getMessage());
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
