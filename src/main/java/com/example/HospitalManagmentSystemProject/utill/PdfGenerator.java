package com.example.HospitalManagmentSystemProject.utill;

import com.example.HospitalManagmentSystemProject.dto.PatientResponseDto;
import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import java.io.ByteArrayOutputStream;


public class PdfGenerator {

    public static byte[] generatePatientPdf(PatientResponseDto patient) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            Document document = new Document();
            PdfWriter.getInstance(document, out);
            document.open();

            // Title
            Font fontTitle = new Font(Font.HELVETICA, 18, Font.BOLD);
            Paragraph title = new Paragraph("Patient Details Report", fontTitle);
            title.setAlignment(Paragraph.ALIGN_CENTER);
            document.add(title);

            document.add(new Paragraph(" ")); // Empty line

            // Table with details
            PdfPTable table = new PdfPTable(2);
            table.setWidthPercentage(100);

            table.addCell("Patient ID");
            table.addCell(String.valueOf(patient.getId()));

            table.addCell("Name");
            table.addCell(patient.getName());

            table.addCell("Email");
            table.addCell(patient.getGmail());

            table.addCell("Gender");
            table.addCell(patient.getGender());

            table.addCell("Birthdate");
            table.addCell(String.valueOf(patient.getBirthdate()));

            table.addCell("Created Date");
            table.addCell(String.valueOf(patient.getCreateDate()));

            table.addCell("Updated Date");
            table.addCell(String.valueOf(patient.getUpdateDate()));

            document.add(table);

            document.close();
        } catch (Exception e) {
            throw new RuntimeException("Error generating PDF: " + e.getMessage());
        }

        return out.toByteArray();
    }
}

