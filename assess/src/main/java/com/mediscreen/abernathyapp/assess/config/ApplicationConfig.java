package com.mediscreen.abernathyapp.assess.config;

import brave.sampler.Sampler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {
    @Bean
    public Logger getAssessLogger() {
        return LoggerFactory.getLogger("assess");
    }


    // Spring Sleuth tracing configuration
    @Bean
    public Sampler defaultSampler() {
        return Sampler.ALWAYS_SAMPLE;
    }

}