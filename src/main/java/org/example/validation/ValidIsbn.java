package org.example.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ValidIsbnValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidIsbn {
    String message() default "Invalid ISBN (must be 10 or 13 digits, can include hyphens)";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
