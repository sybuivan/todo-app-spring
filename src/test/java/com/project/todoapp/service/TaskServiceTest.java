package com.project.todoapp.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.project.todoapp.dto.ITaskDto;
import com.project.todoapp.exception.ResourceNotFoundException;
import com.project.todoapp.models.Task;
import com.project.todoapp.models.TaskType;
import com.project.todoapp.models.User;
import com.project.todoapp.payload.response.ListResponse;
import com.project.todoapp.repositories.TaskRepository;
import com.project.todoapp.services.task.ITaskService;
import com.project.todoapp.services.task.TaskService;
import com.project.todoapp.services.user.IUserService;
import com.project.todoapp.utils.PageableCommon;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@ExtendWith(MockitoExtension.class)
public class TaskServiceTest {

  @InjectMocks
  private TaskService taskService;

  @Mock
  private IUserService userService;
  @Mock
  private TaskRepository taskRepository;

  @Mock
  private PageableCommon pageableCommon;

  private Task task;
  public int taskId;

  @BeforeEach
  void init() {
    taskId = 1;

    task = new Task();
    task.setName("Task 1");
    task.setTaskId(taskId);
    task.setDueDate(LocalDateTime.parse("2023-08-10T14:21:57"));
    task.setUser(new User());
  }

  @Test
  void should_save_task() {
    when(taskRepository.save(any(Task.class))).thenReturn(task);

    Task taskCreate = (Task) taskService.createTask(task);

    assertEquals(task, taskCreate);

    verify(taskRepository).save(any(Task.class));
  }

  @Test
  void should_update_task() {

    Task existingTask = new Task();
    existingTask.setTaskId(44);
    existingTask.setName("Existing Task");

    Task updatedTask = new Task();
    updatedTask.setName("Updated Task");
    updatedTask.setDescription("Updated description");
    updatedTask.setDueDate(LocalDate.now().plusDays(7).atStartOfDay());

    when(taskRepository.existsByTaskIdAndUser(44, (User) userService.getUserLogin())).thenReturn(
        true);

    when(taskRepository.findById(44)).thenReturn(Optional.of(existingTask));

    when(taskRepository.save(any(Task.class))).thenReturn(updatedTask);

    Task result = taskService.updateTaskById(44, updatedTask);
    
    assertEquals(updatedTask.getName(), result.getName());
    assertEquals(updatedTask.getTaskType(), result.getTaskType());
    assertEquals(updatedTask.getDescription(), result.getDescription());
    assertEquals(updatedTask.getDueDate(), result.getDueDate());
  }

  @Test
  void find_task_by_id_notFound() {
    int taskId = 1;

    when(taskRepository.findById(taskId)).thenReturn(Optional.empty());
    when(taskService.existsByTaskIdAndUser(taskId, (User) userService.getUserLogin())).thenReturn(
        false);

    assertThrows(ResourceNotFoundException.class, () -> {
      taskService.findTaskById(taskId);
    });
  }

  @Test
  void find_task_by_id_test() {
    int taskId = 1;

    when(taskRepository.findById(taskId)).thenReturn(Optional.ofNullable(task));
    when(taskService.existsByTaskIdAndUser(taskId, (User) userService.getUserLogin())).thenReturn(
        true);

    Task result = taskService.findTaskById(taskId);

    System.out.println("Result: " + result.getDueDate());

    assertEquals(result.getName(), task.getName());
    assertEquals(result.getTaskType(), task.getTaskType());
    assertEquals(result.getDescription(), task.getDescription());
    assertEquals(result.getDueDate(), task.getDueDate());
  }

  @Test
  void getAllTaskList_test() {
    User user = new User();
    user.setUserId(1);

    Pageable pageable = PageRequest.of(0, 10, Sort.by("createdAt").descending());

    Page<ITaskDto> tasksPage = new PageImpl<>(new ArrayList<>(), pageable, 0);

    when(pageableCommon.getPageable(anyInt(), anyInt(), anyString(), anyString())).thenReturn(
        pageable);
    when(taskRepository.findTasksByUserWithFilter(eq(1), anyString(), anyInt(), anyString(),
        eq(pageable)))
        .thenReturn(tasksPage);

    ListResponse<ITaskDto> result = taskService.findAllTask(user, "filter", 1, "name", 0, 10,
        "createdAt", "desc");

    assertEquals(tasksPage.getContent(), result.getData());
    assertEquals(tasksPage.getTotalElements(), result.getTotalData());
    assertEquals(tasksPage.getTotalPages(), result.getTotalPage());
    assertEquals(0, result.getPage());
    assertEquals(0, result.getTotalCurrentData());
  }
}