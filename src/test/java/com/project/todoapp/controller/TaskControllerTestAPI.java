package com.project.todoapp.controller;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.project.todoapp.controllers.TaskController;
import com.project.todoapp.exception.ResourceNotFoundException;
import com.project.todoapp.models.Task;
import com.project.todoapp.models.User;
import com.project.todoapp.payload.response.ListResponse;
import com.project.todoapp.services.task.ITaskService;
import com.project.todoapp.services.taskType.ITaskType;
import com.project.todoapp.services.user.IUserService;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

//@WebMvcTest(controllers = TaskController.class)
@AutoConfigureMockMvc(addFilters = false)
@SpringBootTest(classes = {TaskController.class})
@ExtendWith(MockitoExtension.class)
public class TaskControllerTestAPI {

  @Autowired
  private MockMvc mockMvc;
  @Autowired
  private WebApplicationContext webApplicationContext;
  private MockMvc mvc;
  @MockBean
  private ITaskService<Task, User> taskService;

  @MockBean
  private IUserService userService;
  @MockBean
  private ITaskType taskTypeService;

  @InjectMocks
  private TaskController taskController;

  @BeforeEach
  public void setup() {
//    mvc = MockMvcBuilders
//        .webAppContextSetup(webApplicationContext)
//        .build();
//    this.mockMvc = webAppContextSetup(webApplicationContext).build();
  }

  @Test
  void testGetTaskList() throws Exception {
    // Arrange
    User mockUser = new User();
    mockUser.setEmail("sybuivan1429@gmail.com");
    mockUser.setUserId(1);

    String filters = "ALL";
    String querySearch = "";
    int page = 1;
    int size = 10;
    String sortBy = "name";
    String sortDir = "ASC";

    List<Task> taskList = new ArrayList<>();
    taskList.add(new Task(31, "task 1", new Date(),
        LocalDateTime.parse("2023-08-10T14:21:57"),
        LocalDateTime.parse("2023-08-10T14:21:57"),null,null, mockUser,null, null));

    ListResponse<Task> listResponse = new ListResponse<>();
    listResponse.setTotalPage(1);
    listResponse.setTotalData(1);
    listResponse.setPage(1);
    listResponse.setTotalCurrentData(1);
    listResponse.setData(taskList);

    when(userService.getUserLogin()).thenReturn(mockUser);
    when(userService.findByEmail(mockUser.getEmail())).thenReturn(mockUser);
    when(taskService.findAllTask(mockUser, filters, 1, querySearch, page, size, sortBy,
        sortDir)).thenReturn(listResponse);

    // Call the controller method
    ResponseEntity<ListResponse<Task>> responseEntity = taskController.getAllTask(page, size,
        sortBy, sortDir, querySearch,"ALL", filters);

    // Assertions
    verify(userService, times(1)).getUserLogin();
    verify(userService, times(1)).findByEmail(mockUser.getEmail());
    verify(taskService, times(1)).findAllTask(eq(mockUser), anyString(), anyInt(), anyString(), anyInt(),
        anyInt(), anyString(), anyString());

    assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    assertNotNull(responseEntity.getBody());

    System.out.println("responseList: " + responseEntity.getBody().getData());

    ListResponse<Task> responseList = responseEntity.getBody();
    assertEquals(1, responseList.getTotalPage());
    assertEquals(1, responseList.getTotalData());
    assertEquals(1, responseList.getPage());
    assertEquals(1, responseList.getTotalCurrentData());
    assertEquals(taskList, responseList.getData());
  }

  @Test
  void getTaskId() {
    User mockUser = new User();
    mockUser.setEmail("sybuivan1429@gmail.com");
    mockUser.setUserId(1);

    Task mockTask = new Task(31, "task 1", new Date(), LocalDateTime.parse("2023-08-10T14:21:57"),
        LocalDateTime.parse("2023-08-10T14:21:57"),null,null, mockUser,null, null);

    when(taskService.findTaskById(mockTask.getTaskId())).thenReturn(mockTask);

    ResponseEntity<Task> responseEntity = taskController.getTaskById(mockTask.getTaskId());

    verify(taskService, times(1)).findTaskById(anyInt());

    Task taskResponse = responseEntity.getBody();

    assertEquals(mockTask.getTaskId(), taskResponse.getTaskId());
    assertEquals(mockUser, taskResponse.getUser());
    assertEquals(LocalDateTime.parse("2023-08-10T14:21:57"), taskResponse.getCreatedTime());
  }

  @Test
  void completedTask() throws Exception {
    int taskId = 33;
    User mockUser = new User();
    mockUser.setEmail("sybuivan1429@gmail.com");
    mockUser.setUserId(1);

    Task mockTask = new Task(31, "task 1", new Date(), LocalDateTime.parse("2023-08-10T14:21:57"),
        LocalDateTime.parse("2023-08-10T14:21:57"), null, null, mockUser,null, null);

    when(userService.getUserLogin()).thenReturn(mockUser);
    when(taskService.existsByTaskIdAndUser(taskId, mockUser)).thenReturn(true);
    when(taskService.completedTask(taskId)).thenReturn(mockTask);

    ResponseEntity responseEntity = taskController.completedTask(taskId);

    assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
  }

  @Test
  public void testDeleteTask_Success() {
    int taskId = 33;
    User mockUser = new User();

    when(userService.getUserLogin()).thenReturn(mockUser);
    when(taskService.existsByTaskIdAndUser(taskId, mockUser)).thenReturn(true);

    ResponseEntity responseEntity = taskController.deleteTask(taskId);

    assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    verify(taskService, times(1)).deleteTaskById(taskId);
  }

  @Test
  public void testDeleteTask_TaskNotFound() {
    int taskId = 33;
    User mockUser = new User();

    when(userService.getUserLogin()).thenReturn(mockUser);
    when(taskService.existsByTaskIdAndUser(taskId, mockUser)).thenReturn(false);

    assertThrows(ResourceNotFoundException.class, () -> {
      taskController.deleteTask(taskId);
    });
  }
}
