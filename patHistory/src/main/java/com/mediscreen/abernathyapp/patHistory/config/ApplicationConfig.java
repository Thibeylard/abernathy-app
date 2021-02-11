package com.mediscreen.abernathyapp.patHistory.config;

import brave.sampler.Sampler;
import com.mediscreen.abernathyapp.patHistory.validators.PatHistoryValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {

    @Bean
    public PatHistoryValidator beforeCreatePatientValidator() {
        return new PatHistoryValidator();
    }

    @Bean
    public PatHistoryValidator beforeSavePatientValidator() {
        return new PatHistoryValidator();
    }

    @Bean
    public Logger getPatHistoryLogger() {
        return LoggerFactory.getLogger("patHistory");
    }

    // Spring Sleuth tracing configuration
    @Bean
    public Sampler defaultSampler() {
        return Sampler.ALWAYS_SAMPLE;
    }

}
