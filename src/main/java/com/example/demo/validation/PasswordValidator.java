package com.example.demo.validation;

import jakarta.validation.Constraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Payload;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Constraint(validatedBy = {CustomPasswordValidator.class})
public @interface PasswordValidator {

    String message() default
            """
            Password must be at least 8 characters
            long,
            contains at least 1 upper case letter,
            1 lower case letter,
            1 digit
            and one of these symbols ~`! @#$%^&*()_-+={[}]|\\:;\"'<,>.?/.
            """;
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
