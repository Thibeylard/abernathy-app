package com.mediscreen.abernathyapp.patHistory.config;

import com.mediscreen.abernathyapp.patHistory.models.PatHistory;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;

@Configuration
public class RepositoryConfig implements RepositoryRestConfigurer {

    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
        config.setReturnBodyForPutAndPost(true);
        // TODO try to add servlet filter in client to modify API gateway URL and avoid ID exposing
        config.exposeIdsFor(PatHistory.class);
        config.returnBodyOnCreate("application/json");
        config.returnBodyOnUpdate("application/json");
    }


}
