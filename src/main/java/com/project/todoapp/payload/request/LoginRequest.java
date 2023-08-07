package com.project.todoapp.payload.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest {
  @NotBlank(message = "Email is a required field.")
  @Email(message = "Incorrect email format")
  private String email;

  @NotBlank(message = "Password is a required field.")
  private String password;
}
