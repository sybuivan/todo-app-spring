package com.project.todoapp.controllers;

import com.project.todoapp.constants.AppConstants;
import com.project.todoapp.constants.MessageEnum;
import com.project.todoapp.dto.ITaskDto;
import com.project.todoapp.exception.ResourceNotFoundException;
import com.project.todoapp.mapper.TaskMapper;
import com.project.todoapp.models.Task;
import com.project.todoapp.models.TaskType;
import com.project.todoapp.models.User;
import com.project.todoapp.payload.request.TaskNameRequest;
import com.project.todoapp.payload.request.TaskRequest;
import com.project.todoapp.payload.response.CommonResponse;
import com.project.todoapp.payload.response.ListResponse;
import com.project.todoapp.payload.response.MessageResponse;
import com.project.todoapp.services.task.ITaskService;
import com.project.todoapp.services.taskType.ITaskType;
import com.project.todoapp.services.user.IUserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
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
@CrossOrigin(origins = "http:localhost:3000", maxAge = 3600)
@RequestMapping("/api/v1/tasks")
@SecurityRequirement(name = "Bearer Authentication")
@Tag(name = " Task", description = "The Task API. Contains all the operations that can be performed on a Task.")
@AllArgsConstructor
public class TaskController {

  private ITaskService<Task, User> taskService;
  private IUserService<User> userService;
  private ITaskType<TaskType, User> taskTypeService;
  private TaskMapper taskMapper;

  @PostMapping
  public ResponseEntity createTask(@Valid @RequestBody TaskNameRequest taskRequest) {
    Task taskNew = new Task();
    taskNew.setName(taskRequest.getName());
    taskNew.setUser(userService.getUserLogin());
    taskNew.setTaskType(null);

    try {
      Task task = taskService.createTask(taskNew);
      CommonResponse commonResponse = new CommonResponse<Task>("Create task success", task);
      return ResponseEntity.status(HttpStatus.CREATED).body(commonResponse);
    } catch (Exception e) {
      MessageResponse errorResponse = new MessageResponse(
          MessageEnum.ERROR.getFormattedField(e.getMessage()));

      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }
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

    try {
      Task task = taskService.updateTaskById(taskId, taskNew);

      CommonResponse commonResponse = new CommonResponse<>("Update task success",
          taskMapper.dtoToTaskInfo(task));

      return ResponseEntity.status(HttpStatus.OK).body(commonResponse);
    } catch (Exception e) {
      MessageResponse errorResponse = new MessageResponse(
          MessageEnum.ERROR.getFormattedField(e.getMessage()));

      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }
  }

  @DeleteMapping("/{taskId}")
  public ResponseEntity<?> deleteTask(@PathVariable int taskId) {
    if (!taskService.existsByTaskIdAndUser(taskId, userService.getUserLogin())) {
      throw new ResourceNotFoundException(
          MessageEnum.NOT_FOUND.getFormattedMessage("task", taskId));
    }
    try {
      taskService.deleteTaskById(taskId);

      return ResponseEntity.status(HttpStatus.OK)
          .body(new MessageResponse("Delete task successfully"));
    } catch (Exception e) {
      MessageResponse errorResponse = new MessageResponse(
          MessageEnum.ERROR.getFormattedField(e.getMessage()));

      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }
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

    int typeIdConvert = typeId.equals(AppConstants.DEFAULT_FILTER) ? 0 : Integer.parseInt(typeId);
    User user = userService.findByEmail(userService.getUserLogin().getEmail());

    if (typeIdConvert != 0 && taskTypeService.findTypeById(user, typeIdConvert) == null) {
      throw new ResourceNotFoundException(
          MessageEnum.NOT_FOUND.getFormattedMessage("typeId", typeIdConvert));
    }

    ListResponse<ITaskDto> taskList = taskService.findAllTask(user, filters, typeIdConvert,
        querySearch,
        page,
        size,
        sortBy,
        sortDir);

    return ResponseEntity.status(HttpStatus.OK).body(taskList);
  }

  @GetMapping("/{taskId}")
  public ResponseEntity getTaskById(@PathVariable int taskId) {
    Task task = taskService.findTaskById(taskId);

    return ResponseEntity.status(HttpStatus.OK).body(taskMapper.dtoToTaskInfo(task));
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
