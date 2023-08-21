package com.project.todoapp.dto;

import java.util.Date;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public interface ITaskDto {
   int getTaskId();
   String getTaskTypeName();
   String getName();
   LocalDateTime getDueDate();
   int getTotalSubTasks();
   Date getCompleteDate();
}
