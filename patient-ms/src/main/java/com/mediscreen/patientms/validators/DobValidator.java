package com.mediscreen.patientms.validators;

import com.mediscreen.patientms.annotations.ValidDob;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class DobValidator implements ConstraintValidator<ValidDob, String> {

    @Override
    public void initialize(ValidDob constraintAnnotation) {

    }

    @Override
    public boolean isValid(String dob, ConstraintValidatorContext context) {
        try {
            //TODO develop check by verifying individually day, month and year values
            new SimpleDateFormat("yyyy-MM-dd").parse(dob);
        } catch (ParseException e) {
            return false;
        }
        return true;
    }
}
