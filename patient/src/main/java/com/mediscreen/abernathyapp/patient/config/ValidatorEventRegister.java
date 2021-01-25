package com.mediscreen.abernathyapp.patient.config;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.event.ValidatingRepositoryEventListener;

import org.springframework.validation.Validator;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Configuration
public class ValidatorEventRegister implements InitializingBean {

    private final Map<String, Validator> validators;
    ValidatingRepositoryEventListener validatingRepositoryEventListener;

    @Autowired
    public ValidatorEventRegister(ValidatingRepositoryEventListener val, Map<String, Validator> validators) {
        this.validatingRepositoryEventListener = val;
        this.validators = validators;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        List<String> events = Collections.singletonList("beforeCreate");
        for (Map.Entry<String, Validator> entry : validators.entrySet()) {
            events.stream()
                    .filter(p -> entry.getKey().startsWith(p))
                    .findFirst()
                    .ifPresent(
                            p -> validatingRepositoryEventListener
                                    .addValidator(p, entry.getValue()));
        }
    }
}