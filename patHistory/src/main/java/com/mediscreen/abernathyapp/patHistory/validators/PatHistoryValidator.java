package com.mediscreen.abernathyapp.patHistory.validators;

import com.mediscreen.abernathyapp.patHistory.models.PatHistory;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class PatHistoryValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return PatHistory.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

    }
}
