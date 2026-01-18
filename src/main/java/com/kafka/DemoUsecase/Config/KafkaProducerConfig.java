package com.kafka.DemoUsecase.Config;

import com.kafka.DemoUsecase.avro.User.User;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaProducerConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

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

    @Value("${spring.kafka.producer.key-serializer}")
    private Class<?> keySerializer;

    @Value("${spring.kafka.producer.value-serializer}")
    private Class<?> valueSerializer;

    // ===============================
    // ProducerFactory<String, User>
    // ===============================
    @Bean
    public ProducerFactory<String, User> userProducerFactory() {

        Map<String, Object> props = new HashMap<>();

        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);

        // Security
        props.put("security.protocol", securityProtocol);
        props.put("sasl.mechanism", saslMechanism);
        props.put("sasl.jaas.config", saslJaasConfig);

        // Schema Registry
        props.put("schema.registry.url", schemaRegistryUrl);
        props.put("basic.auth.credentials.source", schemaAuthSource);
        props.put("basic.auth.user.info", schemaAuthUserInfo);

        // Serializers
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, keySerializer);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, valueSerializer);

        return new DefaultKafkaProducerFactory<>(props);
    }

    // ===============================
    // KafkaTemplate<String, User>
    // ===============================
    @Bean
    public KafkaTemplate<String, User> userKafkaTemplate() {
        return new KafkaTemplate<>(userProducerFactory());
    }
}
