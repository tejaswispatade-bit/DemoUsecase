package com.kafka.DemoUsecase.Consumer;


import com.kafka.DemoUsecase.avro.Employee.Employee;
import com.kafka.DemoUsecase.avro.Employee.salary_details;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class EmployeeConsumer {
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public EmployeeConsumer(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }
    public void registerSchemaConsumer() {
        salary_details sal = salary_details.newBuilder()
                .setBaseSalary(60000)
                .setCurrency("USD").build();
        Employee emp = Employee.newBuilder()
                .setEmployeeId("101")
                .setDepartmentName("Engineering")
                .setFullName("John Doe")
                .setWorkEmail("john.doe@example.com")
                .setEmploymentStatus("Active")
                .setHireDate(null)
                .setSalaryDetails(sal)
                .build();

        System.out.println(">>> Sending User message");
        // THIS ONE LINE registers schema automatically
        kafkaTemplate.send("Employee",emp);
    }
}
