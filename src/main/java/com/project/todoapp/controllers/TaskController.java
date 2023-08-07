package com.project.todoapp.controllers;

import com.project.todoapp.constants.StatusEnum;
import com.project.todoapp.exception.ResourceNotFoundException;
import com.project.todoapp.models.Task;
import com.project.todoapp.models.User;
import com.project.todoapp.payload.request.TaskRequest;
import com.project.todoapp.payload.response.CommonResponse;
import com.project.todoapp.payload.response.ListResponse;
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

  private ITaskService taskService;
  private IUserService userService;

  @PostMapping
  public ResponseEntity createTask(@Valid @RequestBody TaskRequest taskRequest) {
    UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
        .getPrincipal();

    User user = userService.findByEmail(userDetails.getUsername());
    Task taskNew = new Task();
    taskNew.setName(taskRequest.getName());
    taskNew.setUser(user);

    Task task = taskService.createTask(taskNew);

    CommonResponse commonResponse = new CommonResponse<Task>("Create task success", task);

    return ResponseEntity.status(HttpStatus.CREATED).body(commonResponse);
  }

  @PutMapping("/{taskId}")
  public ResponseEntity updateTask(@Valid @RequestBody TaskRequest taskRequest,
      @PathVariable int taskId) {

    Task taskNew = new Task();
    taskNew.setName(taskRequest.getName());

    Task task = taskService.updateTaskById(taskId, taskNew);

    CommonResponse commonResponse = new CommonResponse<>("Update task success", task);

    return ResponseEntity.status(HttpStatus.OK).body(commonResponse);
  }

  @DeleteMapping("/{taskId}")
  public ResponseEntity deleteTask(@PathVariable int taskId) {
    if (!taskService.existsByTaskId(taskId)) {
      throw new ResourceNotFoundException("Task not found");
    }

    taskService.deleteTaskById(taskId);

    return ResponseEntity.status(HttpStatus.OK).body("Delete success");
  }

  @GetMapping
  public ResponseEntity getAllTask(@RequestParam(required = false, defaultValue = "1") String page) {
    UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
        .getPrincipal();

    User user = userService.findByEmail(userDetails.getUsername());
    List<Task> taskList = taskService.findAllTask(user);
    ListResponse listResponse = new ListResponse(Integer.parseInt(page), taskList.size(), taskList);
    return ResponseEntity.status(HttpStatus.OK).body(listResponse);
  }
}
