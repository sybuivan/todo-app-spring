package com.project.todoapp.services.taskType;

import com.project.todoapp.dto.ITaskTypeDto;
import java.util.List;

public interface ITaskType<T, U> {
  T createTaskType(T taskType);

  boolean isExitsTaskType(U u, String name);

  T findTypeById(U u, int typeId);

  List<ITaskTypeDto> getTypeTaskList(U u);

  T updateTaskType(T t);
}
