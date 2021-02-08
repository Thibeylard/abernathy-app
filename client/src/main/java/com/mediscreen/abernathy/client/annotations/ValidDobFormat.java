package com.mediscreen.abernathy.client.annotations;

import com.mediscreen.abernathy.client.validators.DobConstraintValidator;

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

    String message() default "doit respecter le format yyyy-MM-dd.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
