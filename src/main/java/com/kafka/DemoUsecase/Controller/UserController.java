package com.kafka.DemoUsecase.Controller;

import com.kafka.DemoUsecase.Consumer.EmployeeConsumer;
import com.kafka.DemoUsecase.Producer.UserProducer;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserProducer producer;
    private final EmployeeConsumer consumer;

    public UserController(UserProducer producer, EmployeeConsumer consumer) {
        this.producer = producer;
        this.consumer = consumer;
    }

    @PostMapping("/publish")
    public String publishUser() throws Exception {
        producer.registerSchema();
        //consumer.registerSchemaConsumer();
        return "Event published";
    }
}

