package com.project.todoapp.services.task;

import com.project.todoapp.constants.MessageEnum;
import com.project.todoapp.exception.ResourceNotFoundException;
import com.project.todoapp.models.Task;
import com.project.todoapp.models.TaskDetail;
import com.project.todoapp.models.User;
import com.project.todoapp.repositories.TaskRepository;
import com.project.todoapp.services.user.IUserService;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class TaskService implements ITaskService<Task, User> {

  private TaskRepository taskRepository;
  private IUserService userService;

  @Transactional
  @Override
  public Task createTask(Task task) {
    return taskRepository.save(task);
  }

  @Override
  public Task updateTaskById(int taskId, Task task) {
    Task taskFound = this.findTaskById(taskId);

    taskFound.setName(task.getName());

    return taskRepository.save(taskFound);
  }

  @Override
  public void deleteTaskById(int taskId) {
    Task taskFound = this.findTaskById(taskId);

    taskRepository.deleteById(taskId);
  }

  @Override
  public Task findTaskById(int taskId) {
    Task taskFound = taskRepository.findById(taskId).orElse(null);

    if (taskFound == null) {
      throw new ResourceNotFoundException(
          MessageEnum.NOT_FOUND.getFormattedMessage("task", taskId));
    }

    return taskFound;
  }

  @Override
  public List<Task> findAllTask(User user) {
    return taskRepository.findByUser(user);
  }

  @Override
  public boolean existsByTaskIdAndUser(int taskId, User user) {
    return taskRepository.existsByTaskIdAndUser(taskId, user);
  }

  @Override
  public boolean completedTask(int taskId) {
    return false;
  }
}
