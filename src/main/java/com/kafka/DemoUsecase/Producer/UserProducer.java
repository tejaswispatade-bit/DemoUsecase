package com.kafka.DemoUsecase.Producer;
import com.kafka.DemoUsecase.avro.User.User;
import com.kafka.DemoUsecase.avro.User.contact;
import com.kafka.DemoUsecase.avro.User.personal_details;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
//import com.kafka.DemoUsecase.avro.*;


@Service
public class UserProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public UserProducer(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void registerSchema() {
        System.out.println("Inside UserProducer");
       contact con = contact.newBuilder()
               .setEmail("karen.hopp@example.com")
               .setPhone("123-456-9990")
               .build();

       personal_details details = personal_details.newBuilder()
               .setFirstName("Karen")
               .setLastName("Hopp")
               .setDateOfBirth("1994-01-15")
               .build();

       User user = User.newBuilder().setUserId(102)
               .setContact(con)
               .setPersonalDetails(details)
               .setDepartment("Engineering")
               .setStatus("Active")
               .build();
        System.out.println(">>> Sending User message");
        // THIS ONE LINE registers schema automatically
        kafkaTemplate.send("User", user);
    }
}

