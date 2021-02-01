package com.mediscreen.abernathyapp.patient.config;

import brave.sampler.Sampler;
import com.mediscreen.abernathyapp.patient.validators.PatientValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.ForwardedHeaderFilter;

@Configuration
public class ApplicationConfig {

    @Bean
    public PatientValidator beforeCreatePatientValidator() {
        return new PatientValidator();
    }

    @Bean
    public PatientValidator beforeSavePatientValidator() {
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

    @Bean
    public FilterRegistrationBean<ForwardedHeaderFilter> forwardedHeaderFilter() {
        FilterRegistrationBean<ForwardedHeaderFilter> result = new FilterRegistrationBean<>();
        result.setFilter(new ForwardedHeaderFilter());
        result.setOrder(0);
        return result;
    }
}
