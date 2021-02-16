package com.mediscreen.abernathyapp.assess.dtos;

import com.mediscreen.abernathyapp.assess.models.DiabeteStatus;

public class DiabeteAssessmentDTO {

    PatientAssessmentDTO patient;
    String assessment;

    public DiabeteAssessmentDTO() {
    }

    public DiabeteAssessmentDTO(PatientAssessmentDTO patient, DiabeteStatus diabeteStatus) {
        this.patient = patient;
        this.assessment = diabeteStatus.toString();
    }

    public PatientAssessmentDTO getPatient() {
        return patient;
    }

    public String getAssessment() {
        return assessment;
    }
}
