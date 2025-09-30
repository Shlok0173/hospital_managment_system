package com.example.HospitalManagmentSystemProject.config;

import com.example.HospitalManagmentSystemProject.dto.PatientEvent;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConsumerConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String host;



    @Bean
    public ConsumerFactory<String, PatientEvent> consumerFactory() {
        JsonDeserializer<PatientEvent> deserializer = new JsonDeserializer<>(PatientEvent.class);
        deserializer.addTrustedPackages("com.example.HospitalManagmentSystemProject.dto");

        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, host);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,JsonDeserializer.class);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "hms-patient-group");
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        return new DefaultKafkaConsumerFactory<>(props);


    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, PatientEvent> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, PatientEvent> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        factory.setConcurrency(2);
        // You can set error handlers / DLQ here (DefaultErrorHandler) for production
        return factory;
    }
}
