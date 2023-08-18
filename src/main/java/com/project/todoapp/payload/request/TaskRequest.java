package com.project.todoapp.payload.request;

import com.project.todoapp.utils.Common;
import com.project.todoapp.validator.DueDateValid;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TaskRequest extends TaskNameRequest {
  private String description;

  private LocalDateTime dueDate;
  private int typeId;
}
