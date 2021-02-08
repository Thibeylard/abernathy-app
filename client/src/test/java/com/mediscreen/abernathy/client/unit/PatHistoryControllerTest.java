package com.mediscreen.abernathy.client.unit;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mediscreen.abernathy.client.proxies.AppPatHistoryProxy;
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
public class PatHistoryControllerTest {

    @Autowired
    ObjectMapper objectMapper;
    @MockBean
    AppPatHistoryProxy appPatHistoryProxy;
    @Autowired
    private MockMvc mockMvc;

    // TODO implementing addingPatHistoryIsOk()
    @Test
    public void addingPatHistoryIsOk() throws Exception {
/*
        mockMvc.perform(get("/patient/add"))
                .andExpect(view().name("patient/add"));

        // Valid PatientDTO
        PatientDTO patientToAdd = new PatientDTO(
                "TestFamily3",
                "TestGiven3",
                "1894-09-10",
                "F",
                "3st Oakland St",
                "030-111-224"
        );


        Link patientLinks = Link.of("{\"href\":\"/patient/3/uri\"}");

        EntityModel<PatientDTO> patientAdded = EntityModel.of(
                patientToAdd,
                patientLinks);

        when(appPatHistoryProxy.addPatient(
                patientToAdd.getFamily(),
                patientToAdd.getGiven(),
                patientToAdd.getDob(),
                patientToAdd.getSex(),
                patientToAdd.getAddress(),
                patientToAdd.getPhone()
        )).thenReturn(patientAdded);


        mockMvc.perform(post("/patient/add")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .content(EntityUtils.toString(new UrlEncodedFormEntity(Arrays.asList(
                        new BasicNameValuePair("family", patientToAdd.getFamily()),
                        new BasicNameValuePair("given", patientToAdd.getGiven()),
                        new BasicNameValuePair("dob", patientToAdd.getDob()),
                        new BasicNameValuePair("sex", patientToAdd.getSex()),
                        new BasicNameValuePair("address", patientToAdd.getAddress()),
                        new BasicNameValuePair("phone", patientToAdd.getPhone())
                )))))
//                .andExpect(view().name("patient/list"))
                .andExpect(redirectedUrl("/patient/list"))
                .andExpect(flash().attributeExists("patientAdded"))
                .andExpect(flash().attribute("patientAdded", true));

        // Invalid PatientDTO
        patientToAdd = new PatientDTO(
                "TestFamily3",
                "TestGiven3",
                "10/09/1994", // Date format is wrong, must be yyyy-MM-dd
                "F",
                "3st Oakland St",
                "030-111-224"
        );

        mockMvc.perform(post("/patient/add")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .content(EntityUtils.toString(new UrlEncodedFormEntity(Arrays.asList(
                        new BasicNameValuePair("family", patientToAdd.getFamily()),
                        new BasicNameValuePair("given", patientToAdd.getGiven()),
                        new BasicNameValuePair("dob", patientToAdd.getDob()),
                        new BasicNameValuePair("sex", patientToAdd.getSex()),
                        new BasicNameValuePair("address", patientToAdd.getAddress()),
                        new BasicNameValuePair("phone", patientToAdd.getPhone())
                )))))
                .andExpect(view().name("patient/add"))
                .andExpect(model().hasErrors());

        // Invalid PatientDTO
        patientToAdd = new PatientDTO(
                "", // Blank family
                "TestGiven3",
                "1994-09-10",
                "F",
                "3st Oakland St",
                "030-111-224"
        );

        mockMvc.perform(post("/patient/add")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .content(EntityUtils.toString(new UrlEncodedFormEntity(Arrays.asList(
                        new BasicNameValuePair("family", patientToAdd.getFamily()),
                        new BasicNameValuePair("given", patientToAdd.getGiven()),
                        new BasicNameValuePair("dob", patientToAdd.getDob()),
                        new BasicNameValuePair("sex", patientToAdd.getSex()),
                        new BasicNameValuePair("address", patientToAdd.getAddress()),
                        new BasicNameValuePair("phone", patientToAdd.getPhone())
                )))))
                .andExpect(view().name("patient/add"))
                .andExpect(model().hasErrors());

        // Invalid PatientDTO
        patientToAdd = new PatientDTO(
                "TestFamily3",
                "TestGiven3",
                "1994-09-10",
                "F",
                "3st Oakland St",
                "06 74 58 47 45" // Invalid phone format
        );

        mockMvc.perform(post("/patient/add")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .content(EntityUtils.toString(new UrlEncodedFormEntity(Arrays.asList(
                        new BasicNameValuePair("family", patientToAdd.getFamily()),
                        new BasicNameValuePair("given", patientToAdd.getGiven()),
                        new BasicNameValuePair("dob", patientToAdd.getDob()),
                        new BasicNameValuePair("sex", patientToAdd.getSex()),
                        new BasicNameValuePair("address", patientToAdd.getAddress()),
                        new BasicNameValuePair("phone", patientToAdd.getPhone())
                )))))
                .andExpect(view().name("patient/add"))
                .andExpect(model().hasErrors());*/
    }

    // TODO implementing updatingPatHistoryIsOk()
    @Test
    public void updatingPatHistoryIsOk() throws Exception {
/*
        // Valid PatientDTO
        PatientDTO patientDtoToUpdate = new PatientDTO(
                "3",
                "TestFamily3",
                "TestGiven3",
                "1894-09-10",
                "F",
                "3st Oakland St",
                "030-111-224"
        );

        Link patientLinks = Link.of("{\"href\":\"/patient/3/uri\"}");

        EntityModel<PatientDTO> patientItemToUpdate = EntityModel.of(
                patientDtoToUpdate,
                patientLinks);

        doReturn(patientItemToUpdate).when(appPatHistoryProxy).getPatient(any(String.class));

        mockMvc.perform(get("/patient/update")
                .param("id", "3"))
                .andExpect(view().name("patient/update"))
                .andExpect(model().attributeExists("patientToUpdate"))
                .andExpect(model().attribute("patientToUpdate", is(patientDtoToUpdate)));

        when(appPatHistoryProxy.updatePatient(
                patientDtoToUpdate.getId(),
                patientDtoToUpdate.getFamily(),
                patientDtoToUpdate.getGiven(),
                patientDtoToUpdate.getDob(),
                patientDtoToUpdate.getSex(),
                patientDtoToUpdate.getAddress(),
                patientDtoToUpdate.getPhone()
        )).thenReturn(patientItemToUpdate);

        mockMvc.perform(post("/patient/update")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .content(EntityUtils.toString(new UrlEncodedFormEntity(Arrays.asList(
                        new BasicNameValuePair("id", patientDtoToUpdate.getId()),
                        new BasicNameValuePair("family", patientDtoToUpdate.getFamily()),
                        new BasicNameValuePair("given", patientDtoToUpdate.getGiven()),
                        new BasicNameValuePair("dob", patientDtoToUpdate.getDob()),
                        new BasicNameValuePair("sex", patientDtoToUpdate.getSex()),
                        new BasicNameValuePair("address", patientDtoToUpdate.getAddress()),
                        new BasicNameValuePair("phone", patientDtoToUpdate.getPhone())
                )))))
                .andExpect(redirectedUrl("/patient/list"))
                .andExpect(flash().attributeExists("patientUpdated"))
                .andExpect(flash().attribute("patientUpdated", true));

        // Invalid PatientDTO
        patientDtoToUpdate = new PatientDTO(
                "3",
                "TestFamily3",
                "TestGiven3",
                "10/09/1994", // Date format is wrong, must be yyyy-MM-dd
                "F",
                "3st Oakland St",
                "030-111-224"
        );

        mockMvc.perform(post("/patient/update")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .content(EntityUtils.toString(new UrlEncodedFormEntity(Arrays.asList(
                        new BasicNameValuePair("id", patientDtoToUpdate.getId()),
                        new BasicNameValuePair("family", patientDtoToUpdate.getFamily()),
                        new BasicNameValuePair("given", patientDtoToUpdate.getGiven()),
                        new BasicNameValuePair("dob", patientDtoToUpdate.getDob()),
                        new BasicNameValuePair("sex", patientDtoToUpdate.getSex()),
                        new BasicNameValuePair("address", patientDtoToUpdate.getAddress()),
                        new BasicNameValuePair("phone", patientDtoToUpdate.getPhone())
                )))))
                .andExpect(view().name("patient/update"))
                .andExpect(model().hasErrors());

        // Invalid PatientDTO
        patientDtoToUpdate = new PatientDTO(
                "3",
                "", // Blank family
                "TestGiven3",
                "1994-09-10",
                "F",
                "3st Oakland St",
                "030-111-224"
        );

        mockMvc.perform(post("/patient/update")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .content(EntityUtils.toString(new UrlEncodedFormEntity(Arrays.asList(
                        new BasicNameValuePair("id", patientDtoToUpdate.getId()),
                        new BasicNameValuePair("family", patientDtoToUpdate.getFamily()),
                        new BasicNameValuePair("given", patientDtoToUpdate.getGiven()),
                        new BasicNameValuePair("dob", patientDtoToUpdate.getDob()),
                        new BasicNameValuePair("sex", patientDtoToUpdate.getSex()),
                        new BasicNameValuePair("address", patientDtoToUpdate.getAddress()),
                        new BasicNameValuePair("phone", patientDtoToUpdate.getPhone())
                )))))
                .andExpect(view().name("patient/update"))
                .andExpect(model().hasErrors());

        // Invalid PatientDTO
        patientDtoToUpdate = new PatientDTO(
                "3",
                "TestFamily3",
                "TestGiven3",
                "1994-09-10",
                "F",
                "3st Oakland St",
                "06 74 58 47 45" // Invalid phone format
        );

        mockMvc.perform(post("/patient/update")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .content(EntityUtils.toString(new UrlEncodedFormEntity(Arrays.asList(
                        new BasicNameValuePair("id", patientDtoToUpdate.getId()),
                        new BasicNameValuePair("family", patientDtoToUpdate.getFamily()),
                        new BasicNameValuePair("given", patientDtoToUpdate.getGiven()),
                        new BasicNameValuePair("dob", patientDtoToUpdate.getDob()),
                        new BasicNameValuePair("sex", patientDtoToUpdate.getSex()),
                        new BasicNameValuePair("address", patientDtoToUpdate.getAddress()),
                        new BasicNameValuePair("phone", patientDtoToUpdate.getPhone())
                )))))
                .andExpect(view().name("patient/update"))
                .andExpect(model().hasErrors());

        // Invalid PatientDTO
        patientDtoToUpdate = new PatientDTO(
                // No ID
                "TestFamily3",
                "TestGiven3",
                "1994-09-10",
                "F",
                "3st Oakland St",
                "06 74 58 47 45"
        );

        mockMvc.perform(post("/patient/update")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .content(EntityUtils.toString(new UrlEncodedFormEntity(Arrays.asList(
                        new BasicNameValuePair("id", patientDtoToUpdate.getId()),
                        new BasicNameValuePair("family", patientDtoToUpdate.getFamily()),
                        new BasicNameValuePair("given", patientDtoToUpdate.getGiven()),
                        new BasicNameValuePair("dob", patientDtoToUpdate.getDob()),
                        new BasicNameValuePair("sex", patientDtoToUpdate.getSex()),
                        new BasicNameValuePair("address", patientDtoToUpdate.getAddress()),
                        new BasicNameValuePair("phone", patientDtoToUpdate.getPhone())
                )))))
                .andExpect(view().name("patient/update"))
                .andExpect(model().hasErrors());*/
    }
}
