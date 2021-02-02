package com.mediscreen.abernathy.client.unit;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mediscreen.abernathy.client.patient.dtos.*;
import com.mediscreen.abernathy.client.patient.proxies.AppPatientProxy;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

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
    public void gettingPatientListIsOk() throws Exception {

        // No patient available in database
        PatientCollectionResourceDTO collectionResource = new PatientCollectionResourceDTO(
                Lists.emptyList(),
                new ResourceLinksDTO(objectMapper.createObjectNode()),
                new ResourcePageDTO(0, 0, 0, 0)
        );

        when(appPatientProxy.getAllPatients(null, null)).thenReturn(collectionResource);

        mockMvc.perform(get("/patient/list"))
                .andExpect(view().name("patientList"))
                .andExpect(model().attributeExists("allPatients"))
                .andExpect(model().attribute("allPatients", collectionResource.getPatientItems()));

        // Two patients available in database
        List<PatientItemResourceDTO> patients = new ArrayList<>();
        ResourceLinksDTO patientLinks = new ResourceLinksDTO(objectMapper.createObjectNode());
        patientLinks.getLinks().set("self", objectMapper.readTree("{\"href\":\"/patient/1/uri\"}"));
        patients.add(new PatientItemResourceDTO(
                new PatientDTO(
                        "TestFamily",
                        "TestGiven",
                        "1854-02-27",
                        "M",
                        "1st Oakland St",
                        "000-111-222"),
                patientLinks));
        patientLinks.getLinks().set("self", objectMapper.readTree("{\"href\":\"/patient/2/uri\"}"));
        patients.add(new PatientItemResourceDTO(
                new PatientDTO(
                        "TestFamily2",
                        "TestGiven2",
                        "1854-03-30",
                        "F",
                        "2st Oakland St",
                        "000-111-223"),
                patientLinks));

        collectionResource = new PatientCollectionResourceDTO(
                patients,
                new ResourceLinksDTO(objectMapper.createObjectNode()),
                new ResourcePageDTO(2, 2, 1, 0)
        );

        when(appPatientProxy.getAllPatients(null, null)).thenReturn(collectionResource);

        mockMvc.perform(get("/patient/list"))
                .andExpect(view().name("patientList"))
                .andExpect(model().attributeExists("allPatients"))
                .andExpect(model().attribute("allPatients", patients));
    }

    @Test
    public void gettingPatientIsOk() throws Exception {

        // Two patients available in database
        List<PatientItemResourceDTO> patients = new ArrayList<>();
        ResourceLinksDTO patientLinks = new ResourceLinksDTO(objectMapper.createObjectNode());
        patientLinks.getLinks().set("self", objectMapper.readTree("{\"href\":\"/patient/1/uri\"}"));
        patients.add(new PatientItemResourceDTO(
                new PatientDTO(
                        "TestFamily",
                        "TestGiven",
                        "1854-02-27",
                        "M",
                        "1st Oakland St",
                        "000-111-222"),
                patientLinks));
        patientLinks.getLinks().set("self", objectMapper.readTree("{\"href\":\"/patient/2/uri\"}"));
        patients.add(new PatientItemResourceDTO(
                new PatientDTO(
                        "TestFamily2",
                        "TestGiven2",
                        "1854-03-30",
                        "F",
                        "2st Oakland St",
                        "000-111-223"),
                patientLinks));

        when(appPatientProxy.getPatient("1")).thenReturn(patients.get(0));

        mockMvc.perform(get("/patient/get")
                .param("id", "1"))
                .andExpect(view().name("patientForm"))
                .andExpect(model().attributeExists("item"))
                .andExpect(model().attributeExists("links"))
                .andExpect(model().attribute("item", patients.get(0).getItem()))
                .andExpect(model().attribute("links", patients.get(0).getLinks()));

        when(appPatientProxy.getPatient("3")).thenReturn(null);

        mockMvc.perform(get("/patient/get")
                .param("id", "3"))
                .andExpect(view().name("patientList"))
                .andExpect(model().attributeExists("patientNotFound"))
                .andExpect(model().attribute("patientNotFound", true));
    }
/*
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
