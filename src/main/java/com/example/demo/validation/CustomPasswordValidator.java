package com.example.demo.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Pattern;

public class CustomPasswordValidator implements ConstraintValidator<PasswordValidator, String> {

    private static final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[~`! @#$%^&*()_\\-+={[}]|:;\"'<,>.?/])(?=\\S+$).{8,100}$";

    private String message;


    @Override
    public void initialize (final PasswordValidator constraintAnnotation) {
        this.message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        boolean matches = Pattern.compile(PASSWORD_PATTERN)
                .matcher(value)
                .matches();
        if (!matches) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(message)
                    .addConstraintViolation();
        }
        return matches;
    }
}
