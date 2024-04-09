package com.guen.validator;

import com.guen.common.annotation.EnumNotNull;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class EnumValidValidator implements ConstraintValidator<EnumNotNull, Enum<?>> {
    @Override
    public void initialize(EnumNotNull constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Enum<?> value, ConstraintValidatorContext context) {
        return value != null;
    }
}