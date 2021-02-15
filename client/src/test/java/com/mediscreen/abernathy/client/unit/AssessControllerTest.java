package com.mediscreen.abernathy.client.unit;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mediscreen.abernathy.client.dtos.DiabeteAssessmentDTO;
import com.mediscreen.abernathy.client.dtos.PatientAssessmentDTO;
import com.mediscreen.abernathy.client.proxies.AppProxy;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.stringContainsInOrder;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class AssessControllerTest {

    @Autowired
    ObjectMapper objectMapper;
    @MockBean
    AppProxy appProxy;
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void assessByIdIsOk() throws Exception {

        mockMvc.perform(get("/assess/id"))
                .andExpect(status().isBadRequest());

        PatientAssessmentDTO patientAssessmentDTO =
                new PatientAssessmentDTO(
                        "family1",
                        "given1",
                        25);
        DiabeteAssessmentDTO diabeteAssessmentDTO =
                new DiabeteAssessmentDTO(
                        patientAssessmentDTO,
                        "Borderline");

        // Assess works case
        ResponseEntity<String> diabeteAssessment =
                ResponseEntity.ok(objectMapper.writeValueAsString(diabeteAssessmentDTO));

        when(appProxy.getDiabeteAssessmentByPatId(
                anyString()
        )).thenReturn(diabeteAssessment);

        mockMvc.perform(get("/assess/id")
                .param("patId", "2"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/patient/list"))
                .andExpect(flash().attributeExists("patientAssessment"))
                .andExpect(flash().attribute("patientAssessment", stringContainsInOrder(diabeteAssessmentDTO.getAssessment())));

        // Assess does not work case

        diabeteAssessment =
                new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
        when(appProxy.getDiabeteAssessmentByPatId(
                anyString()
        )).thenReturn(diabeteAssessment);

        mockMvc.perform(get("/assess/id")
                .param("patId", "2"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/patient/list"))
                .andExpect(flash().attributeExists("assessmentFailed"))
                .andExpect(flash().attribute("assessmentFailed", is(true)));
    }

    @Test
    public void assessByNameIsOk() throws Exception {

        mockMvc.perform(get("/assess/familyGiven"))
                .andExpect(status().isBadRequest());


        mockMvc.perform(get("/assess/familyGiven")
                .param("familyName", "family1"))
                .andExpect(status().isBadRequest());


        mockMvc.perform(get("/assess/familyGiven")
                .param("givenName", "given1"))
                .andExpect(status().isBadRequest());

        PatientAssessmentDTO patientAssessmentDTO =
                new PatientAssessmentDTO(
                        "family1",
                        "given1",
                        25);
        DiabeteAssessmentDTO diabeteAssessmentDTO =
                new DiabeteAssessmentDTO(
                        patientAssessmentDTO,
                        "Borderline");

        // Assess works case
        ResponseEntity<String> diabeteAssessment =
                ResponseEntity.ok(objectMapper.writeValueAsString(diabeteAssessmentDTO));

        when(appProxy.getDiabeteAssessmentByName(
                anyString(),
                anyString()
        )).thenReturn(diabeteAssessment);

        mockMvc.perform(get("/assess/familyGiven")
                .param("familyName", "family1")
                .param("givenName", "given1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/patient/list"))
                .andExpect(flash().attributeExists("patientAssessment"))
                .andExpect(flash().attribute("patientAssessment", stringContainsInOrder(diabeteAssessmentDTO.getAssessment())));

        // Assess does not work case

        diabeteAssessment =
                new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);

        when(appProxy.getDiabeteAssessmentByName(
                anyString(),
                anyString()
        )).thenReturn(diabeteAssessment);

        mockMvc.perform(get("/assess/familyGiven")
                .param("familyName", "family1")
                .param("givenName", "given1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/patient/list"))
                .andExpect(flash().attributeExists("assessmentFailed"))
                .andExpect(flash().attribute("assessmentFailed", is(true)));
    }
}
