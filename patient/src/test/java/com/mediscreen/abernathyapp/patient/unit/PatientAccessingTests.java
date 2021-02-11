package com.mediscreen.abernathyapp.patient.unit;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mediscreen.abernathyapp.patient.models.Patient;
import com.mediscreen.abernathyapp.patient.models.PatientHealthInfos;
import com.mediscreen.abernathyapp.patient.repositories.PatientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.time.LocalDate;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
@ActiveProfiles("test")
public class PatientAccessingTests {

    @Autowired
    private PatientRepository patientRepository;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper mapper;

    private Patient testPatient;

    @BeforeEach
    public void deleteDatabaseEntities() {
        // Temporary solution
        // TODO EnableTransactionManagement and Rollback did not work. Find out why.
        patientRepository.deleteAll();

        Patient patient = patientRepository.save(new Patient(
                "family",
                "given",
                LocalDate.EPOCH,
                "M",
                "address",
                "000-111-2222"));

        assertThat(patient)
                .isNotNull();

        this.testPatient = patient;
    }

    @Test
    public void accessPatient() throws Exception {

        MvcResult result = mockMvc.perform(get("/patient/" + testPatient.getId()))
                .andExpect(status().isOk())
                .andReturn();

        String jsonBody = result.getResponse().getContentAsString();

        Patient patientFound =
                mapper.convertValue(mapper.readTree(jsonBody), Patient.class);

        assertThat(patientFound)
                .usingRecursiveComparison()
                .isEqualTo(testPatient);
    }

    @Test
    public void accessPatientByFamilyAndGiven() throws Exception {

        MvcResult result = mockMvc.perform(get("/patient/search/withFamilyAndGiven")
                .param("family", testPatient.getFamily())
                .param("given", testPatient.getGiven()))
                .andExpect(status().isOk())
                .andReturn();

        String jsonBody = result.getResponse().getContentAsString();

        Patient patientFound =
                mapper.convertValue(mapper.readTree(jsonBody), Patient.class);

        assertThat(patientFound)
                .usingRecursiveComparison()
                .isEqualTo(testPatient);
    }

    @Test
    public void accessPatientProjection() throws Exception {

        MvcResult result = mockMvc.perform(get("/patient/" + testPatient.getId() + "?projection=healthInfos"))
                .andExpect(status().isOk())
                .andReturn();

        String jsonBody = result.getResponse().getContentAsString();

        PatientHealthInfosImpl patientFound =
                mapper.convertValue(mapper.readTree(jsonBody), PatientHealthInfosImpl.class);

        assertThat(patientFound)
                .usingRecursiveComparison()
                .ignoringFields("phone")
                .ignoringFields("address")
                .isEqualTo(testPatient);
    }

    @Test
    public void accessPatientProjectionByFamilyAndGiven() throws Exception {

        MvcResult result = mockMvc.perform(get("/patient/search/withFamilyAndGiven")
                .param("family", testPatient.getFamily())
                .param("given", testPatient.getGiven()))
                .andExpect(status().isOk())
                .andReturn();

        String jsonBody = result.getResponse().getContentAsString();

        PatientHealthInfosImpl patientFound =
                mapper.convertValue(mapper.readTree(jsonBody), PatientHealthInfosImpl.class);

        assertThat(patientFound)
                .usingRecursiveComparison()
                .ignoringFields("phone")
                .ignoringFields("address")
                .isEqualTo(testPatient);
    }

    private static class PatientHealthInfosImpl implements PatientHealthInfos {
        private String id;
        private String family;
        private String given;
        private LocalDate dob;
        private String sex;

        public PatientHealthInfosImpl() {
        }

        public String getId() {
            return this.id;
        }

        public String getFamily() {
            return family;
        }

        public String getGiven() {
            return given;
        }

        public LocalDate getDob() {
            return dob;
        }

        public String getSex() {
            return sex;
        }

    }

}
