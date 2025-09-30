package com.example.HospitalManagmentSystemProject.serviceImpl;

import com.example.HospitalManagmentSystemProject.dto.PatientEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class PatientEventProducer {

    @Autowired
    private KafkaTemplate<String,PatientEvent> kafkaTemplate;

    @Value("${hms.kafka.topic.patient-registered:patient-registered}")
    private String topic;

    public void publish(PatientEvent event) {
        // use patient id as key for per-patient ordering
        kafkaTemplate.send(topic, String.valueOf(event.getId()), event);
    }
}
