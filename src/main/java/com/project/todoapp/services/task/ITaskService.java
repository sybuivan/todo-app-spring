package com.project.todoapp.services.task;

import com.project.todoapp.models.Task;
import com.project.todoapp.models.User;
import java.util.List;

public interface ITaskService<T, U> {
  T createTask(T task);
  T updateTaskById(int taskId, T task);

  void deleteTaskById(int id);

  T findTaskById(int id);

  List<T> findAllTask(U user);

  boolean existsByTaskIdAndUser(int taskId, User user);

  boolean completedTask(int taskId);
}
