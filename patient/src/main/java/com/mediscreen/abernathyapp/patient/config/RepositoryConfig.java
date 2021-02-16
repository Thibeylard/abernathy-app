package com.mediscreen.abernathyapp.patient.config;

import com.mediscreen.abernathyapp.patient.models.Patient;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;

@Configuration
public class RepositoryConfig implements RepositoryRestConfigurer {

    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
        config.setReturnBodyForPutAndPost(true);
        // TODO try to add servlet filter in client to modify API gateway URL and avoid ID exposing
        config.exposeIdsFor(Patient.class);
        config.returnBodyOnCreate("application/json");
        config.returnBodyOnUpdate("application/json");
    }


}
