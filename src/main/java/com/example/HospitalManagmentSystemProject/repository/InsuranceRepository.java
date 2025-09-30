package com.example.HospitalManagmentSystemProject.repository;

import com.example.HospitalManagmentSystemProject.entity.Insurance;
import com.example.HospitalManagmentSystemProject.type.InsuranceStatus;
import io.lettuce.core.dynamic.annotation.Param;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface InsuranceRepository extends JpaRepository<Insurance,Long> {
    List<Insurance> findByStatusAndValidUntilBefore(InsuranceStatus status, LocalDate date);


    @Modifying
    @Transactional
    @Query("UPDATE Insurance i SET i.status = 'EXPIRED' WHERE i.id IN :ids")
    void updateInsuranceToExpired(@Param("ids") List<Long> ids);

}
