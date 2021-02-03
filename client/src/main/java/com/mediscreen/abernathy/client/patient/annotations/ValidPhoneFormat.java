package com.mediscreen.abernathy.client.patient.annotations;

import com.mediscreen.abernathy.client.patient.validators.PhoneConstraintValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Constraint(validatedBy = PhoneConstraintValidator.class)
@Target({TYPE, FIELD, ANNOTATION_TYPE})
@Retention(RUNTIME)
public @interface ValidPhoneFormat {

    String message() default "Invalid phone format";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
