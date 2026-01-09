package org.example.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ValidIsbnValidator implements ConstraintValidator<ValidIsbn, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) return false;
        String normalized = value.replace("-", "").trim();
        if(!(normalized.length() == 10 || normalized.length() == 13)) return false;
        for (char c : normalized.toCharArray()) {
            if(!Character.isDigit(c)) return false;
        }
        return true;
    }
}
