package com.project.todoapp.controllers;

import com.project.todoapp.constants.AppConstants;
import com.project.todoapp.constants.MessageEnum;
import com.project.todoapp.constants.StatusEnum;
import com.project.todoapp.exception.ResourceNotFoundException;
import com.project.todoapp.models.Task;
import com.project.todoapp.models.User;
import com.project.todoapp.payload.request.TaskRequest;
import com.project.todoapp.payload.response.CommonResponse;
import com.project.todoapp.payload.response.ListResponse;
import com.project.todoapp.payload.response.MessageResponse;
import com.project.todoapp.services.task.ITaskService;
import com.project.todoapp.services.user.IUserService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/tasks")
@AllArgsConstructor
public class TaskController {

  private ITaskService<Task, User> taskService;
  private IUserService userService;

  @PostMapping
  public ResponseEntity createTask(@Valid @RequestBody TaskRequest taskRequest) {
    Task taskNew = new Task();
    taskNew.setName(taskRequest.getName());
    taskNew.setUser(userService.getUserLogin());

    Task task = taskService.createTask(taskNew);

    CommonResponse commonResponse = new CommonResponse<Task>("Create task success", task);

    return ResponseEntity.status(HttpStatus.CREATED).body(commonResponse);
  }

  @PutMapping("/{taskId}")
  public ResponseEntity updateTask(@Valid @RequestBody TaskRequest taskRequest,
      @PathVariable int taskId) {

    if (!taskService.existsByTaskIdAndUser(taskId, userService.getUserLogin())) {
      throw new ResourceNotFoundException(
          MessageEnum.NOT_FOUND.getFormattedMessage("task", taskId));
    }

    Task taskNew = new Task();
    taskNew.setName(taskRequest.getName());

    Task task = taskService.updateTaskById(taskId, taskNew);

    CommonResponse commonResponse = new CommonResponse<>("Update task success", task);

    return ResponseEntity.status(HttpStatus.OK).body(commonResponse);
  }

  @DeleteMapping("/{taskId}")
  public ResponseEntity deleteTask(@PathVariable int taskId) {
    if (!taskService.existsByTaskIdAndUser(taskId, userService.getUserLogin())) {
      throw new ResourceNotFoundException(
          MessageEnum.NOT_FOUND.getFormattedMessage("task", taskId));
    }

    taskService.deleteTaskById(taskId);

    return ResponseEntity.status(HttpStatus.OK)
        .body(new MessageResponse("Delete task successfully"));
  }

  @GetMapping
  public ResponseEntity getAllTask(
      @RequestParam(value = "page", required = false, defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
      @RequestParam(value = "size", required = false, defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size,
      @RequestParam(value = "sortBy", required = false, defaultValue = AppConstants.DEFAULT_SORT_BY_NAME) String sortBy,
      @RequestParam(value = "sortDir", required = false, defaultValue = AppConstants.DEFAULT_SORT_DIRECTION) String sortDir,
      @RequestParam(value = "querySearch", required = false, defaultValue = AppConstants.DEFAULT_QUERY_SEARCH) String querySearch,
      @RequestParam(value = "filters", required = false, defaultValue = AppConstants.DEFAULT_FILTER) String filters) {
    User user = userService.findByEmail(userService.getUserLogin().getEmail());

    ListResponse<Task> taskList = taskService.findAllTask(user, filters, querySearch, page, size, sortBy,
        sortDir);

    return ResponseEntity.status(HttpStatus.OK).body(taskList);
  }

  @GetMapping("/{taskId}")
  public ResponseEntity getTaskById(@PathVariable int taskId) {
    Task task = taskService.findTaskById(taskId);

    return ResponseEntity.status(HttpStatus.OK).body(task);
  }

  @PutMapping("/{taskId}/completed-task")
  public ResponseEntity completedTask(@PathVariable int taskId) {
    if (!taskService.existsByTaskIdAndUser(taskId, userService.getUserLogin())) {
      throw new ResourceNotFoundException(
          MessageEnum.NOT_FOUND.getFormattedMessage("task", taskId));
    }
    return ResponseEntity.status(HttpStatus.OK)
        .body(new CommonResponse<>("Completed task", taskService.completedTask(taskId)));
  }
}
