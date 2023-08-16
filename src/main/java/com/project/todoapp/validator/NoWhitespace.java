package com.project.todoapp.validator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Documented
@Constraint(validatedBy = NoWhitespaceValidator.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface NoWhitespace {
  String message() default "must not contain whitespace";
  Class<?>[] groups() default {};
  Class<? extends Payload>[] payload() default {};
}