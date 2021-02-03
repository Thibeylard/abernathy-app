package com.mediscreen.abernathy.client.patient.annotations;

import com.mediscreen.abernathy.client.patient.validators.DobConstraintValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Constraint(validatedBy = DobConstraintValidator.class)
@Target({TYPE, FIELD, ANNOTATION_TYPE})
@Retention(RUNTIME)
public @interface ValidDobFormat {

    String message() default "Invalid date of birth format";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
