package com.project.todoapp.payload.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

public class LockUserRequest {

  @NotBlank(message = "Email is a required field.")
  @Email(message = "Incorrect email format")
  private String email;

  @NotNull(message = "isLocked is a required field.")
  private boolean isLocked;

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public boolean getIsLocked() {
    return isLocked;
  }

  public void setIsLocked(boolean isLocked) {
    this.isLocked = isLocked;
  }
}
