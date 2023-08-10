package com.project.todoapp.payload.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PasswordForgot {
  @NotEmpty(message = "Email not empty")
  @Email(message = "Incorrect email format")
  private String email;
}
