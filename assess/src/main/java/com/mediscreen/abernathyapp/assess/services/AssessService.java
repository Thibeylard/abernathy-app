package com.mediscreen.abernathyapp.assess.services;

import com.mediscreen.abernathyapp.assess.dtos.DiabeteAssessmentDTO;

public interface AssessService {

    DiabeteAssessmentDTO assessPatientDiabeteStatus(String patientId);

    DiabeteAssessmentDTO assessPatientDiabeteStatus(String family, String given);
}
