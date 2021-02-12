package com.mediscreen.abernathyapp.assess.unit;

import com.mediscreen.abernathyapp.assess.dtos.DiabeteAssessmentDTO;
import com.mediscreen.abernathyapp.assess.dtos.PatientAssessmentDTO;
import com.mediscreen.abernathyapp.assess.dtos.PatientHealthInfosDTO;
import com.mediscreen.abernathyapp.assess.models.DiabeteStatus;
import com.mediscreen.abernathyapp.assess.proxies.PatHistoryProxy;
import com.mediscreen.abernathyapp.assess.proxies.PatientProxy;
import com.mediscreen.abernathyapp.assess.services.AgeCalculatorService;
import com.mediscreen.abernathyapp.assess.services.AssessService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

@AutoConfigureMockMvc
@SpringBootTest
@ActiveProfiles("test")
public class AssessServiceTest {

    int age;
    String sex;
    long termCount;
    DiabeteStatus expectedStatus;
    @Autowired
    private AssessService assessService;
    @MockBean
    private PatHistoryProxy patHistoryProxy;
    @MockBean
    private PatientProxy patientProxy;
    @MockBean
    private AgeCalculatorService ageCalculatorService;

    @AfterEach
    private void runDiabeteStatusTest() {
        PatientAssessmentDTO patientAssessmentDTO = new PatientAssessmentDTO(
                "family",
                "given",
                this.age);

        DiabeteAssessmentDTO diabeteAssessmentDTO = new DiabeteAssessmentDTO(
                patientAssessmentDTO,
                expectedStatus);

        PatientHealthInfosDTO patientHealthInfosDTO = new PatientHealthInfosDTO(
                "1",
                "family",
                "given",
                LocalDate.EPOCH,
                sex);

        doReturn(ResponseEntity.ok(patientHealthInfosDTO)).when(patientProxy).getPatient(anyString());
        doReturn(ResponseEntity.ok(termCount)).when(patHistoryProxy).getAssessment(anyString(), anySet());
        when(ageCalculatorService.getAge(any(LocalDate.class))).thenReturn(age);

        assertThat(assessService.assessPatientDiabeteStatus("1"))
                .usingRecursiveComparison()
                .isEqualTo(diabeteAssessmentDTO);
    }

    @Test
    public void Given_patientWithZeroTerm_When_getDiabeteStatus_Then_isNone() {
        this.age = 42;
        this.sex = "M";
        this.termCount = 0L;
        this.expectedStatus = DiabeteStatus.NONE;
    }

    @Test
    public void Given_patientWithOneTerm_When_getDiabeteStatus_Then_isNone() {

        // EDGE CASE OF PATIENT WITH 1 TERMINOLOGY COUNT
        this.age = 42;
        this.sex = "M";
        this.termCount = 1L;
        this.expectedStatus = DiabeteStatus.NONE;
    }

    @Test
    public void Given_patientAbove30WithTwoTerms_When_getDiabeteStatus_Then_isBorderline() {

        this.age = 31;
        this.sex = "F";
        this.termCount = 2L;
        this.expectedStatus = DiabeteStatus.BORDERLINE;
    }

    @Test
    public void Given_patientAt30WithTwoTerms_When_getDiabeteStatus_Then_isBorderline() {

        // EDGE CASE OF PATIENT WITH EXACTLY 30.
        this.age = 30;
        this.sex = "M";
        this.termCount = 2L;
        this.expectedStatus = DiabeteStatus.BORDERLINE;
    }

    @Test
    public void Given_malePatientUnder30WithThreeTerms_When_getDiabeteStatus_Then_isInDanger() {

        this.age = 25;
        this.sex = "M";
        this.termCount = 3L;
        this.expectedStatus = DiabeteStatus.IN_DANGER;
    }

    @Test
    public void Given_femalePatientUnder30WithFourTerms_When_getDiabeteStatus_Then_isInDanger() {

        this.age = 22;
        this.sex = "F";
        this.termCount = 4L;
        this.expectedStatus = DiabeteStatus.IN_DANGER;
    }

    @Test
    public void Given_patientAbove30WithSixTerms_When_getDiabeteStatus_Then_isInDanger() {

        this.age = 53;
        this.sex = "M";
        this.termCount = 6L;
        this.expectedStatus = DiabeteStatus.IN_DANGER;
    }

    @Test
    public void Given_malePatientUnder30WithFiveTerms_When_getDiabeteStatus_Then_isEarlyOnset() {

        this.age = 27;
        this.sex = "M";
        this.termCount = 5L;
        this.expectedStatus = DiabeteStatus.EARLY_ONSET;
    }

    @Test
    public void Given_femalePatientUnder30WithSevenTerms_When_getDiabeteStatus_Then_isEarlyOnset() {

        this.age = 23;
        this.sex = "F";
        this.termCount = 7L;
        this.expectedStatus = DiabeteStatus.EARLY_ONSET;
    }

    @Test
    public void Given_patientAbove30WithHeightTerms_When_getDiabeteStatus_Then_isEarlyOnset() {

        this.age = 36;
        this.sex = "F";
        this.termCount = 8L;
        this.expectedStatus = DiabeteStatus.EARLY_ONSET;
    }

    @Test
    public void Given_patientAbove30WithTebTerms_When_getDiabeteStatus_Then_isEarlyOnset() {

        this.age = 39;
        this.sex = "M";
        this.termCount = 10L;
        this.expectedStatus = DiabeteStatus.EARLY_ONSET;
    }


}
