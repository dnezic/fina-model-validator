package com.svesoftware.validators.fina;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = FINAModelValidator.class)
public @interface FINAModel {
    String message() default "{com.svesoftware.validators.fina.constraints.FINAModel}";
    
    Class<?>[] groups() default {};

    public abstract Class<? extends Payload>[] payload() default {};

}
