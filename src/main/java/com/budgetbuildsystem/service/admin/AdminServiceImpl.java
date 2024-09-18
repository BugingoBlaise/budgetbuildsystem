package com.budgetbuildsystem.service.admin;

import com.budgetbuildsystem.model.Administrator;
import com.budgetbuildsystem.repository.IAdminRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service@Transactional

public class AdminServiceImpl implements IAdminService{
    @Autowired
    IAdminRepository iAdminRepository;
    @Override
    public Administrator saveAdmin(Administrator administrator) {
        return iAdminRepository.save(administrator);
    }
}
