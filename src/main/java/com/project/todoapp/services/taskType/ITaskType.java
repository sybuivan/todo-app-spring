package com.project.todoapp.services.taskType;

import com.project.todoapp.dto.TaskTypeDto;
import com.project.todoapp.models.TaskType;
import com.project.todoapp.models.User;
import java.util.List;

public interface ITaskType<T, U> {
  T createTaskType(T taskType);

  boolean isExitsTaskType(U u, String name);

  T findTypeById(U u, int typeId);

  List<TaskTypeDto> getTypeTaskList(U u);
}
