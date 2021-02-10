package com.mediscreen.abernathyapp.patient.unit;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.entity.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
@ActiveProfiles("test")
public class PatientRestEntityTests {

    @Autowired
    private MockMvc mockMvc;
    private Writer writer;
    private JsonGenerator jsonGenerator;

    @BeforeEach
    public void openWriter() throws IOException {
        this.writer = new StringWriter();
        this.jsonGenerator = new JsonFactory().createGenerator(writer);
    }

    @Test
    public void validPatient() throws Exception {
        // Valid patient
        jsonGenerator.writeStartObject();
        jsonGenerator.writeStringField("family", "FamilyValue");
        jsonGenerator.writeStringField("given", "GivenValue");
        jsonGenerator.writeStringField("dob", "1987-12-30");
        jsonGenerator.writeStringField("sex", "M");
        jsonGenerator.writeStringField("address", "AddressValue");
        jsonGenerator.writeStringField("phone", "000-000-0000");
        jsonGenerator.writeEndObject();
        jsonGenerator.close();

        mockMvc.perform(post("/patient")
                .contentType(ContentType.APPLICATION_JSON.toString())
                .content(writer.toString()))
                .andExpect(status().isCreated());
    }

    @Test
    public void nullPatient() throws Exception {
        // Invalid patient
        jsonGenerator.writeStartObject();
        jsonGenerator.writeStringField("family", null);
        jsonGenerator.writeStringField("given", null);
        jsonGenerator.writeStringField("dob", null);
        jsonGenerator.writeStringField("sex", null);
        jsonGenerator.writeStringField("address", null);
        jsonGenerator.writeStringField("phone", null);
        jsonGenerator.writeEndObject();
        jsonGenerator.close();

        mockMvc.perform(post("/patient")
                .contentType(ContentType.APPLICATION_JSON.toString())
                .content(writer.toString()))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void invalidFamilyPatient() throws Exception {
        // Invalid patient
        jsonGenerator.writeStartObject();
        jsonGenerator.writeStringField("family", "");
        jsonGenerator.writeStringField("given", "GivenValue");
        jsonGenerator.writeStringField("dob", "1987-12-30");
        jsonGenerator.writeStringField("sex", "M");
        jsonGenerator.writeStringField("address", "AddressValue");
        jsonGenerator.writeStringField("phone", "000-000-0000");
        jsonGenerator.writeEndObject();
        jsonGenerator.close();

        mockMvc.perform(post("/patient")
                .contentType(ContentType.APPLICATION_JSON.toString())
                .content(writer.toString()))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void invalidGivenPatient() throws Exception {
        // Invalid patient
        jsonGenerator.writeStartObject();
        jsonGenerator.writeStringField("family", "FamilyValue");
        jsonGenerator.writeStringField("given", "");
        jsonGenerator.writeStringField("dob", "1987-12-30");
        jsonGenerator.writeStringField("sex", "M");
        jsonGenerator.writeStringField("address", "AddressValue");
        jsonGenerator.writeStringField("phone", "000-000-0000");
        jsonGenerator.writeEndObject();
        jsonGenerator.close();

        mockMvc.perform(post("/patient")
                .contentType(ContentType.APPLICATION_JSON.toString())
                .content(writer.toString()))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void invalidDatePatient() throws Exception {
        // Invalid patient
        jsonGenerator.writeStartObject();
        jsonGenerator.writeStringField("family", "FamilyValue");
        jsonGenerator.writeStringField("given", "GivenValue");
        jsonGenerator.writeStringField("dob", "");
        jsonGenerator.writeStringField("sex", "M");
        jsonGenerator.writeStringField("address", "AddressValue");
        jsonGenerator.writeStringField("phone", "000-000-0000");
        jsonGenerator.writeEndObject();
        jsonGenerator.close();

        mockMvc.perform(post("/patient")
                .contentType(ContentType.APPLICATION_JSON.toString())
                .content(writer.toString()))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void invalidSexPatient() throws Exception {
        // Invalid patient
        jsonGenerator.writeStartObject();
        jsonGenerator.writeStringField("family", "FamilyValue");
        jsonGenerator.writeStringField("given", "GivenValue");
        jsonGenerator.writeStringField("dob", "1987-12-30");
        jsonGenerator.writeStringField("sex", "Female");
        jsonGenerator.writeStringField("address", "AddressValue");
        jsonGenerator.writeStringField("phone", "000-000-0000");
        jsonGenerator.writeEndObject();
        jsonGenerator.close();

        mockMvc.perform(post("/patient")
                .contentType(ContentType.APPLICATION_JSON.toString())
                .content(writer.toString()))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void invalidAddressPatient() throws Exception {
        // Invalid patient
        jsonGenerator.writeStartObject();
        jsonGenerator.writeStringField("family", "FamilyValue");
        jsonGenerator.writeStringField("given", "GivenValue");
        jsonGenerator.writeStringField("dob", "1987-12-30");
        jsonGenerator.writeStringField("sex", "M");
        jsonGenerator.writeStringField("address", "");
        jsonGenerator.writeStringField("phone", "000-000-0000");
        jsonGenerator.writeEndObject();
        jsonGenerator.close();

        mockMvc.perform(post("/patient")
                .contentType(ContentType.APPLICATION_JSON.toString())
                .content(writer.toString()))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void invalidPhonePatient() throws Exception {
        // Invalid patient
        jsonGenerator.writeStartObject();
        jsonGenerator.writeStringField("family", "FamilyValue");
        jsonGenerator.writeStringField("given", "GivenValue");
        jsonGenerator.writeStringField("dob", "1987-12-30");
        jsonGenerator.writeStringField("sex", "M");
        jsonGenerator.writeStringField("address", "AddressValue");
        jsonGenerator.writeStringField("phone", "00-000-000");
        jsonGenerator.writeEndObject();
        jsonGenerator.close();

        String json = writer.toString();

        mockMvc.perform(post("/patient")
                .contentType(ContentType.APPLICATION_JSON.toString())
                .content(json))
                .andExpect(status().isBadRequest());

        json = json.replace("00-000-000", "415854965");

        mockMvc.perform(post("/patient")
                .contentType(ContentType.APPLICATION_JSON.toString())
                .content(json))
                .andExpect(status().isBadRequest());


        json = json.replace("415854965", "005-985-1k5");

        mockMvc.perform(post("/patient")
                .contentType(ContentType.APPLICATION_JSON.toString())
                .content(json))
                .andExpect(status().isBadRequest());

        json = json.replace("005-985-1k5", "000-141-005-032");

        mockMvc.perform(post("/patient")
                .contentType(ContentType.APPLICATION_JSON.toString())
                .content(json))
                .andExpect(status().isBadRequest());

        json = json.replace("000-141-005-032", "");

        mockMvc.perform(post("/patient")
                .contentType(ContentType.APPLICATION_JSON.toString())
                .content(json))
                .andExpect(status().isBadRequest());
    }
}
