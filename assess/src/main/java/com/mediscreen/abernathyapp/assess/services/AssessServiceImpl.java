package com.mediscreen.abernathyapp.assess.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mediscreen.abernathyapp.assess.dtos.DiabeteAssessmentDTO;
import com.mediscreen.abernathyapp.assess.dtos.PatientAssessmentDTO;
import com.mediscreen.abernathyapp.assess.dtos.PatientHealthInfosDTO;
import com.mediscreen.abernathyapp.assess.models.DiabeteStatus;
import com.mediscreen.abernathyapp.assess.proxies.PatHistoryProxy;
import com.mediscreen.abernathyapp.assess.proxies.PatientProxy;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AssessServiceImpl implements AssessService {

    private final PatientProxy patientProxy;
    private final PatHistoryProxy patHistoryProxy;
    private final ObjectMapper mapper;
    private final Logger logger;
    private final AgeCalculatorService ageCalculator;

    @Autowired
    public AssessServiceImpl(PatientProxy patientProxy,
                             PatHistoryProxy patHistoryProxy,
                             AgeCalculatorService ageCalculator,
                             ObjectMapper mapper,
                             Logger logger) {
        this.patientProxy = patientProxy;
        this.patHistoryProxy = patHistoryProxy;
        this.ageCalculator = ageCalculator;
        this.mapper = mapper;
        this.logger = logger;
    }

    @Override
    public DiabeteAssessmentDTO assessPatientDiabeteStatus(String patientId) {
        ResponseEntity<?> responseEntity = patientProxy.getPatient(patientId);

        // Http Status is OK, patHistory response contains valid Patient
        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            PatientHealthInfosDTO patientHealthInfosDTO = mapper.convertValue(responseEntity.getBody(), PatientHealthInfosDTO.class);
            return assessPatientDiabeteStatus(patientHealthInfosDTO);
        } else {
            // Http Status is not Ok, there are no Patient with given id
            throw new NoSuchElementException();
        }
    }

    @Override
    public DiabeteAssessmentDTO assessPatientDiabeteStatus(String family, String given) {
        ResponseEntity<?> responseEntity = patientProxy.getPatient(family, given);

        // Http Status is OK, patHistory response contains valid Patient
        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            PatientHealthInfosDTO patientHealthInfosDTO = mapper.convertValue(responseEntity.getBody(), PatientHealthInfosDTO.class);
            return assessPatientDiabeteStatus(patientHealthInfosDTO);
        } else {
            // Http Status is not Ok, there are no Patient with given params
            throw new NoSuchElementException();
        }
    }

    // -------------------------------------------------------------- Convenience private methods
    // ------------------------------------------------------------------------------------

    private DiabeteAssessmentDTO assessPatientDiabeteStatus(PatientHealthInfosDTO patientInfos) {

        ResponseEntity<?> responseEntity = patHistoryProxy.getAssessment(patientInfos.getId(),
                Set.of(Arrays.stream(DiabeteStatus.values())
                        .map(DiabeteStatus::toString)
                        .collect(Collectors.joining())));

        // Http Status is OK, patHistory response contains terminologyCount
        if (responseEntity.getStatusCode() == HttpStatus.OK) {

            int patientAge = ageCalculator.getAge(patientInfos.getDob());
            long terminologyCount = (long) responseEntity.getBody();
            DiabeteStatus diabeteStatus = determineDiabeteStatus(patientInfos.getSex(), patientAge, terminologyCount);

            return new DiabeteAssessmentDTO(
                    new PatientAssessmentDTO(patientInfos.getFamily(), patientInfos.getGiven(), patientAge),
                    diabeteStatus);
        } else {
            // Http Status is not Ok, there are no patHistory with given patientId
            throw new NoSuchElementException();
        }
    }

    private DiabeteStatus determineDiabeteStatus(String sex, int age, long terminologyCount) {
        if (hasDiabeteStatusEarlyOnSet(sex, age, terminologyCount)) {
            return DiabeteStatus.EARLY_ONSET;
        } else if (hasDiabeteStatusInDanger(sex, age, terminologyCount)) {
            return DiabeteStatus.IN_DANGER;
        } else if (hasDiabeteStatusBorderline(age, terminologyCount)) {
            return DiabeteStatus.BORDERLINE;
        } else if (hasDiabeteStatusNone(terminologyCount)) {
            return DiabeteStatus.NONE;
        } else {
            logger.debug("A case is missed by the conditions.");
            throw new InternalError();
        }
    }


    // -------------------------------------------------------------- DiabeteStatus conditions
    // ------------------------------------------------------------------------------------

    private boolean hasDiabeteStatusEarlyOnSet(String sex, int age, long terminologyCount) {
        return (sex.equals("M") && age < 30 && terminologyCount >= 5) ||
                (sex.equals("F") && age < 30 && terminologyCount >= 7) ||
                (age > 30 && terminologyCount >= 8);
    }


    private boolean hasDiabeteStatusInDanger(String sex, int age, long terminologyCount) {
        return (sex.equals("M") && age < 30 && terminologyCount >= 3) ||
                (sex.equals("F") && age < 30 && terminologyCount == 4) ||
                (age > 30 && terminologyCount >= 6);
    }

    private boolean hasDiabeteStatusBorderline(int age, long terminologyCount) {
        return age >= 30 && terminologyCount == 2;
    }

    private boolean hasDiabeteStatusNone(long terminologyCount) {
        return terminologyCount <= 1;
    }

}
