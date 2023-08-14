package com.project.todoapp.controllers;

import com.project.todoapp.constants.AppConstants;
import com.project.todoapp.constants.MessageEnum;
import com.project.todoapp.constants.StatusEnum;
import com.project.todoapp.exception.ResourceNotFoundException;
import com.project.todoapp.models.Task;
import com.project.todoapp.models.TaskType;
import com.project.todoapp.models.User;
import com.project.todoapp.payload.request.TaskRequest;
import com.project.todoapp.payload.response.CommonResponse;
import com.project.todoapp.payload.response.ListResponse;
import com.project.todoapp.payload.response.MessageResponse;
import com.project.todoapp.services.task.ITaskService;
import com.project.todoapp.services.taskType.ITaskType;
import com.project.todoapp.services.user.IUserService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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

  @Autowired
  private IUserService<User> userService;

  private ITaskType<TaskType,User> taskTypeService;

  @PostMapping
  public ResponseEntity createTask(@Valid @RequestBody TaskRequest taskRequest) {
    Task taskNew = new Task();
    taskNew.setName(taskRequest.getName());
    taskNew.setUser(userService.getUserLogin());
    taskNew.setTaskType(null);

    Task task = taskService.createTask(taskNew);

    CommonResponse commonResponse = new CommonResponse<Task>("Create task success", task);

    return ResponseEntity.status(HttpStatus.CREATED).body(commonResponse);
  }

  @PutMapping("/{taskId}")
  public ResponseEntity updateTask(@Valid @RequestBody TaskRequest taskRequest,
      @PathVariable int taskId) {
    Task taskNew = new Task();
    User user = userService.getUserLogin();

    if (!taskService.existsByTaskIdAndUser(taskId, user)) {
      throw new ResourceNotFoundException(
          MessageEnum.NOT_FOUND.getFormattedMessage("task", taskId));
    }

    if (taskRequest.getTypeId() != 0) {
      TaskType taskType = taskTypeService.findTypeById(user, taskRequest.getTypeId());

      if (taskType == null) {
        throw new ResourceNotFoundException(
            MessageEnum.NOT_FOUND.getFormattedMessage("typeTask", taskRequest.getTypeId()));
      }
      taskNew.setTaskType(taskType);
    }

    taskNew.setDescription(taskRequest.getDescription());
    taskNew.setName(taskRequest.getName());
    taskNew.setDueDate(taskRequest.getDueDate());

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
      @RequestParam(value = "typeId", required = false, defaultValue = AppConstants.DEFAULT_FILTER) String typeId,
      @RequestParam(value = "filters", required = false, defaultValue = AppConstants.DEFAULT_FILTER) String filters) {

    System.out.println("tét: " + typeId.equals(AppConstants.DEFAULT_FILTER));
    int typeIdConvert = typeId.equals(AppConstants.DEFAULT_FILTER) ? 0 : Integer.parseInt(typeId);

    System.out.println("typeIdConvert: " + typeIdConvert);

    User user = userService.findByEmail(userService.getUserLogin().getEmail());

    ListResponse<Task> taskList = taskService.findAllTask(user, filters, typeIdConvert, querySearch, page,
        size,
        sortBy,
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
