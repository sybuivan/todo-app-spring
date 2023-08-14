package com.project.todoapp.payload.request;

import com.project.todoapp.utils.Common;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TaskRequest {
  @NotBlank(message = "name is a required field.")
  private String name;

  private String description;
  private LocalDateTime dueDate;
  private int typeId;
}
