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

    @Autowired
    private ObjectMapper objectMapper;

    private Writer writer;
    private JsonGenerator jsonGenerator;

    @BeforeEach
    public void openWriter() throws IOException {
        this.writer = new StringWriter();
        this.jsonGenerator = new JsonFactory().createGenerator(writer);
    }

    @Test
    public void validPatient() throws Exception {
        Writer writer = new StringWriter();
        JsonGenerator generator = new JsonFactory().createGenerator(writer);

        // Valid patient
        generator.writeStartObject();
        generator.writeStringField("family", "FamilyValue");
        generator.writeStringField("given", "GivenValue");
        generator.writeStringField("dob", "1987-12-30");
        generator.writeStringField("sex", "M");
        generator.writeStringField("address", "AddressValue");
        generator.writeStringField("phone", "000-000-000");
        generator.writeEndObject();
        generator.close();

        mockMvc.perform(post("/patient")
                .contentType(ContentType.APPLICATION_JSON.toString())
                .content(writer.toString()))
                .andExpect(status().isCreated());
    }

    @Test
    public void nullPatient() throws Exception {
        Writer writer = new StringWriter();
        JsonGenerator generator = new JsonFactory().createGenerator(writer);

        // Valid patient
        generator.writeStartObject();
        generator.writeStringField("family", null);
        generator.writeStringField("given", null);
        generator.writeStringField("dob", null);
        generator.writeStringField("sex", null);
        generator.writeStringField("address", null);
        generator.writeStringField("phone", null);
        generator.writeEndObject();
        generator.close();

        mockMvc.perform(post("/patient")
                .contentType(ContentType.APPLICATION_JSON.toString())
                .content(writer.toString()))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void invalidFamilyPatient() throws Exception {
        Writer writer = new StringWriter();
        JsonGenerator generator = new JsonFactory().createGenerator(writer);

        // Valid patient
        generator.writeStartObject();
        generator.writeStringField("family", "");
        generator.writeStringField("given", "GivenValue");
        generator.writeStringField("dob", "1987-12-30");
        generator.writeStringField("sex", "M");
        generator.writeStringField("address", "AddressValue");
        generator.writeStringField("phone", "000-000-000");
        generator.writeEndObject();
        generator.close();

        mockMvc.perform(post("/patient")
                .contentType(ContentType.APPLICATION_JSON.toString())
                .content(writer.toString()))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void invalidGivenPatient() throws Exception {
        Writer writer = new StringWriter();
        JsonGenerator generator = new JsonFactory().createGenerator(writer);

        // Valid patient
        generator.writeStartObject();
        generator.writeStringField("family", "FamilyValue");
        generator.writeStringField("given", "");
        generator.writeStringField("dob", "1987-12-30");
        generator.writeStringField("sex", "M");
        generator.writeStringField("address", "AddressValue");
        generator.writeStringField("phone", "000-000-000");
        generator.writeEndObject();
        generator.close();

        mockMvc.perform(post("/patient")
                .contentType(ContentType.APPLICATION_JSON.toString())
                .content(writer.toString()))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void invalidDatePatient() throws Exception {
        Writer writer = new StringWriter();
        JsonGenerator generator = new JsonFactory().createGenerator(writer);

        // Valid patient
        generator.writeStartObject();
        generator.writeStringField("family", "FamilyValue");
        generator.writeStringField("given", "GivenValue");
        generator.writeStringField("dob", "");
        generator.writeStringField("sex", "M");
        generator.writeStringField("address", "AddressValue");
        generator.writeStringField("phone", "000-000-000");
        generator.writeEndObject();
        generator.close();

        mockMvc.perform(post("/patient")
                .contentType(ContentType.APPLICATION_JSON.toString())
                .content(writer.toString()))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void invalidSexPatient() throws Exception {
        Writer writer = new StringWriter();
        JsonGenerator generator = new JsonFactory().createGenerator(writer);

        // Valid patient
        generator.writeStartObject();
        generator.writeStringField("family", "FamilyValue");
        generator.writeStringField("given", "GivenValue");
        generator.writeStringField("dob", "1987-12-30");
        generator.writeStringField("sex", "Female");
        generator.writeStringField("address", "AddressValue");
        generator.writeStringField("phone", "000-000-000");
        generator.writeEndObject();
        generator.close();

        mockMvc.perform(post("/patient")
                .contentType(ContentType.APPLICATION_JSON.toString())
                .content(writer.toString()))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void invalidAddressPatient() throws Exception {
        Writer writer = new StringWriter();
        JsonGenerator generator = new JsonFactory().createGenerator(writer);

        // Valid patient
        generator.writeStartObject();
        generator.writeStringField("family", "FamilyValue");
        generator.writeStringField("given", "GivenValue");
        generator.writeStringField("dob", "1987-12-30");
        generator.writeStringField("sex", "M");
        generator.writeStringField("address", "");
        generator.writeStringField("phone", "000-000-000");
        generator.writeEndObject();
        generator.close();

        mockMvc.perform(post("/patient")
                .contentType(ContentType.APPLICATION_JSON.toString())
                .content(writer.toString()))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void invalidPhonePatient() throws Exception {
        Writer writer = new StringWriter();
        JsonGenerator generator = new JsonFactory().createGenerator(writer);

        // Valid patient
        generator.writeStartObject();
        generator.writeStringField("family", "FamilyValue");
        generator.writeStringField("given", "GivenValue");
        generator.writeStringField("dob", "1987-12-30");
        generator.writeStringField("sex", "M");
        generator.writeStringField("address", "AddressValue");
        generator.writeStringField("phone", "00-000-000");
        generator.writeEndObject();
        generator.close();

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
