package com.budgetbuildsystem.service.user;

import com.budgetbuildsystem.dto.SignDto;
import com.budgetbuildsystem.model.*;
import com.budgetbuildsystem.repository.*;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
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

                Contractor contractor = new Contractor();
                contractor.setCompanyName(signDto.getCompanyName());
                contractor.setEmail(signDto.getEmail());
                contractor.setPhoneNumber(signDto.getPhoneNumber());
                contractor.setUsername(signDto.getUsername());
                contractor.setContactDetails(signDto.getContactDetails());
                contractor.setLicenseNumber(signDto.getLicenseNumber());
                contractor.setAddress(signDto.getAddress());
                contractor.setUser(user);
                contractorRepository.save(contractor);
                break;
        }

        return user;
    }

    @Override
    public Optional<User> findByUser(String username) {
        Optional<User> user = userRepository.findByUsername(username);
        return Optional.ofNullable(user.orElseThrow(() -> new UsernameNotFoundException("User with username ::"+username+" not found.")));
    }

    @Override
    public Optional<User> findById(UUID id) {
        return Optional.ofNullable(userRepository.findById(id).orElseThrow(()->new EntityNotFoundException("USER OF ID {} NOT FOUND"+id)));
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

  /*  public User login(String username, String password) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        if (passwordEncoder.matches(password, user.getPassword())) {
            return user;
        } else {
            throw new BadCredentialsException("Invalid password");
        }
    }
*/

   /* @Override
    public User signUpContractor(Local_Contractor contractor) {
        return null;
    }

    @Override
    public User signUpSupplier(Supplier supplier) {
        return null;
    }*/
}
