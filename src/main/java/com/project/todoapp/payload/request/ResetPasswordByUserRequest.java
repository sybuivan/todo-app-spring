package com.project.todoapp.payload.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
public class ResetPasswordByUserRequest {
  @NotBlank(message = "Email is a required field.")
  @Email(message = "Incorrect email format")
  private String email;

  @NotBlank(message = "newPassword is a required field.")
  @Length(min = 8, message = "newPassword need at least 8 characters")
  private String newPassword;
}
