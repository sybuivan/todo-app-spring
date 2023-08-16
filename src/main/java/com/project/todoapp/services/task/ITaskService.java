package com.project.todoapp.services.task;

import com.project.todoapp.dto.TaskDto;
import com.project.todoapp.models.Task;
import com.project.todoapp.models.User;
import com.project.todoapp.payload.response.ListResponse;
import java.util.List;

public interface ITaskService<T, U> {

  T createTask(T task);

  T updateTaskById(int taskId, T task);

  int deleteTaskById(int id);

  T findTaskById(int id);

  ListResponse<TaskDto> findAllTask(U user, String filters, int typeId, String name, int page, int size,
      String sortBy,
      String sortDir);

  boolean existsByTaskIdAndUser(int taskId, User user);

  Task completedTask(int taskId);
}
