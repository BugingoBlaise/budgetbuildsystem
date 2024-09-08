package com.budgetbuildsystem.repository.test;

import com.budgetbuildsystem.model.testModels.MyUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MyUserRepository extends JpaRepository<MyUser,Long> {
    Optional<MyUser> findByUsername(String username);

}
