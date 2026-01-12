package com.kafka.DemoUsecase.KStreamTransformation;

import com.kafka.DemoUsecase.avro.Employee.Employee;
import com.kafka.DemoUsecase.avro.Employee.salary_details;
import com.kafka.DemoUsecase.avro.User.User;
import io.confluent.kafka.streams.serdes.avro.SpecificAvroSerde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Produced;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.Map;
@Component
public class UserToEmployeeStream {

    @Value("${spring.kafka.properties.schema.registry.url}")
    private String schemaRegistryUrl;

    @Value("${spring.kafka.properties.basic.auth.credentials.source}")
    private String credentialsSource;

    @Value("${spring.kafka.properties.basic.auth.user.info}")
    private String userInfo;
    @Bean
    public KStream<String, User> process(StreamsBuilder builder) {

        SpecificAvroSerde<User> userSerde = new SpecificAvroSerde<>();
        SpecificAvroSerde<Employee> employeeSerde = new SpecificAvroSerde<>();

        Map<String, String> props = Map.of(
                "schema.registry.url", schemaRegistryUrl,
                "basic.auth.credentials.source", credentialsSource,
                "basic.auth.user.info", userInfo
        );

        userSerde.configure(props, false);
        employeeSerde.configure(props, false);

        KStream<String, User> stream =
                builder.stream("User", Consumed.with(Serdes.String(), userSerde));

        stream
                .mapValues(this::mapToEmployee)
                .to("Employee", Produced.with(Serdes.String(), employeeSerde));

        return stream;
    }

    private Employee mapToEmployee(User user) {

        return Employee.newBuilder()
                .setEmployeeId("E" + user.getUserId())
                .setFullName(
                        user.getPersonalDetails().getFirstName() + " " +
                                user.getPersonalDetails().getLastName()
                )
                .setDepartmentName(user.getDepartment())
                .setWorkEmail(user.getContact().getEmail())
                .setEmploymentStatus(user.getStatus())
                .setHireDate(null)
                .setSalaryDetails(
                        salary_details.newBuilder().
                                setBaseSalary(60000).setCurrency("USD").build()
                )
                .build();
    }
}
