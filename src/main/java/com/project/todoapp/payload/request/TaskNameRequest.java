package com.project.todoapp.payload.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TaskNameRequest {
  @NotBlank(message = "Name is a required field")
  private String name;
}
