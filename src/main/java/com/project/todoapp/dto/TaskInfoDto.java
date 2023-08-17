package com.project.todoapp.dto;

import com.project.todoapp.models.TaskDetail;
import com.project.todoapp.models.TaskType;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TaskInfoDto {
  private int taskId;
  private String name;
  private LocalDateTime createdTime;
  private LocalDateTime dueDate;
  private String description;
  private List<TaskDetail> taskDetailList;
  private TaskTypeDto taskType;
}
