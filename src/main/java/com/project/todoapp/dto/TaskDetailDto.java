package com.project.todoapp.dto;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TaskDetailDto {
  private String name;
  private LocalDateTime created_at;
  private int taskDetailId;
}
