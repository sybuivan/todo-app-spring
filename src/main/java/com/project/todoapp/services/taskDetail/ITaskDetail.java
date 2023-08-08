package com.project.todoapp.services.taskDetail;

import com.project.todoapp.models.TaskDetail;
import com.project.todoapp.models.User;
import java.util.Optional;

public interface ITaskDetail<TD, T> {
  TD createTaskDetail(TD td, int taskId);
  TD updateTaskDetail(TD td, int taskDetailId);
  void deleteTaskDetail(int taskDetailId);

  Optional<TaskDetail> getTaskDetail(int taskDetailId);
  boolean isTaskDetailBelongsToUser(int taskDetailId, int userId);
}
