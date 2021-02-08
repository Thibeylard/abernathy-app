package com.mediscreen.abernathy.client.unit;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mediscreen.abernathy.client.dtos.PatientDTO;
import com.mediscreen.abernathy.client.proxies.AppProxy;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class PatientControllerTest {

    @Autowired
    ObjectMapper objectMapper;
    @MockBean
    AppProxy appProxy;
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void gettingPatientListIsOk() throws Exception {

        // No patient available in database
        PagedModel<EntityModel<PatientDTO>> collectionResource = PagedModel.of(
                Lists.emptyList(),
                new PagedModel.PageMetadata(20, 0, 0, 1),
                Link.of("/patient/list/link", "self"));

        when(appProxy.getAllPatients(any(Integer.class), any(Integer.class))).thenReturn(collectionResource);

        // TODO improve test by checking links and page model attributes
        mockMvc.perform(get("/patient/list"))
                .andExpect(view().name("patient/list"))
                .andExpect(model().attributeExists("allPatients"));
//                .andExpect(model().attribute("allPatients", collectionResource.getContent()));

        // Two patients available in database
        List<EntityModel<PatientDTO>> patients = new ArrayList<>();

        patients.add(EntityModel.of(
                new PatientDTO(
                        "1",
                        "TestFamily",
                        "TestGiven",
                        "1854-02-27",
                        "M",
                        "1st Oakland St",
                        "000-111-222"),
                Link.of("{\"href\":\"/patient/1/uri\"}", "self")));

        patients.add(EntityModel.of(
                new PatientDTO(
                        "2",
                        "TestFamily2",
                        "TestGiven2",
                        "1854-03-30",
                        "F",
                        "2st Oakland St",
                        "000-111-223"),
                Link.of("{\"href\":\"/patient/2/uri\"}", "self")));

        collectionResource = PagedModel.of(
                patients,
                new PagedModel.PageMetadata(20, 0, 2, 1),
                Link.of("/patient/list/link", "self"));

        when(appProxy.getAllPatients(any(Integer.class), any(Integer.class))).thenReturn(collectionResource);

        mockMvc.perform(get("/patient/list"))
                .andExpect(view().name("patient/list"))
                .andExpect(model().attributeExists("allPatients"))
                .andExpect(model().attribute("allPatients", hasSize(2)))
                .andExpect(model().attribute("allPatients", hasItems(is(patients.get(0)), is(patients.get(1)))));
    }

    @Test
    public void gettingPatientIsOk() throws Exception {

        // Two patients available in database
        List<EntityModel<PatientDTO>> patients = new ArrayList<>();
        Link patientLinks = Link.of("{\"href\":\"/patient/1/uri\"}");
        patients.add(EntityModel.of(
                new PatientDTO(
                        "TestFamily",
                        "TestGiven",
                        "1854-02-27",
                        "M",
                        "1st Oakland St",
                        "000-111-222"),
                patientLinks));
        patientLinks = Link.of("{\"href\":\"/patient/2/uri\"}");
        patients.add(EntityModel.of(
                new PatientDTO(
                        "TestFamily2",
                        "TestGiven2",
                        "1854-03-30",
                        "F",
                        "2st Oakland St",
                        "000-111-223"),
                patientLinks));

        when(appProxy.getPatient(any(String.class))).thenReturn(patients.get(0));

        mockMvc.perform(get("/patient/get")
                .param("id", "1"))
                .andExpect(view().name("patient/details"))
                .andExpect(model().attributeExists("patientResource"))
                .andExpect(model().attribute("patientResource", is(patients.get(0))))
                .andExpect(model().attribute("patientResource", hasProperty("content", is(patients.get(0).getContent()))))
                .andExpect(model().attribute("patientResource", hasProperty("links", is(patients.get(0).getLinks()))));

        when(appProxy.getPatient(any(String.class))).thenReturn(null);

        mockMvc.perform(get("/patient/get")
                .param("id", "3"))
                .andExpect(view().name("patient/list"))
                .andExpect(model().attributeExists("patientNotFound"))
                .andExpect(model().attribute("patientNotFound", true));
    }

    @Test
    public void addingPatientIsOk() throws Exception {

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

        when(appProxy.addPatient(
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
                .andExpect(model().hasErrors());
    }

    @Test
    public void updatingPatientIsOk() throws Exception {

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

        doReturn(patientItemToUpdate).when(appProxy).getPatient(any(String.class));

        mockMvc.perform(get("/patient/update")
                .param("id", "3"))
                .andExpect(view().name("patient/update"))
                .andExpect(model().attributeExists("patientToUpdate"))
                .andExpect(model().attribute("patientToUpdate", is(patientDtoToUpdate)));

        when(appProxy.updatePatient(
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
                .andExpect(model().hasErrors());
    }
}
