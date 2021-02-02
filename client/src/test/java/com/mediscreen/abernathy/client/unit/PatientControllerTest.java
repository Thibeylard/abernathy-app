package com.mediscreen.abernathy.client.unit;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mediscreen.abernathy.client.patient.proxies.AppPatientProxy;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@AutoConfigureMockMvc
@SpringBootTest
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class PatientControllerTest {

    @Autowired
    ObjectMapper objectMapper;
    @MockBean
    AppPatientProxy appPatientProxy;
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void gettingHomeIsOk() throws Exception {
/*
        // No patient available in database
        List<Patient> noPatients = Lists.emptyList();

        when(appPatientProxy.getAllPatients()).thenReturn(noPatients);

        mockMvc.perform(get("/"))
                .andExpect(view().name("home"))
                .andExpect(model().attributeExists("allPatients"))
                .andExpect(model().attribute("allPatients", is(noPatients)));

        // Two patients available in database
        List<Patient> patients = new ArrayList<>();
        patients.add(new Patient(
                "TestFamily",
                "TestGiven",
                new SimpleDateFormat("yyyy-MM-dd").parse("1854-02-27"),
                "M",
                "1st Oakland St",
                "000-111-222"
        ));
        patients.add(new Patient(
                "TestFamily2",
                "TestGiven2",
                new SimpleDateFormat("yyyy-MM-dd").parse("1854-03-30"),
                "F",
                "2st Oakland St",
                "000-111-223"
        ));

        when(appPatientProxy.getAllPatients()).thenReturn(patients);

        mockMvc.perform(get("/"))
                .andExpect(view().name("home"))
                .andExpect(model().attributeExists("allPatients"))
                .andExpect(model().attribute("allPatients", is(patients)));*/
    }
/*
    @Test
    public void gettingPatientIsOk() throws Exception {

        // Two patients available in database
        List<Patient> patients = new ArrayList<>();
        patients.add(new Patient(
                "TestFamily",
                "TestGiven",
                new SimpleDateFormat("yyyy-MM-dd").parse("1854-02-27"),
                "M",
                "1st Oakland St",
                "000-111-222"
        ));

        patients.add(new Patient(
                "TestFamily2",
                "TestGiven2",
                new SimpleDateFormat("yyyy-MM-dd").parse("1854-03-30"),
                "F",
                "2st Oakland St",
                "000-111-223"
        ));

        patients.get(0).setId("1");
        patients.get(1).setId("2");

        when(appPatientProxy.getPatient("1")).thenReturn(patients.get(0));

        mockMvc.perform(get("/patient/1"))
                .andExpect(view().name("patientForm"))
                .andExpect(model().attributeExists("patient"))
                .andExpect(model().attribute("patient", is(patients.get(0))));

        when(appPatientProxy.getPatient("3")).thenReturn(null);

        mockMvc.perform(get("/patient/3"))
                .andExpect(view().name("home"))
                .andExpect(model().attributeExists("patientNotFound"))
                .andExpect(model().attribute("patientNotFound", is(true)));
    }

    @Test
    public void addingPatientIsOk() throws Exception {

        // Two patients available in database
        List<Patient> patients = new ArrayList<>();
        patients.add(new Patient(
                "TestFamily",
                "TestGiven",
                new SimpleDateFormat("yyyy-MM-dd").parse("1854-02-27"),
                "M",
                "1st Oakland St",
                "000-111-222"
        ));

        patients.add(new Patient(
                "TestFamily2",
                "TestGiven2",
                new SimpleDateFormat("yyyy-MM-dd").parse("1854-03-30"),
                "F",
                "2st Oakland St",
                "000-111-223"
        ));

        // Valid PatientDTO
        PatientDTO patientToAdd = new PatientDTO(
                "TestFamily3",
                "TestGiven3",
                "1894-09-10",
                "F",
                "3st Oakland St",
                "030-111-224"
        );

        Patient patientAdded = new Patient(patientToAdd);
        patientAdded.setId("3");

        patients.get(0).setId("1");
        patients.get(1).setId("2");

        when(appPatientProxy.addPatient(patientToAdd)).thenReturn(patientAdded);

        mockMvc.perform(post("/patient")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(patientToAdd)))
                .andExpect(view().name("home"))
                .andExpect(model().attributeExists("patientAdded"))
                .andExpect(model().attribute("patientAdded", true));

        // Invalid PatientDTO
        patientToAdd = new PatientDTO(
                "TestFamily3",
                "TestGiven3",
                "10/09/1994", // Date format is wrong, must be yyyy-MM-dd
                "F",
                "3st Oakland St",
                "030-111-224"
        );

        mockMvc.perform(post("/patient")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(patientToAdd)))
                .andExpect(view().name("patientForm"))
                .andExpect(model().hasErrors());
    }*/

}
