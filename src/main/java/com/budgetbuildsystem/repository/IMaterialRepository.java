package com.budgetbuildsystem.repository;

import com.budgetbuildsystem.model.Materials;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public interface IMaterialRepository extends JpaRepository<Materials, UUID> {
    @Query("SELECT m FROM Materials m WHERE m.supplier.id = :supplierId")
    List<Materials> findMaterialsBySupplierId(@Param("supplierId") UUID supplierId);

    @Query("SELECT m FROM Materials m WHERE LOWER(m.materialName) LIKE LOWER(CONCAT('%', :kw, '%'))")
    List<Materials> findMaterialsByMaterialName(@Param("kw") String materialName);

    // New query method to find materials by supplier
    @Query("SELECT m.supplier, COUNT(m) FROM Materials m GROUP BY m.supplier ORDER BY COUNT(m) DESC")
    List<Object[]> findSupplierWithMostMaterials();

    List<Materials> findByPostedDateBetween(Date startDate, Date endDate);
    long countByPostedDateBetween(Date startDate, Date endDate);
}






