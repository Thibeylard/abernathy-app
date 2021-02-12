package com.mediscreen.abernathyapp.assess.unit;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mediscreen.abernathyapp.assess.controllers.AssessController;
import com.mediscreen.abernathyapp.assess.dtos.DiabeteAssessmentDTO;
import com.mediscreen.abernathyapp.assess.dtos.PatientAssessmentDTO;
import com.mediscreen.abernathyapp.assess.models.DiabeteStatus;
import com.mediscreen.abernathyapp.assess.services.AssessService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.NoSuchElementException;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
@ActiveProfiles("test")
public class AssessControllerTest {

    @Autowired
    private AssessController assessController;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper mapper;
    @MockBean
    private AssessService assessServiceMock;

    @Test
    public void assessByIdReturnValidResponse() throws Exception {
        PatientAssessmentDTO patientAssessmentDTO = new PatientAssessmentDTO(
                "family",
                "given",
                42);

        DiabeteAssessmentDTO diabeteAssessmentDTO = new DiabeteAssessmentDTO(
                patientAssessmentDTO,
                DiabeteStatus.NONE);

        when(assessServiceMock.assessPatientDiabeteStatus(anyString())).thenReturn(diabeteAssessmentDTO);

        MvcResult result = mockMvc.perform(get("/assess/id")
                .param("patId", "1"))
                .andExpect(status().isOk())
                .andReturn();

        String expectedJson = mapper.writeValueAsString(diabeteAssessmentDTO);
        String actualJson = result.getResponse().getContentAsString();

        assertThat(actualJson)
                .isNotBlank()
                .isEqualTo(expectedJson);
    }

    @Test
    public void assessByIdReturnBadRequest() throws Exception {
        PatientAssessmentDTO patientAssessmentDTO = new PatientAssessmentDTO(
                "family",
                "given",
                42);

        DiabeteAssessmentDTO diabeteAssessmentDTO = new DiabeteAssessmentDTO(
                patientAssessmentDTO,
                DiabeteStatus.NONE);

        when(assessServiceMock.assessPatientDiabeteStatus(anyString())).thenThrow(NoSuchElementException.class);

        mockMvc.perform(get("/assess/id")
                .param("patId", "1"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void assessByFamilyGivenReturnValidResponse() throws Exception {
        PatientAssessmentDTO patientAssessmentDTO = new PatientAssessmentDTO(
                "family",
                "given",
                42);

        DiabeteAssessmentDTO diabeteAssessmentDTO = new DiabeteAssessmentDTO(
                patientAssessmentDTO,
                DiabeteStatus.NONE);

        when(assessServiceMock.assessPatientDiabeteStatus(anyString(), anyString())).thenReturn(diabeteAssessmentDTO);

        MvcResult result = mockMvc.perform(get("/assess/familyGiven")
                .param("familyName", "family")
                .param("givenName", "given"))
                .andExpect(status().isOk())
                .andReturn();

        String expectedJson = mapper.writeValueAsString(diabeteAssessmentDTO);

        String actualJson = result.getResponse().getContentAsString();

        assertThat(actualJson)
                .isNotBlank()
                .isEqualTo(expectedJson);
    }

    @Test
    public void assessByFamilyGivenReturnBadRequest() throws Exception {
        PatientAssessmentDTO patientAssessmentDTO = new PatientAssessmentDTO(
                "family",
                "given",
                42);

        DiabeteAssessmentDTO diabeteAssessmentDTO = new DiabeteAssessmentDTO(
                patientAssessmentDTO,
                DiabeteStatus.NONE);

        when(assessServiceMock.assessPatientDiabeteStatus(anyString(), anyString())).thenThrow(NoSuchElementException.class);

        mockMvc.perform(get("/assess/id")
                .param("familyName", "family")
                .param("givenName", "given"))
                .andExpect(status().isBadRequest());
    }
}
