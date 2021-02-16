package com.mediscreen.abernathy.client.dtos;


public class DiabeteAssessmentDTO {

    PatientAssessmentDTO patient;
    String assessment;

    public DiabeteAssessmentDTO() {
    }

    public DiabeteAssessmentDTO(PatientAssessmentDTO patient, String assessment) {
        this.patient = patient;
        this.assessment = assessment;
    }

    public PatientAssessmentDTO getPatient() {
        return patient;
    }

    public void setPatient(PatientAssessmentDTO patient) {
        this.patient = patient;
    }

    public String getAssessment() {
        return assessment;
    }

    public void setAssessment(String assessment) {
        this.assessment = assessment;
    }
}
