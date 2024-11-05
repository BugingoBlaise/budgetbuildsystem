package com.budgetbuildsystem.service.user;

import com.budgetbuildsystem.dto.SignDto;
import com.budgetbuildsystem.model.*;
import com.budgetbuildsystem.repository.*;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;

@Service@Slf4j
public class UserServiceImpl implements IUserService {

    @Autowired
    ICitizenRepository citizenRepository;
    @Autowired
    ISupplierRepository supplierRepository;
    @Autowired
    IContractorRepository contractorRepository;
    @Autowired
    IUserRepository userRepository;
    @Autowired
    IAdminRepository iAdminRepository;

    @Override
    public User signUpUser(SignDto signDto) {
        User user = new User();
        user.setUsername(signDto.getUsername());
        user.setPassword(signDto.getPassword());
        Set<String> roles = new HashSet<>();
        roles.add("ROLE_" + signDto.getUserType().toString().toUpperCase());
        user.setRoles(roles);
        user = userRepository.save(user);

        switch (signDto.getUserType().toString().toUpperCase()) {
            case "CITIZEN":
                Citizen citizen = new Citizen();
                citizen.setFirstName(signDto.getFirstName());
                citizen.setLastName(signDto.getLastName());
                citizen.setEmail(signDto.getEmail());
                citizen.setPhoneNumber(signDto.getPhoneNumber());
                citizen.setAddress(signDto.getAddress());
                citizen.setUser(user);
                citizenRepository.save(citizen);
                break;
            case "ADMIN":
                Administrator admin = new Administrator();
                admin.setUsername(signDto.getUsername());
                admin.setPassword(signDto.getPassword());
                admin.setUser(user);
                iAdminRepository.save(admin);
                break;
            case "SUPPLIER":
                Supplier supplier = new Supplier();
                supplier.setSupplierName(signDto.getSupplierName());
                supplier.setCompanyName(signDto.getCompanyName());
                supplier.setAddress(signDto.getAddress());
                supplier.setEmail(signDto.getEmail());
                supplier.setPhoneNumber(signDto.getPhoneNumber());
                supplier.setUsername(signDto.getUsername());
                supplier.setTinNumber(signDto.getTinNumber());
                supplier.setUser(user);
                supplierRepository.save(supplier);
                break;

            case "CONTRACTOR":
                Optional<Contractor> checkContractor = contractorRepository
                        .findContractorByEmailAndUsername(signDto.getEmail(), signDto.getUsername());
                if (checkContractor.isPresent()) {
                    throw new IllegalArgumentException("Contractor with email and username already exists.");
                } else {
                    Contractor contractor = new Contractor();
                    contractor.setCompanyName(signDto.getCompanyName());
                    contractor.setEmail(signDto.getEmail());
                    contractor.setPhoneNumber(signDto.getPhoneNumber());
                    contractor.setUsername(signDto.getUsername());
                    contractor.setContactDetails(signDto.getContactDetails());
                    contractor.setLicenseNumber(signDto.getLicenseNumber());
                    contractor.setAddress(signDto.getAddress());
                    contractor.setProfilePic(signDto.getProfilePic());
                    contractor.setUser(user);
                    contractorRepository.save(contractor);
                    break;
                }

        }


        return user;
    }

    @Override
    public void deleteUser(UUID id) {
            Optional<User> user = userRepository.findById(id);
            if (user.isPresent()) {
                switch (user.get().getRoles().iterator().next().toUpperCase()) {
                    case "ROLE_CITIZEN":
                        citizenRepository.deleteById(id);
                        break;
                    case "ROLE_ADMIN":
                        iAdminRepository.deleteById(id);
                        break;
                    case "ROLE_SUPPLIER":
                        supplierRepository.deleteById(id);
                        break;
                    case "ROLE_CONTRACTOR":
                        contractorRepository.deleteById(id);
                        break;
                }
                userRepository.deleteById(id);
            } else {

                throw new EntityNotFoundException("User with id ::" + id + " not found.");
            }
    }

    @Override
    public Optional<User> findByUser(String username) {
        Optional<User> user = userRepository.findByUsername(username);
        return Optional.ofNullable(user.orElseThrow(() -> new UsernameNotFoundException("User with username ::" + username + " not found.")));
    }

    @Override
    public Optional<User> findById(UUID id) {
        return Optional.ofNullable(userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("USER OF ID {} NOT FOUND" + id)));
    }

    @Override

    public Citizen saveCitizen(Citizen citizen) {
        return citizenRepository.save(citizen);
    }

    @Override
    public Page<Administrator> getAllAdminUsers(Pageable pageable) {
        return iAdminRepository.findAll(pageable);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public List<Contractor> getContractors() {
        return contractorRepository.findAll();
    }

    @Override
    public List<Supplier> loadSuppliers() {
        return supplierRepository.findAll();
    }

}
