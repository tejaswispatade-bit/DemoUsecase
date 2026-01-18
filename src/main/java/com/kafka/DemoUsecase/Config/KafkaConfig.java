package com.kafka.DemoUsecase.Config;

import io.confluent.kafka.serializers.KafkaAvroDeserializer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ContainerProperties;

import java.util.HashMap;
import java.util.Map;

@EnableKafka
@Configuration
public class KafkaConfig {

    // ===============================
    // Read values from application.properties
    // ===============================

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Value("${spring.kafka.consumer.group-id}")
    private String groupId;

    @Value("${spring.kafka.consumer.auto-offset-reset}")
    private String autoOffsetReset;

    @Value("${spring.kafka.properties.security.protocol}")
    private String securityProtocol;

    @Value("${spring.kafka.properties.sasl.mechanism}")
    private String saslMechanism;

    @Value("${spring.kafka.properties.sasl.jaas.config}")
    private String saslJaasConfig;

    @Value("${spring.kafka.properties.schema.registry.url}")
    private String schemaRegistryUrl;

    @Value("${spring.kafka.properties.basic.auth.credentials.source}")
    private String schemaAuthSource;

    @Value("${spring.kafka.properties.basic.auth.user.info}")
    private String schemaAuthUserInfo;

    @Value("${spring.kafka.properties.specific.avro.reader}")
    private boolean specificAvroReader;

    @Value("${spring.kafka.consumer.max-poll-records}")
    private int maxPollRecords;

    // ===============================
    // Consumer Factory
    // ===============================
    @Bean
    public ConsumerFactory<String, Object> consumerFactory() {

        Map<String, Object> props = new HashMap<>();

        // Kafka Cluster
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, autoOffsetReset);
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);
        props.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, maxPollRecords);
        // Security
        props.put("security.protocol", securityProtocol);
        props.put("sasl.mechanism", saslMechanism);
        props.put("sasl.jaas.config", saslJaasConfig);

        // Deserializers
        props.put(
                ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
                StringDeserializer.class
        );
        props.put(
                ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
                KafkaAvroDeserializer.class
        );

        // Schema Registry
        props.put("schema.registry.url", schemaRegistryUrl);
        props.put("basic.auth.credentials.source", schemaAuthSource);
        props.put("basic.auth.user.info", schemaAuthUserInfo);

        // Avro → POJO
        props.put("specific.avro.reader", specificAvroReader);

        return new DefaultKafkaConsumerFactory<>(props);
    }

    // ===============================
    // Listener Factory
    // ===============================
    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, Object>
    kafkaListenerContainerFactory() {

        ConcurrentKafkaListenerContainerFactory<String, Object> factory =
                new ConcurrentKafkaListenerContainerFactory<>();

        factory.setConsumerFactory(consumerFactory());
        factory.setConcurrency(3);
        factory.getContainerProperties().setAckMode(
                ContainerProperties.AckMode.MANUAL_IMMEDIATE
        );

        return factory;
    }
}
