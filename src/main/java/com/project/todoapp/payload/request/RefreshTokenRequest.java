package com.project.todoapp.payload.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RefreshTokenRequest {
  @NotBlank(message = "refreshToken not empty")
  private String refreshToken;
}
