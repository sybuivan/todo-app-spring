package com.project.todoapp.controllers;

import com.project.todoapp.constants.MessageEnum;
import com.project.todoapp.dto.TaskDetailDto;
import com.project.todoapp.exception.ResourceNotFoundException;
import com.project.todoapp.mapper.TaskMapper;
import com.project.todoapp.models.Task;
import com.project.todoapp.models.TaskDetail;
import com.project.todoapp.models.User;
import com.project.todoapp.payload.request.TaskDetailRequest;
import com.project.todoapp.payload.response.CommonResponse;
import com.project.todoapp.payload.response.MessageResponse;
import com.project.todoapp.services.task.ITaskService;
import com.project.todoapp.services.taskDetail.ITaskDetail;
import com.project.todoapp.services.user.IUserService;
import jakarta.validation.Valid;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/task-detail")
@AllArgsConstructor
public class TaskDetailController {

  private ITaskDetail<TaskDetail, Task> taskDetailService;
  private ITaskService<Task, User> taskService;
  private TaskMapper taskMapper;
  private IUserService userService;

  @PostMapping("/task/{taskId}")
  public ResponseEntity createTaskDetail(@Valid @RequestBody TaskDetailRequest taskDetailRequest,
      @PathVariable int taskId) {
    if (!taskService.existsByTaskIdAndUser(taskId, userService.getUserLogin())) {
      throw new ResourceNotFoundException(
          MessageEnum.NOT_FOUND.getFormattedMessage("task", taskId));
    }

    TaskDetail taskDetail = taskDetailService.createTaskDetail(
        taskMapper.dtoToTask(taskDetailRequest), taskId);

    return ResponseEntity.status(HttpStatus.CREATED)
        .body(new CommonResponse<TaskDetailDto>("Create task detail successfully",
            taskMapper.taskToDto(taskDetail)));
  }

  @PutMapping("/{taskDetailId}/task")
  public ResponseEntity updateTaskDetailById(
      @Valid @RequestBody TaskDetailRequest taskDetailRequest,
      @PathVariable int taskDetailId) {
    TaskDetail taskDetail = taskDetailService.updateTaskDetail(
        taskMapper.dtoToTask(taskDetailRequest), taskDetailId);

    return ResponseEntity.status(HttpStatus.OK)
        .body(new CommonResponse<TaskDetailDto>("Update task detail successfully",
            taskMapper.taskToDto(taskDetail)));
  }

  @GetMapping("/{taskDetailId}/task")
  private ResponseEntity getTaskDetailById(@PathVariable int taskDetailId) {

    Optional<TaskDetail> taskDetail = taskDetailService.getTaskDetail(taskDetailId);

    return ResponseEntity.status(HttpStatus.OK)
        .body(taskDetail);
  }

  @DeleteMapping("/{taskDetailId}/task")
  private ResponseEntity deleteTaskDetailById(@PathVariable int taskDetailId) {
    taskDetailService.deleteTaskDetail(taskDetailId);

    return ResponseEntity.status(HttpStatus.OK)
        .body(new MessageResponse("Delete task detail successfully"));
  }
}
