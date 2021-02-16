package com.mediscreen.abernathyapp.patient.validators;

import com.mediscreen.abernathyapp.patient.models.Patient;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class PatientValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return Patient.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Patient patient = (Patient) target;

        if (patient.getFamily() == null || patient.getFamily().isBlank()) {
            errors.rejectValue(
                    "family",
                    "family.empty",
                    "family field is mandatory");
        }

        if (patient.getGiven() == null || patient.getGiven().isBlank()) {
            errors.rejectValue(
                    "given",
                    "given.empty",
                    "given field is mandatory");

        }

        if (patient.getSex() == null || !patient.getSex().equals("M") && !patient.getSex().equals("F")) {
            errors.rejectValue(
                    "sex",
                    "sex.wrongValue",
                    "sex field must be either M or F");
        }

        if (patient.getDob() == null) {
            errors.rejectValue(
                    "dob",
                    "dob.empty",
                    "dob field is mandatory");
        }

        if (patient.getAddress() == null || patient.getAddress().isBlank()) {
            errors.rejectValue(
                    "address",
                    "address.empty",
                    "address field is mandatory");
        }

        if (patient.getPhone() == null || patient.getPhone().isBlank()) {
            errors.rejectValue(
                    "phone",
                    "phone.empty",
                    "phone field is mandatory");

        } else if (!patient.getPhone().matches("((\\d{3}-){2}\\d{4})")) {
            errors.rejectValue(
                    "phone",
                    "phone.wrongFormat",
                    "phone field must respect XXX-XXX-XXXX format where X is a number between 0 and 9");
        }

    }
}
