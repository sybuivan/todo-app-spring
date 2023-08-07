package com.project.todoapp.services.task;

import com.project.todoapp.models.Task;
import com.project.todoapp.models.User;
import java.util.List;

public interface ITaskService {
  Task createTask(Task task);
  Task updateTaskById(int taskId, Task task);

  void deleteTaskById(int id);

  Task findTaskById(int id);

  List<Task> findAllTask(User user);

  boolean existsByTaskId(int taskId);

}
