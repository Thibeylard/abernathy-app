package com.mediscreen.abernathy.client.config;

import brave.sampler.Sampler;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.mediscreen.abernathy.client.patient.deserializers.PatientCollectionResourceDeserializer;
import com.mediscreen.abernathy.client.patient.dtos.PatientCollectionResourceDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {
    @Bean
    public Logger getPatientLogger() {
        return LoggerFactory.getLogger("patient");
    }

    // Spring Sleuth tracing configuration
    @Bean
    public Sampler defaultSampler() {
        return Sampler.ALWAYS_SAMPLE;
    }

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        PatientCollectionResourceDeserializer patientCollectionDeserializer =
                new PatientCollectionResourceDeserializer(objectMapper);
        SimpleModule module = new SimpleModule();
        module.addDeserializer(PatientCollectionResourceDTO.class, patientCollectionDeserializer);

        objectMapper.registerModule(module);
        return objectMapper;
    }
}
