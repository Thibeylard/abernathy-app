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
        PatHistory patHistory = (PatHistory) target;

        if (patHistory.getPatientId() == null || patHistory.getPatientId().isBlank()) {
            errors.rejectValue(
                    "patientId",
                    "patientId.empty",
                    "patientId field is mandatory");
        }

        if (patHistory.getContent() == null || patHistory.getContent().isBlank()) {
            errors.rejectValue(
                    "content",
                    "content.empty",
                    "content field is mandatory");
        }
    }
}
