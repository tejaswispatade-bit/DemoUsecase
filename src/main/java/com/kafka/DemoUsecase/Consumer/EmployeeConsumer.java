package com.kafka.DemoUsecase.Consumer;

import com.kafka.DemoUsecase.avro.Employee.Employee;
import com.kafka.DemoUsecase.db.entity.EmployeeEntity;
import com.kafka.DemoUsecase.db.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;

@Service
@Slf4j

public class EmployeeConsumer {

    private final EmployeeRepository repository;

    public EmployeeConsumer(EmployeeRepository repository) {
        this.repository = repository;
    }
    @KafkaListener(
            topics = "Employee",
            groupId = "employee-db-group",
            containerFactory = "kafkaListenerContainerFactory"
    )

    public void consume(Employee employee, Acknowledgment ack) {
try {
    log.info("Received Employee from Kafka: {}", employee);

    // Prevent duplicate inserts
    repository.findByEmployeeId(employee.getEmployeeId().toString())
            .ifPresentOrElse(
                    e -> log.warn("Employee already exists: {}", employee.getEmployeeId()),
                    () -> saveEmployee(employee)
            );
    ack.acknowledge();

} catch (Exception e) {
    throw new RuntimeException(e);
}
    }

    private void saveEmployee(Employee employee) {

        EmployeeEntity entity = EmployeeEntity.builder()
                .employeeId(employee.getEmployeeId().toString())
                .fullName(employee.getFullName().toString())
                .departmentName(employee.getDepartmentName().toString())
                .workEmail(employee.getWorkEmail().toString())
                .employmentStatus(employee.getEmploymentStatus().toString())
                .hireDate(null)
                .baseSalary(employee.getSalaryDetails().getBaseSalary())
                .currency(employee.getSalaryDetails().getCurrency().toString())
                .build();

        repository.save(entity);
        log.info("Employee saved to DB: {}", entity.getEmployeeId());
    }
}
