package com.project.todoapp.services.task;

import com.project.todoapp.constants.MessageEnum;
import com.project.todoapp.exception.ResourceNotFoundException;
import com.project.todoapp.models.Task;
import com.project.todoapp.models.User;
import com.project.todoapp.payload.response.ListResponse;
import com.project.todoapp.repositories.TaskRepository;
import com.project.todoapp.services.user.IUserService;
import com.project.todoapp.utils.PageableCommon;
import java.util.Date;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class TaskService implements ITaskService<Task, User> {

  private TaskRepository taskRepository;
  private IUserService userService;
  private PageableCommon pageableCommon;


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

    if (this.existsByTaskIdAndUser(taskId, userService.getUserLogin())) {
      throw new ResourceNotFoundException(
          MessageEnum.NOT_FOUND.getFormattedMessage("task", taskId));
    }

    return taskFound;
  }

  @Override
  public ListResponse<Task> findAllTask(User user, String filters, int page, int size,
      String sortBy, String sortDir) {

    Pageable pageable = pageableCommon.getPageable(page, size, sortBy,sortDir);

    Page<Task> tasks = taskRepository.findTasksByUserWithFilter(user.getUserId(), filters,
        pageable);

    ListResponse<Task> listResponse = new ListResponse<>();
    List<Task> listOfPosts = tasks.getContent();

    listResponse.setTotalData(tasks.getContent().size() == 0 ? 0 : (int) tasks.getTotalElements());
    listResponse.setTotalPage(tasks.getContent().size() == 0 ? 0 : (int) tasks.getTotalPages());
    listResponse.setData(listOfPosts);
    listResponse.setPage(page);
    listResponse.setTotalCurrentData(tasks.getContent().size());

    return listResponse;
  }

  @Override
  public boolean existsByTaskIdAndUser(int taskId, User user) {
    return taskRepository.existsByTaskIdAndUser(taskId, user);
  }

  @Override
  public Task completedTask(int taskId) {
    Task taskFound = this.findTaskById(taskId);

    taskFound.setCompleteDate(new Date());
    return taskRepository.save(taskFound);
  }
}
