package com.kafka.DemoUsecase;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafkaStreams;

@EnableKafkaStreams
@SpringBootApplication
public class DemoUsecaseApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoUsecaseApplication.class, args);
	}

}
