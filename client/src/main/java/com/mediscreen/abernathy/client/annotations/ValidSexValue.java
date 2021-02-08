package com.mediscreen.abernathy.client.annotations;

import com.mediscreen.abernathy.client.validators.SexConstraintValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Constraint(validatedBy = SexConstraintValidator.class)
@Target({TYPE, FIELD, ANNOTATION_TYPE})
@Retention(RUNTIME)
public @interface ValidSexValue {

    String message() default "doit être soit M soit F.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
