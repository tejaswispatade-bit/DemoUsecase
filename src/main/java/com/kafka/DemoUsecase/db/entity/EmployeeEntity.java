package com.kafka.DemoUsecase.db.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "employee")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeEntity {

    @Id
    @Column(name = "employee_id")
    private String employeeId;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "department_name")
    private String departmentName;

    @Column(name = "work_email")
    private String workEmail;

    @Column(name = "employment_status")
    private String employmentStatus;

    @Column(name = "hire_date")
    private String hireDate;

    @Column(name = "base_salary")
    private Integer baseSalary;

    @Column(name = "currency")
    private String currency;
}
