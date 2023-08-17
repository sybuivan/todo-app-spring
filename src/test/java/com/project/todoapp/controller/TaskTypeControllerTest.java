package com.project.todoapp.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.project.todoapp.controllers.TaskController;
import com.project.todoapp.controllers.TaskTypeController;
import com.project.todoapp.models.TaskType;
import com.project.todoapp.models.User;
import com.project.todoapp.services.taskType.ITaskType;
import com.project.todoapp.services.user.IUserService;
import com.project.todoapp.utils.Functional;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@AutoConfigureMockMvc(addFilters = false)
@SpringBootTest(classes = {TaskTypeController.class})
@ExtendWith(MockitoExtension.class)
public class TaskTypeControllerTest {
  @InjectMocks
  private TaskTypeController taskTypeController;

  @MockBean
  private IUserService userService;
  @MockBean
  private ITaskType taskTypeService;

  @MockBean
  private Functional functional;

  @Test
  public void giveTaskTypeUser() {
    User user = new User();
    user.setEmail("phamvanthien@gmail.com");

    List<TaskType> mockTaskTypeList = new ArrayList<>();
    mockTaskTypeList.add(new TaskType(1, "worker", null, user));
    when(userService.getUserLogin()).thenReturn(user);
    when(taskTypeService.getTypeTaskList(any(User.class))).thenReturn(mockTaskTypeList);

    // Create the controller instance and inject the mocked services
    TaskTypeController controller = new TaskTypeController(taskTypeService, userService, functional);

    ResponseEntity responseEntity = controller.getTaskTypeByUser();
    System.out.println("Body: " + responseEntity.getBody());

    // Verify the response
    assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    assertEquals(mockTaskTypeList, responseEntity.getBody());
  }
}
