package com.mediscreen.abernathyapp.patHistory;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
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
public class PatHistoryRestEntityTests {

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
    public void validPatHistory() throws Exception {

        // Valid patHistory
        jsonGenerator.writeStartObject();
        jsonGenerator.writeStringField("patientId", "5");
        jsonGenerator.writeStringField("content", "Ceci est une note");
        jsonGenerator.writeEndObject();
        jsonGenerator.close();

        mockMvc.perform(post("/patHistory")
                .contentType(ContentType.APPLICATION_JSON.toString())
                .content(writer.toString()))
                .andExpect(status().isCreated());
    }

    @Test
    public void nullPatHistory() throws Exception {

        // Invalid patHistory
        jsonGenerator.writeStartObject();
        jsonGenerator.writeStringField("patientId", null);
        jsonGenerator.writeStringField("content", null);
        jsonGenerator.writeEndObject();
        jsonGenerator.close();

        mockMvc.perform(post("/patHistory")
                .contentType(ContentType.APPLICATION_JSON.toString())
                .content(writer.toString()))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void invalidPatientId() throws Exception {

        // Invalid patHistory
        jsonGenerator.writeStartObject();
        jsonGenerator.writeStringField("patientId", "");
        jsonGenerator.writeStringField("content", "Ceci est une note");
        jsonGenerator.writeEndObject();
        jsonGenerator.close();

        mockMvc.perform(post("/patHistory")
                .contentType(ContentType.APPLICATION_JSON.toString())
                .content(writer.toString()))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void invalidContent() throws Exception {

        // Invalid patHistory
        jsonGenerator.writeStartObject();
        jsonGenerator.writeStringField("patientId", "4");
        jsonGenerator.writeStringField("content", "");
        jsonGenerator.writeEndObject();
        jsonGenerator.close();

        mockMvc.perform(post("/patHistory")
                .contentType(ContentType.APPLICATION_JSON.toString())
                .content(writer.toString()))
                .andExpect(status().isBadRequest());
    }
}
