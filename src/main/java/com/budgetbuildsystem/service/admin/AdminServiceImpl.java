package com.budgetbuildsystem.service.admin;

import com.budgetbuildsystem.model.Administrator;
import com.budgetbuildsystem.repository.IAdminRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j

public class AdminServiceImpl implements IAdminService{
    @Autowired
    IAdminRepository iAdminRepository;

    private final IAdminRepository adminRepository;

    @Override
    @Transactional
    public Administrator saveAdmin(Administrator administrator) {
        log.info("Saving new administrator: {}", administrator.getUsername());
        return adminRepository.save(administrator);
    }
/*
    @Override
    @Transactional(readOnly = true)
    public Page<Administrator> getAllAdmins(Pageable pageable) {
        log.info("Fetching page {} of administrators", pageable.getPageNumber());
        return adminRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Administrator getAdminById(Long id) {
        log.info("Fetching administrator with ID: {}", id);
        return adminRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Administrator not found with id: " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public Administrator getAdminByUsername(String username) {
        log.info("Fetching administrator with username: {}", username);
        return adminRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("Administrator not found with username: " + username));
    }

    @Override
    @Transactional
    public Administrator updateAdmin(Long id, Administrator updatedAdmin) {
        log.info("Updating administrator with ID: {}", id);

        Administrator existingAdmin = getAdminById(id);

        // Update fields if they are not null
        if (updatedAdmin.getUsername() != null) {
            existingAdmin.setUsername(updatedAdmin.getUsername());
        }
        if (updatedAdmin.getPassword() != null) {
            existingAdmin.setPassword(updatedAdmin.getPassword());
        }
        // Add any other fields that need to be updated

        return adminRepository.save(existingAdmin);
    }

    @Override
    @Transactional
    public void deleteAdmin(Long id) {
        log.info("Deleting administrator with ID: {}", id);
        if (!adminRepository.existsById(id)) {
            throw new ResourceNotFoundException("Administrator not found with id: " + id);
        }
        adminRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByUsername(String username) {
        return adminRepository.existsByUsername(username);
    }



    private AdminResponseDto mapToAdminResponseDto(Administrator admin) {
        return AdminResponseDto.builder()
                .id(admin.getId())
                .username(admin.getUsername())
                .roles(admin.getUser().getRoles())
                // Add any other fields you want to include in the response
                .build();
    }*/
  }
