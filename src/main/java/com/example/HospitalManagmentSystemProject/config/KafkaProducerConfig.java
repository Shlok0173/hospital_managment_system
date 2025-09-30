package com.example.HospitalManagmentSystemProject.config;

import com.example.HospitalManagmentSystemProject.dto.PatientEvent;
import com.nimbusds.jose.JSONSerializable;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaProducerConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String host;

    @Bean
    public ProducerFactory<String , PatientEvent> producerFactory(){
        Map<String,Object> configprops=new HashMap<>();
        configprops.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,host);
        configprops.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configprops.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);




        // reliability settings
        configprops.put(ProducerConfig.ACKS_CONFIG, "all");
        configprops.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, true);
        configprops.put(ProducerConfig.MAX_IN_FLIGHT_REQUESTS_PER_CONNECTION, 1);
        configprops.put(ProducerConfig.RETRIES_CONFIG, 5);
        return  new DefaultKafkaProducerFactory<>(configprops);
    }


    @Bean
    public KafkaTemplate<String,PatientEvent> kafkaTemplate(){
        return  new KafkaTemplate<>(producerFactory());
    }


}
