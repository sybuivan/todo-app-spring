package com.project.todoapp.payload.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
public class ResetPassword {

  @NotBlank(message = "newPassword is a required field.")
  @Length(min = 8, message = "newPassword need at least 8 characters")
  private String newPassword;

  @NotEmpty(message = "confirmPassword is a required field.")
  private String confirmPassword;

  @NotEmpty(message = "token is a required field.")
  private String token;
}
