package com.project.todoapp.services.task;

import com.project.todoapp.constants.MessageEnum;
import com.project.todoapp.dto.ITaskDto;
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
  private IUserService<User> userService;
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
    taskFound.setTaskType(task.getTaskType());
    if (task.getDescription() != null) {
      taskFound.setDescription(task.getDescription());
    }

    if (task.getDueDate() != null) {
      taskFound.setDueDate(task.getDueDate());
    }

    return taskRepository.save(taskFound);
  }

  @Override
  @Transactional
  public int deleteTaskById(int taskId) {
     taskRepository.delete(this.findTaskById(taskId));
     return 0;
  }

  @Override
  public Task findTaskById(int taskId) {
    Task taskFound = taskRepository.findById(taskId).orElse(null);

    if (!this.existsByTaskIdAndUser(taskId, userService.getUserLogin())) {
      System.out.println("Vao day hỉ");
      throw new ResourceNotFoundException(
          MessageEnum.NOT_FOUND.getFormattedMessage("task", taskId));
    }

    return taskFound;
  }

  @Override
  public ListResponse<ITaskDto> findAllTask(User user, String filters, int typeId, String name,
      int page, int size, String sortBy, String sortDir) {

    Pageable pageable = pageableCommon.getPageable(page, size, sortBy, sortDir);

    Page<ITaskDto> tasks = taskRepository.findTasksByUserWithFilter(user.getUserId(), name, typeId,
        filters,
        pageable);

    ListResponse<ITaskDto> listResponse = new ListResponse<>();
    List<ITaskDto> listOfPosts = tasks.getContent();

    listResponse.setTotalData(tasks.getContent().size() == 0 ? 0 : (int) tasks.getTotalElements());
    listResponse.setTotalPage(tasks.getContent().size() == 0 ? 0 : tasks.getTotalPages());
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
