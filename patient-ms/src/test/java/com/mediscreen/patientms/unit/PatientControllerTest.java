package com.mediscreen.patientms.unit;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mediscreen.patientms.models.Patient;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.text.SimpleDateFormat;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
@ExtendWith(SpringExtension.class)
public class PatientControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void addingAndRetrievingPatient() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk());

        Patient addedPatient = new Patient(
                "TestFamily",
                "TestGiven",
                new SimpleDateFormat("yyyy-MM-dd").parse("1854-02-30"),
                "M",
                "1st Oakland St",
                "000-111-222"
        );

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("family", addedPatient.getFamily());
        params.add("given", addedPatient.getGiven());
        params.add("dob", "1854-02-30");
        params.add("sex", addedPatient.getSex());
        params.add("address", addedPatient.getAddress());
        params.add("phone", addedPatient.getPhone());

        // Add new patient

        MvcResult result = mockMvc.perform(
                post("/patient/add")
                        .params(params))
                .andExpect(status().isCreated())
                .andReturn();
        JsonNode response = objectMapper.readTree(result.getResponse().getContentAsString());

        // Patient was added with right values
        assertThat(response.get("family").asText())
                .isEqualTo(addedPatient.getFamily());
        assertThat(response.get("given").asText())
                .isEqualTo(addedPatient.getGiven());
        // TODO Add dob comparison
        assertThat(response.get("sex").asText())
                .isEqualTo(addedPatient.getSex());
        assertThat(response.get("address").asText())
                .isEqualTo(addedPatient.getAddress());
        assertThat(response.get("phone").asText())
                .isEqualTo(addedPatient.getPhone());

        String selfLink = response.get("_links").get("self").get("href").asText();

        // New patient can be accessed to provided link
        mockMvc.perform(
                get(selfLink))
                .andExpect(status().isOk());
    }
}
