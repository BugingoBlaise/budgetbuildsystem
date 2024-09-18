package com.budgetbuildsystem.service.user;

import com.budgetbuildsystem.model.*;
import com.budgetbuildsystem.repository.IAdminRepository;
import com.budgetbuildsystem.repository.ICitizenRepository;
import com.budgetbuildsystem.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    ICitizenRepository citizenRepository;
    @Autowired
    IUserRepository userRepository;
    @Autowired
    IAdminRepository iAdminRepository;


    @Override
    public void saveUser(User user) {
        userRepository.save(user);
    }

    @Override
    public Optional<User> findByUser(String username) {
        Optional<User> user = userRepository.findByUsername(username);
        return Optional.ofNullable(user.orElseThrow(() -> new UsernameNotFoundException("User with username ::"+username+" not found.")));
    }

    @Override
    public Citizen saveCitizen(Citizen citizen) {
        return citizenRepository.save(citizen);
    }

    @Override
    public Page<Administrator> getAllAdminUsers(Pageable pageable) {
        return iAdminRepository.findAll(pageable);
    }



   /* @Override
    public User signUpContractor(Local_Contractor contractor) {
        return null;
    }

    @Override
    public User signUpSupplier(Supplier supplier) {
        return null;
    }*/
}
