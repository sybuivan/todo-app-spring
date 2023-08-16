package com.project.todoapp.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class DueDateValidator implements ConstraintValidator<DueDateValid, LocalDateTime> {

  @Override
  public void initialize(DueDateValid constraintAnnotation) {
    ConstraintValidator.super.initialize(constraintAnnotation);
  }

  @Override
  public boolean isValid(LocalDateTime value, ConstraintValidatorContext context) {
    Date date = new Date();

    Instant instant = date.toInstant();
    LocalDateTime localDateTime = instant.atZone(ZoneId.systemDefault()).toLocalDateTime();

    System.out.println("Date: " + date);
    System.out.println("LocalDateTime: " + localDateTime.isBefore(value));

    return localDateTime.isBefore(value);
  }
}
