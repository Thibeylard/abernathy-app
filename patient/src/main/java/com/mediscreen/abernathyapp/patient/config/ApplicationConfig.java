package com.mediscreen.abernathyapp.patient.config;

import brave.sampler.Sampler;
import com.mediscreen.abernathyapp.patient.validators.PatientValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {

    @Bean
    public PatientValidator beforeCreatePatientValidator() {
        return new PatientValidator();
    }

    @Bean
    public Logger getClientPatientLogger() {
        return LoggerFactory.getLogger("patient-logger");
    }

    // Spring Sleuth tracing configuration
    @Bean
    public Sampler defaultSampler() {
        return Sampler.ALWAYS_SAMPLE;
    }
}
