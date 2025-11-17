package com.itrumuserservice.config.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.itrumuserservice.dto.TransactionRequest;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaProducerConfig {
    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Value("${spring.kafka.topic.transaction-request.name}")
    private String transactionRequestTopicName;

    @Value("${spring.kafka.topic.transaction-request.partitions:3}")
    private int transactionRequestPartitions;

    @Value("${spring.kafka.topic.transaction-request.replicas:1}")
    private int transactionRequestReplicas;


    @Bean
    public ProducerFactory<String, TransactionRequest> producerFactory(ObjectMapper objectMapper) {
        Map<String, Object> config = new HashMap<>();
        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        var serializer = new JsonSerializer<TransactionRequest>(objectMapper);
        serializer.setAddTypeInfo(false);

        return new DefaultKafkaProducerFactory<>(config, new StringSerializer(), serializer);
    }

    @Bean
    public KafkaTemplate<String, TransactionRequest> kafkaTemplate(
            ProducerFactory<String, TransactionRequest> producerFactory
    ) {
        return new KafkaTemplate<>(producerFactory);
    }

    @Bean
    public NewTopic  transactionRequestTopic() {
        return TopicBuilder
                .name(transactionRequestTopicName)
                .partitions(transactionRequestPartitions)
                .replicas(transactionRequestReplicas)
                .build();
    }
}