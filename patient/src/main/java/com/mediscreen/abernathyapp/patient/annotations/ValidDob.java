package com.mediscreen.abernathyapp.patient.annotations;

import com.mediscreen.abernathyapp.patient.validators.DobValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Constraint(validatedBy = DobValidator.class)
@Target({TYPE, FIELD, ANNOTATION_TYPE, PARAMETER})
@Retention(RUNTIME)
public @interface ValidDob {

    String message() default "Invalid Date of Birth";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}