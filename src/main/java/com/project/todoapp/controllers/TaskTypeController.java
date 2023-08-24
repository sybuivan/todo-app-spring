package com.project.todoapp.controllers;

import com.project.todoapp.constants.MessageEnum;
import com.project.todoapp.exception.ResourceNotFoundException;
import com.project.todoapp.models.TaskType;
import com.project.todoapp.models.User;
import com.project.todoapp.payload.response.MessageResponse;
import com.project.todoapp.services.taskType.ITaskType;
import com.project.todoapp.services.user.IUserService;
import com.project.todoapp.utils.Functional;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/task-type")
@AllArgsConstructor
@SecurityRequirement(name = "Bearer Authentication")
@Tag(name = "TaskType", description = "The TaskType API. Contains all the operations that can be performed on a TaskType.")
public class TaskTypeController {

  private ITaskType taskTypeService;
  private IUserService<User> userService;
  private Functional functional;

  @PostMapping
  public ResponseEntity createTaskType(@Valid @RequestBody TaskType taskType) {
    User user = userService.getUserLogin();
    if (taskTypeService.isExitsTaskType(user, taskType.getName())) {
      return ResponseEntity.status(HttpStatus.ALREADY_REPORTED)
          .body(new MessageResponse("Type task is already taken"));
    }

    taskType.setUser(userService.getUserLogin());
    return ResponseEntity.status(HttpStatus.CREATED).body(taskTypeService.createTaskType(taskType));
  }

  @GetMapping
  public ResponseEntity getTaskTypeByUser() {
    return ResponseEntity.status(HttpStatus.OK)
        .body(taskTypeService.getTypeTaskList(userService.getUserLogin()));
  }

  @PutMapping("/{typeId}")
  public ResponseEntity updateTaskType(@PathVariable int typeId, @RequestBody TaskType taskType) {
    User user = userService.getUserLogin();
    TaskType taskTypeFound = (TaskType) taskTypeService.findTypeById(user, typeId);
    if (taskTypeFound == null) {
      throw new ResourceNotFoundException(
          MessageEnum.NOT_FOUND.getFormattedMessage("typeId", typeId + ""));
    }

    if (!functional.checkStringForEquality(taskType.getName(), taskTypeFound.getName())) {
      if (taskTypeService.isExitsTaskType(user, taskType.getName())) {
        return ResponseEntity.status(HttpStatus.ALREADY_REPORTED)
            .body(new MessageResponse("Type task is already taken"));
      }
    }

    taskTypeFound.setName(taskType.getName());

    return ResponseEntity.status(HttpStatus.CREATED)
        .body(taskTypeService.updateTaskType(taskTypeFound));
  }
}
