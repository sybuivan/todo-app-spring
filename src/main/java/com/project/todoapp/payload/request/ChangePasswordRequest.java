package com.project.todoapp.payload.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Setter
@Getter
@AllArgsConstructor
public class ChangePasswordRequest {
  @NotBlank(message = "oldPassword is a required field.")
  @Length(min = 8, message = "oldPassword need at least 8 characters")
  private String oldPassword;

  @NotBlank(message = "newPassword is a required field.")
  @Length(min = 8, message = "oldPassword need at least 8 characters")
  private String newPassword;
}
