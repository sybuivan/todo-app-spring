package com.project.todoapp.controllers;

import com.project.todoapp.models.TaskType;
import com.project.todoapp.models.User;
import com.project.todoapp.payload.response.MessageResponse;
import com.project.todoapp.services.taskType.ITaskType;
import com.project.todoapp.services.user.IUserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController

@RequestMapping("/api/v1/task-type")
@AllArgsConstructor
public class TaskTypeController {

  private ITaskType taskTypeService;
  private IUserService<User> userService;

  @PostMapping
  public ResponseEntity createTaskType(@Valid @RequestBody TaskType taskType) {
    User user = userService.getUserLogin();
    if(taskTypeService.isExitsTaskType(user,taskType.getName())) {
      return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse("Type task is already taken"));
    }

    taskType.setUser(userService.getUserLogin());
    return ResponseEntity.status(HttpStatus.CREATED).body(taskTypeService.createTaskType(taskType));
  }

  @GetMapping
  public ResponseEntity getTaskTypeByUser() {
    return ResponseEntity.status(HttpStatus.OK)
        .body(taskTypeService.getTypeTaskList(userService.getUserLogin()));
  }
}
