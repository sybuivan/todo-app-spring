package com.project.todoapp.services.taskDetail;

import com.project.todoapp.constants.MessageEnum;
import com.project.todoapp.exception.ResourceNotFoundException;
import com.project.todoapp.models.Task;
import com.project.todoapp.models.TaskDetail;
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
    Optional<TaskDetail> taskDetailFound = this.getTaskDetail(taskDetailId);

    TaskDetail existingTaskDetail = taskDetailFound.get();
    existingTaskDetail.setName(td.getName());
    return taskDetailRepository.save(existingTaskDetail);
  }

  @Override
  public void deleteTaskDetail(int taskDetailId) {
    this.checkExitsTaskDetail(taskDetailId);

    taskDetailRepository.deleteById(taskDetailId);
  }

  @Override
  public Optional<TaskDetail> getTaskDetail(int taskDetailId) {
    this.checkExitsTaskDetail(taskDetailId);

    return taskDetailRepository.findById(taskDetailId);
  }

  public void checkExitsTaskDetail(int taskDetailId) {
    if (!this.isTaskDetailBelongsToUser(taskDetailId, userService.getUserLogin().getUserId())) {
      throw new ResourceNotFoundException(
          MessageEnum.NOT_FOUND.getFormattedMessage("task detail", taskDetailId));
    }
  }

  @Override
  public boolean isTaskDetailBelongsToUser(int taskDetailId, int userId) {
    return taskDetailRepository.existsByTaskDetailIdAndUserId(taskDetailId, userId);
  }
}
