package com.budgetbuildsystem.service.authentication;

import com.budgetbuildsystem.dto.AuthResponse;
import com.budgetbuildsystem.dto.LoginRequest;
import com.budgetbuildsystem.dto.SignDto;
import com.budgetbuildsystem.model.*;
import com.budgetbuildsystem.repository.*;
import com.budgetbuildsystem.util.JwtService;
import jakarta.mail.MessagingException;
import jakarta.persistence.EntityExistsException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
@Transactional
@Slf4j
public class AuthenticationService {
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
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private EmailService emailService;

    public AuthResponse register(SignDto signDto) {

        User user = new User();

        Optional<User> checkUsername = userRepository.findByUsername(signDto.getUsername());
        if (checkUsername.isPresent()) {
            throw new EntityExistsException("Username already exists");
        }
        user.setUsername(signDto.getUsername());
        user.setPassword(passwordEncoder.encode(signDto.getPassword()));
        Set<String> roles = new HashSet<>();
        roles.add(signDto.getUserType().toString().toUpperCase());
        user.setRoles(roles);



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
                sendEmail(signDto.getEmail(), signDto.getUsername(), signDto.getFirstName());
                break;
            case "ADMIN":
                Administrator admin = new Administrator();
                admin.setUsername(signDto.getUsername());
                admin.setUser(user);
                iAdminRepository.save(admin);
                sendEmail(signDto.getEmail(), signDto.getUsername(), signDto.getFirstName());

                break;
            case "SUPPLIER":
                Supplier supplier = getSupplier(signDto, user);
                supplierRepository.save(supplier);
                sendEmail(signDto.getEmail(), signDto.getUsername(), signDto.getFirstName());

                break;

            case "CONTRACTOR":
                Optional<Contractor> checkContractor = contractorRepository
                        .findContractorByEmailAndUsername(signDto.getEmail(), signDto.getUsername());
                if (checkContractor.isPresent()) {
                    throw new IllegalArgumentException("Contractor with email and username already exists.");
                } else {
                    Contractor contractor = getContractor(signDto, user);
                    contractorRepository.save(contractor);
                    sendEmail(signDto.getEmail(), signDto.getUsername(), signDto.getFirstName());

                    break;
                }
            default:
                throw new IllegalArgumentException("Invalid user type.");
        }
        user = userRepository.save(user);
        String token = jwtService.generateToken(user);
        return new AuthResponse(user.getId(),user.getUsername(), user.getRoles().toString(), token);

    }


    public void sendEmail(String email, String username, String firstName) {
        try {
            emailService.sendWelcomeEmail(email, username);
        } catch (MessagingException e) {
            // Log the error but don't prevent user registration
            // Consider implementing a retry mechanism or queueing system
            e.printStackTrace();
        }
    }

    public AuthResponse authenticate(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );



        User user = userRepository.findByUsername(request.getUsername()).orElseThrow();
        UUID userId=user.getId();

        String username = user.getUsername();

        String role = user.getRoles().toString();

        String token = jwtService.generateToken(user);

        return new AuthResponse(userId,username, role, token);
    }

    private static Contractor getContractor(SignDto signDto, User user) {
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
        return contractor;
    }

    private static Supplier getSupplier(SignDto signDto, User user) {
        Supplier supplier = new Supplier();
        supplier.setSupplierName(signDto.getSupplierName());
        supplier.setCompanyName(signDto.getCompanyName());
        supplier.setAddress(signDto.getAddress());
        supplier.setEmail(signDto.getEmail());
        supplier.setPhoneNumber(signDto.getPhoneNumber());
        supplier.setUsername(signDto.getUsername());
        supplier.setTinNumber(signDto.getTinNumber());
        supplier.setUser(user);
        return supplier;
    }
}
