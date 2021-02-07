package com.mediscreen.abernathyapp.note.config;

import brave.sampler.Sampler;
import com.mediscreen.abernathyapp.note.validators.NoteValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {

    @Bean
    public NoteValidator beforeCreatePatientValidator() {
        return new NoteValidator();
    }

    @Bean
    public NoteValidator beforeSavePatientValidator() {
        return new NoteValidator();
    }

    @Bean
    public Logger getNoteLogger() {
        return LoggerFactory.getLogger("note-logger");
    }

    // Spring Sleuth tracing configuration
    @Bean
    public Sampler defaultSampler() {
        return Sampler.ALWAYS_SAMPLE;
    }

}
