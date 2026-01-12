package com.kafka.DemoUsecase.db.repository;

import com.kafka.DemoUsecase.db.entity.EmployeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<EmployeeEntity, String> {
    Optional<EmployeeEntity> findByEmployeeId(String employeeId);
}
