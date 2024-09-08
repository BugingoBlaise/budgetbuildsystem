package com.budgetbuildsystem.repository.test;

import com.budgetbuildsystem.model.testModels.FileDB;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileDBRepository extends JpaRepository<FileDB, String> {
}
