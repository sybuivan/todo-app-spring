package com.project.todoapp.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public interface TaskDto {
   int getTaskId();
   String getName();
   LocalDateTime getDueDate();
   int getTotalSubTasks();
}
