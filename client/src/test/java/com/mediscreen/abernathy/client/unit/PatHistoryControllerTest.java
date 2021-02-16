package com.mediscreen.abernathy.client.unit;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mediscreen.abernathy.client.dtos.PatHistoryDTO;
import com.mediscreen.abernathy.client.proxies.AppProxy;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class PatHistoryControllerTest {

    @Autowired
    ObjectMapper objectMapper;
    @MockBean
    AppProxy appProxy;
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void addingPatHistoryIsOk() throws Exception {

        mockMvc.perform(get("/patHistory/add"))
                .andExpect(status().isBadRequest());

        mockMvc.perform(get("/patHistory/add")
                .param("patientId", "2"))
                .andExpect(view().name("patHistory/add"))
                .andExpect(model().attributeExists("patHistoryToAdd"));

        // Valid PatientDTO
        PatHistoryDTO patHistoryToAdd = new PatHistoryDTO(
                "1",
                "Note content"
        );


        Link patientLinks = Link.of("{\"href\":\"/patHistory/1/uri\"}");

        EntityModel<PatHistoryDTO> patHistoryAdded = EntityModel.of(
                patHistoryToAdd,
                patientLinks);

        when(appProxy.addPatHistory(
                patHistoryToAdd.getPatientId(),
                patHistoryToAdd.getContent()
        )).thenReturn(patHistoryAdded);


        mockMvc.perform(post("/patHistory/add")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .content(EntityUtils.toString(new UrlEncodedFormEntity(Arrays.asList(
                        new BasicNameValuePair("patientId", patHistoryToAdd.getPatientId()),
                        new BasicNameValuePair("content", patHistoryToAdd.getContent())
                )))))
                .andExpect(redirectedUrl("/patient/get?id=1"))
                .andExpect(flash().attributeExists("patHistoryAdded"))
                .andExpect(flash().attribute("patHistoryAdded", true));

        // Invalid PatHistoryDTO
        patHistoryToAdd = new PatHistoryDTO(
                "1",
                "" // empty content
        );


        mockMvc.perform(post("/patHistory/add")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .content(EntityUtils.toString(new UrlEncodedFormEntity(Arrays.asList(
                        new BasicNameValuePair("patientId", patHistoryToAdd.getPatientId()),
                        new BasicNameValuePair("content", patHistoryToAdd.getContent())
                )))))
                .andExpect(view().name("patHistory/add"))
                .andExpect(model().hasErrors());


        // Invalid PatHistoryDTO
        patHistoryToAdd = new PatHistoryDTO(
                "", // empty patient Id
                "Ceci est une note"
        );

        mockMvc.perform(post("/patHistory/add")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .content(EntityUtils.toString(new UrlEncodedFormEntity(Arrays.asList(
                        new BasicNameValuePair("patientId", patHistoryToAdd.getPatientId()),
                        new BasicNameValuePair("content", patHistoryToAdd.getContent())
                )))))
                .andExpect(view().name("patHistory/add"))
                .andExpect(model().hasErrors());
    }

    @Test
    public void updatingPatHistoryIsOk() throws Exception {

        mockMvc.perform(get("/patHistory/update"))
                .andExpect(status().isBadRequest());

        mockMvc.perform(get("/patHistory/update")
                .param("id", "5"))
                .andExpect(redirectedUrl("/patient/list"))
                .andExpect(flash().attributeExists("patHistoryNotFound"))
                .andExpect(flash().attribute("patHistoryNotFound", true));

        // Valid PatientDTO
        PatHistoryDTO patHistoryToUpdate = new PatHistoryDTO(
                "2",
                "3",
                "Note content"
        );


        Link patientLinks = Link.of("{\"href\":\"/patHistory/2/uri\"}");

        EntityModel<PatHistoryDTO> patHistoryUpdated = EntityModel.of(
                patHistoryToUpdate,
                patientLinks);

        when(appProxy.updatePatHistory(
                patHistoryToUpdate.getId(),
                patHistoryToUpdate.getPatientId(),
                patHistoryToUpdate.getContent()
        )).thenReturn(patHistoryUpdated);

        when(appProxy.getPatHistory(
                patHistoryToUpdate.getId()
        )).thenReturn(patHistoryUpdated);

        mockMvc.perform(get("/patHistory/update")
                .param("id", "2"))
                .andExpect(view().name("patHistory/update"))
                .andExpect(model().attributeExists("patHistoryToUpdate"));


        mockMvc.perform(post("/patHistory/update")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .content(EntityUtils.toString(new UrlEncodedFormEntity(Arrays.asList(
                        new BasicNameValuePair("id", patHistoryToUpdate.getId()),
                        new BasicNameValuePair("patientId", patHistoryToUpdate.getPatientId()),
                        new BasicNameValuePair("content", patHistoryToUpdate.getContent())
                )))))
                .andExpect(redirectedUrl("/patient/get?id=3"))
                .andExpect(flash().attributeExists("patHistoryUpdated"))
                .andExpect(flash().attribute("patHistoryUpdated", true));

        // Invalid PatHistoryDTO
        patHistoryToUpdate = new PatHistoryDTO(
                "2",
                "1",
                "" // empty content
        );


        mockMvc.perform(post("/patHistory/update")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .content(EntityUtils.toString(new UrlEncodedFormEntity(Arrays.asList(
                        new BasicNameValuePair("id", patHistoryToUpdate.getId()),
                        new BasicNameValuePair("patientId", patHistoryToUpdate.getPatientId()),
                        new BasicNameValuePair("content", patHistoryToUpdate.getContent())
                )))))
                .andExpect(view().name("patHistory/update"))
                .andExpect(model().hasErrors());


        // Invalid PatHistoryDTO
        patHistoryToUpdate = new PatHistoryDTO(
                "2",
                "", // empty patientId
                "Ceci est une note"
        );


        mockMvc.perform(post("/patHistory/update")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .content(EntityUtils.toString(new UrlEncodedFormEntity(Arrays.asList(
                        new BasicNameValuePair("id", patHistoryToUpdate.getId()),
                        new BasicNameValuePair("patientId", patHistoryToUpdate.getPatientId()),
                        new BasicNameValuePair("content", patHistoryToUpdate.getContent())
                )))))
                .andExpect(view().name("patHistory/update"))
                .andExpect(model().hasErrors());

        // Invalid PatHistoryDTO
        patHistoryToUpdate = new PatHistoryDTO(
                "", // empty id
                "3",
                "Ceci est une note"
        );


        mockMvc.perform(post("/patHistory/update")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .content(EntityUtils.toString(new UrlEncodedFormEntity(Arrays.asList(
                        new BasicNameValuePair("id", patHistoryToUpdate.getId()),
                        new BasicNameValuePair("patientId", patHistoryToUpdate.getPatientId()),
                        new BasicNameValuePair("content", patHistoryToUpdate.getContent())
                )))))
                .andExpect(view().name("patHistory/update"))
                .andExpect(model().hasErrors());
    }
}
