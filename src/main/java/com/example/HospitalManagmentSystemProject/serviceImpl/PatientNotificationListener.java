package com.example.HospitalManagmentSystemProject.serviceImpl;

import com.example.HospitalManagmentSystemProject.dto.PatientEvent;
import com.example.HospitalManagmentSystemProject.dto.PatientResponseDto;
import com.example.HospitalManagmentSystemProject.service.PatientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import static com.example.HospitalManagmentSystemProject.utill.PdfGenerator.generatePatientPdf;

import java.io.ByteArrayInputStream;
@Service
public class PatientNotificationListener {

//    @Autowired
//    private JavaMailSender javaMailSender;
//
//    public void sendPatientDetails(PatientResponseDto patient){
//
//        byte[] pdfBytes = generatePatientPdf(patient);
//        ByteArrayInputStream pdfStream = new ByteArrayInputStream(pdfBytes);
//
//
//        SimpleMailMessage message=new SimpleMailMessage();
//        message.setFrom("shlokit7856@gmail.com"); // must match spring.mail.username
//        message.setTo(patient.getGmail());        // send to patient email
//        message.setSubject("Hospital Registration Confirmation");
//
//        String mailContent = "Hello " + patient.getName() + ",\n\n"
//                + "You have been registered successfully at our hospital.\n\n"
//                + "📌 Patient Details:\n"
//                + "Name: " + patient.getName() + "\n"
//                + "Gender: " + patient.getGender() + "\n"
//                + "Insurance: " + (patient.getInsurance() != null ? patient.getInsurance().getProviderName() : "Not Provided") + "\n"
//                + "Appointments: " + (patient.getAppointmentIds() != null ? patient.getAppointmentIds().toString() : "None") + "\n\n"
//                + "Regards,\nHospital Team";
//
//        message.setText(mailContent);
//        javaMailSender.send(message);
 //   }

    private static final Logger logger = LoggerFactory.getLogger(PatientNotificationListener.class);

    @Autowired(required = false)
    private JavaMailSender mailSender; // optional, only if you configured mail

    @KafkaListener(topics = "patient-registered", groupId = "hms-patient-group", containerFactory = "kafkaListenerContainerFactory")
    public void onPatientRegistered(PatientEvent  event) {
        logger.info("Consumed PatientRegisteredEvent id={} email={}", event.getId(), event.getGmail());

        // Example: send email (if mailSender configured)
        if (mailSender != null && event.getGmail() != null) {
            try {
                SimpleMailMessage msg = new SimpleMailMessage();
                msg.setTo(event.getGmail());
                msg.setSubject("Welcome to Hospital");

                StringBuilder sb=new StringBuilder();
                sb.append("Dear ").append(event.getName()).append(",\n\n")
                        .append("Welcome to ABC Multi-Speciality Hospital!\n")
                        .append("We are pleased to confirm your successful registration.\n\n")
                        .append("Here are your registration details:\n")
                        .append("---------------------------------------------------\n")
                        .append("Patient ID     : ").append(event.getId()).append("\n")
                        .append("Full Name      : ").append(event.getName()).append("\n")
                        .append("Email          : ").append(event.getGmail()).append("\n")
                        .append("Gender         : ").append(event.getGender()).append("\n")
                        .append("---------------------------------------------------\n\n")
                        .append("Our hospital services are available 24/7.\n")
                        .append("For any queries, contact us at support@abchospital.com or call +91-9876543210.\n\n")
                        .append("We wish you good health!\n")
                        .append("Regards,\n")
                        .append("ABC Multi-Speciality Hospital");

                msg.setText(sb.toString());
                mailSender.send(msg);
                logger.info("Email sent to {}", event.getGmail());
            } catch (Exception ex) {
                logger.error("Failed to send email to {} : {}", event.getGmail(), ex.getMessage());
                // allow retries / DLQ by throwing runtime exception if needed
            }
        }

        // Example: write audit (pseudo)
        // auditRepository.save(new Audit(...))
    }
}
