package com.mediscreen.abernathyapp.assess.unit;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mediscreen.abernathyapp.assess.dtos.DiabeteAssessmentDTO;
import com.mediscreen.abernathyapp.assess.dtos.PatHistoryTermsCountDTO;
import com.mediscreen.abernathyapp.assess.dtos.PatientAssessmentDTO;
import com.mediscreen.abernathyapp.assess.dtos.PatientHealthInfosDTO;
import com.mediscreen.abernathyapp.assess.models.DiabeteStatus;
import com.mediscreen.abernathyapp.assess.proxies.PatHistoryProxy;
import com.mediscreen.abernathyapp.assess.proxies.PatientProxy;
import com.mediscreen.abernathyapp.assess.services.AgeCalculatorService;
import com.mediscreen.abernathyapp.assess.services.AssessService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.NoSuchElementException;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles("test")
public class AssessServiceTest {


    @Autowired
    private AssessService assessService;
    @MockBean
    private PatHistoryProxy patHistoryProxyMock;
    @MockBean
    private PatientProxy patientProxyMock;
    @MockBean
    private AgeCalculatorService ageCalculatorMock;
    @Autowired
    private ObjectMapper objectMapper;

    @ParameterizedTest(name = "{index} {displayName} receive expected assessment")
    @EnumSource(PatientCase.class)
    public void Given_Patient_When_assessPatientDiabeteStatus_Then_returnAccordingStatus(PatientCase patient) throws JsonProcessingException {
        PatientAssessmentDTO patientAssessmentDTO = new PatientAssessmentDTO(
                "family",
                "given",
                patient.age);

        DiabeteAssessmentDTO diabeteAssessmentDTO = new DiabeteAssessmentDTO(
                patientAssessmentDTO,
                patient.expectedStatus);

        PatientHealthInfosDTO patientHealthInfosDTO = new PatientHealthInfosDTO(
                "1",
                "family",
                "given",
                LocalDate.EPOCH,
                patient.sex);

        doReturn(EntityModel.of(patientHealthInfosDTO)).when(patientProxyMock).getPatient(anyString());
        doReturn(ResponseEntity.ok(
                objectMapper.writeValueAsString(new PatHistoryTermsCountDTO(
                        "1",
                        Set.of("ignoredTerm1", "ignoredTerm2"),
                        patient.termCount))))
                .when(patHistoryProxyMock).getAssessment(anyString(), anySet());
        when(ageCalculatorMock.getAge(any(LocalDate.class), any(LocalDate.class))).thenReturn(patient.age);

        assertThat(assessService.assessPatientDiabeteStatus("1"))
                .usingRecursiveComparison()
                .isEqualTo(diabeteAssessmentDTO);
    }

    @Test
    public void Given_anyProxyReturnBadRequest_When_assessPatientDiabeteStatus_Then_ThrowNoSuchElementException() {
        doReturn(null).when(patientProxyMock).getPatient(anyString());
        assertThrows(NoSuchElementException.class, () -> assessService.assessPatientDiabeteStatus("1"));

        PatientHealthInfosDTO patientHealthInfosDTO = new PatientHealthInfosDTO(
                "1",
                "family",
                "given",
                LocalDate.EPOCH,
                "F");

        doReturn(EntityModel.of(patientHealthInfosDTO)).when(patientProxyMock).getPatient(anyString());
        doReturn(ResponseEntity.badRequest().body(null)).when(patHistoryProxyMock).getAssessment(anyString(), anySet());
        assertThrows(NoSuchElementException.class, () -> assessService.assessPatientDiabeteStatus("1"));
    }

    private enum PatientCase {
        PATIENT_OLDER_NONE(42, "M", 0, DiabeteStatus.NONE),
        PATIENT_OLDER_BORDERLINE(35, "F", 2, DiabeteStatus.BORDERLINE),
        PATIENT_MALE_IN_DANGER(26, "M", 3, DiabeteStatus.IN_DANGER),
        PATIENT_FEMALE_IN_DANGER(26, "F", 4, DiabeteStatus.IN_DANGER),
        PATIENT_OLDER_IN_DANGER(35, "F", 6, DiabeteStatus.IN_DANGER),
        PATIENT_MALE_EARLY_ONSET(24, "M", 5, DiabeteStatus.EARLY_ONSET),
        PATIENT_FEMALE_EARLY_ONSET(24, "F", 7, DiabeteStatus.EARLY_ONSET),
        PATIENT_OLDER_EARLY_ONSET(38, "M", 8, DiabeteStatus.EARLY_ONSET),
        PATIENT_UNDEFINED(24, "M", 1, DiabeteStatus.UNDEFINED); // currently missed case

        int age;
        String sex;
        int termCount;
        DiabeteStatus expectedStatus;

        PatientCase(int age, String sex, int termCount, DiabeteStatus expectedStatus) {
            this.age = age;
            this.sex = sex;
            this.termCount = termCount;
            this.expectedStatus = expectedStatus;
        }
    }

}
