package com.project.todoapp.services.taskDetail;

import com.project.todoapp.constants.MessageEnum;
import com.project.todoapp.exception.ResourceNotFoundException;
import com.project.todoapp.models.Task;
import com.project.todoapp.models.TaskDetail;
import com.project.todoapp.models.User;
import com.project.todoapp.repositories.TaskDetailRepository;
import com.project.todoapp.repositories.TaskRepository;
import com.project.todoapp.services.user.IUserService;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TaskDetailService implements ITaskDetail<TaskDetail, Task> {

  private TaskRepository taskRepository;
  private TaskDetailRepository taskDetailRepository;
  private IUserService userService;

  @Override
  public TaskDetail createTaskDetail(TaskDetail td, int taskId) {
    TaskDetail taskDetail = td;
    taskDetail.setTask(taskRepository.findById(taskId).orElse(null));

    return taskDetailRepository.save(taskDetail);
  }

  @Override
  public TaskDetail updateTaskDetail(TaskDetail td, int taskDetailId) {
    TaskDetail taskDetailFound = taskDetailRepository.findById(taskDetailId).orElse(null);
    if (taskDetailFound == null) {
      throw new ResourceNotFoundException("Task detail not found" + taskDetailId);
    }
    taskDetailFound.setName(td.getName());

    return taskDetailRepository.save(td);
  }

  @Override
  public int deleteTaskDetail(int taskDetailId) {
    return 0;
  }

  @Override
  public Optional<TaskDetail> getTaskDetail(int taskId, int taskDetailId) {

    if (!this.isTaskDetailBelongsToUser(taskDetailId, userService.getUserLogin().getUserId())) {
      throw new ResourceNotFoundException(
          MessageEnum.NOT_FOUND.getFormattedMessage("task detail", taskDetailId));
    }

    return taskDetailRepository.findById(taskDetailId);
  }


  @Override
  public boolean isTaskDetailBelongsToUser(int taskDetailId, int userId) {
    return taskDetailRepository.existsByTaskDetailIdAndUserId(taskDetailId, userId);
  }
}
