package com.project.todoapp.dto;

import java.util.Date;
import lombok.Getter;
import lombok.Setter;

//@Getter
//@Setter
public interface UserTaskStatistics {
  String getUsername();
  Long getCompletedTasks();
  Long getIncompleteTasks();
}