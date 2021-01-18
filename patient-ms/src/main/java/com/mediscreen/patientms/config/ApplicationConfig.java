package com.mediscreen.patientms.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;

@Configuration
public class ApplicationConfig implements RepositoryRestConfigurer {

    /**
     * Override this method to add additional configuration.
     *
     * @param config Main configuration bean.
     */
    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
        config.setReturnBodyForPutAndPost(true);
        config.returnBodyOnCreate("application/json");
        config.returnBodyOnUpdate("application/json");
    }

}
