package com.mediscreen.abernathyapp.note.config;

import com.mediscreen.abernathyapp.note.models.Note;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;

@Configuration
public class RepositoryConfig implements RepositoryRestConfigurer {

    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
        config.setReturnBodyForPutAndPost(true);
        // TODO try to add servlet filter in client to modify API gateway URL and avoid ID exposing
        config.exposeIdsFor(Note.class);
        config.returnBodyOnCreate("application/json");
        config.returnBodyOnUpdate("application/json");
    }


}
