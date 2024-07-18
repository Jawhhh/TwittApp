package by.jawh.authmicroservice.config;

import by.jawh.eventsforalltopics.events.UserRegisteredEvent;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.HashMap;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class KafkaConfig {

    private final Environment environment;

    @Bean
    Map<String, Object> producerConfigs() {

        Map<String, Object> config = new HashMap<>();

        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,
                environment.getProperty("spring.kafka.producer.bootstrap-servers"));
        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
                environment.getProperty("spring.kafka.producer.key-serializer"));
        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
                environment.getProperty("spring.kafka.producer.value-serializer"));
        config.put(ProducerConfig.ACKS_CONFIG,
                environment.getProperty("spring.kafka.producer.acks"));
        config.put(ProducerConfig.DELIVERY_TIMEOUT_MS_CONFIG,
                environment.getProperty("spring.kafka.producer.properties.delivery.timeout.ms"));
        config.put(ProducerConfig.LINGER_MS_CONFIG,
                environment.getProperty("spring.kafka.producer.properties.linger.ms"));
        config.put(ProducerConfig.REQUEST_TIMEOUT_MS_CONFIG,
                environment.getProperty("spring.kafka.producer.properties.request.timeout.ms"));

        return config;
    }

    @Bean
    ProducerFactory<String, UserRegisteredEvent> producerFactory() {
        return new DefaultKafkaProducerFactory<>(producerConfigs());
    }

    @Bean
    KafkaTemplate<String, UserRegisteredEvent> kafkaTemplate() {
        return new KafkaTemplate<String, UserRegisteredEvent>(producerFactory());
    }

    @Bean
    NewTopic userRegisteredEventsTopic() {
        return TopicBuilder
                .name("user-registered-events-topic")
                .partitions(3)
                .replicas(3)
                .configs(Map.of("min.insync.replicas", "2"))
                .build();
    }

    @Bean
    NewTopic userSignInEventsTopic() {
        return TopicBuilder
                .name("user-sign-in-events-topic")
                .partitions(3)
                .replicas(3)
                .configs(Map.of("min.insync.replicas", "2"))
                .build();
    }
}
