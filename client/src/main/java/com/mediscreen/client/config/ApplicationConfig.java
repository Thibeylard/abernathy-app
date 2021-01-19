package com.mediscreen.client.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {
    @Bean
    public Logger getClientPatientLogger() {
        return LoggerFactory.getLogger("Client-Patient-Log");
    }
}
