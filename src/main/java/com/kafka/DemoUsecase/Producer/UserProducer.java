package com.kafka.DemoUsecase.Producer;

import com.kafka.DemoUsecase.avro.User.User;
import com.kafka.DemoUsecase.avro.User.contact;
import com.kafka.DemoUsecase.avro.User.personal_details;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;



@Service
public class UserProducer {

    private final KafkaTemplate<String, User> kafkaTemplate;

    public UserProducer(KafkaTemplate<String, User> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void registerSchema() {
        System.out.println("Inside UserProducer");

        contact con = contact.newBuilder()
                .setEmail("ron.joe@example.com")
                .setPhone("123-889-0045")
                .build();

        personal_details details = personal_details.newBuilder()
                .setFirstName("Ron")
                .setLastName("Joe")
                .setDateOfBirth("1992-02-15")
                .build();

        User user = User.newBuilder().setUserId(103)
                .setContact(con)
                .setPersonalDetails(details)
                .setDepartment("Engineering")
                .setStatus("Active")
                .build();

        kafkaTemplate.send("User",user);

    }
}

