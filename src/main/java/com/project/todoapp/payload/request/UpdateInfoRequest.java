package com.project.todoapp.payload.request;

import com.project.todoapp.validator.NoWhitespace;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class UpdateInfoRequest {
  @NotBlank(message = "firstName is a required field.")
  private String firstName;

  @NotBlank(message = "lastName is a required field.")
  private String lastName;

  @NotBlank(message = "userName is a required field.")
  @NoWhitespace(message = "Username must not contain whitespace")
  private String username;

  private LocalDateTime createdTime;
  private String email;
}
