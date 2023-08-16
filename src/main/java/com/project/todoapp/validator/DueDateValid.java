package com.project.todoapp.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Documented
@Constraint(validatedBy = DueDateValidator.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface DueDateValid {
  String message() default "Due date must be greater than the current date";
  Class<?>[] groups() default {};
  Class<? extends Payload>[] payload() default {};
}
