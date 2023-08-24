package com.project.todoapp.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.project.todoapp.controllers.UserController;
import com.project.todoapp.dto.UserDto;
import com.project.todoapp.dto.UserTaskStatistics;
import com.project.todoapp.mapper.UserMapper;
import com.project.todoapp.models.User;
import com.project.todoapp.payload.request.ChangePasswordRequest;
import com.project.todoapp.payload.request.LockUserRequest;
import com.project.todoapp.payload.request.ResetPasswordByUserRequest;
import com.project.todoapp.payload.response.CommonResponse;
import com.project.todoapp.payload.response.ListResponse;
import com.project.todoapp.payload.response.MessageResponse;
import com.project.todoapp.services.user.IUserService;
import java.rmi.AlreadyBoundException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

  @Mock
  private IUserService<User> userService;
  @Mock
  private UserMapper userMapper;

  @Mock
  private PasswordEncoder passwordEncoder;

  @InjectMocks
  private UserController userController;


  @Test
  public void testGetUserList() {
    when(userService.getUserList(anyInt(), anyInt(), anyString(), anyString(), anyString(),
        anyString()))
        .thenReturn(new ListResponse<>());

    ResponseEntity response = userController.getUserList(1, 10, "query", "filters", "sortBy",
        "asc");

    // Assertions for the response
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertNotNull(response.getBody());
  }

  @Test
  public void testUpdateLockStatus() throws AlreadyBoundException {
    when(userService.updateLockStatus(anyString(), anyBoolean())).thenReturn(new User());
    when(userMapper.mapToUserDto(any(User.class))).thenReturn(new UserDto());

    LockUserRequest lockUserRequest = new LockUserRequest();
    lockUserRequest.setEmail("sybuivan1429@gmail.com");
    lockUserRequest.setIsLocked(true);

    ResponseEntity response = userController.updateLockStatus(lockUserRequest);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertNotNull(response.getBody());
    assertTrue(response.getBody() instanceof CommonResponse);
  }

  @Test
  public void testResetPasswordByUser() {
    // Create a ResetPasswordByUserRequest
    String email = "sybuivan14@gmail.com";
    String password = "vansy1429";
    ResetPasswordByUserRequest request = new ResetPasswordByUserRequest();
    request.setEmail(email);
    request.setNewPassword(password);

    String newPassword = "$2a$10$ZMjGeUrpFBv3mf70k2hJpuzcMKeXwJA0u51Zo.SaYNISRuAM9tuoi";

    when(passwordEncoder.encode(eq(password))).thenReturn(newPassword);
    when(userService.resetPasswordByUser(anyString(), anyString())).thenReturn(true);

    // Call the controller method
    ResponseEntity response = userController.resetPasswordByUser(request);

    // Assertions for the response
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertNotNull(response.getBody());
    assertTrue(response.getBody() instanceof MessageResponse);

    MessageResponse messageResponse = (MessageResponse) response.getBody();
    assertEquals("reset password user: " + request.getEmail() + " successfully",
        messageResponse.getMessage());

    verify(passwordEncoder).encode(eq(password));
    verify(userService).resetPasswordByUser(eq(newPassword), eq(email));
  }

  @Test
  void testGetUserStatistics() {
    String startDateStr = "2023-01-01";
    String endDateStr = "2023-12-31";
    List<UserTaskStatistics> userTaskStatistics = new ArrayList<>();
    try {
      SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
      Date startDate = dateFormat.parse(startDateStr);
      Date endDate = dateFormat.parse(endDateStr);
      when(userService.getUserTaskStatistics(startDate, endDate)).thenReturn(userTaskStatistics);

      ResponseEntity response = userController.getUserTaskStatistics(startDate, endDate);

      assertEquals(HttpStatus.OK, response.getStatusCode());
      verify(userService).getUserTaskStatistics(eq(startDate), eq(endDate));
    } catch (ParseException e) {
      e.printStackTrace();
    }
  }

  @Test
  void testChangePassword() throws Exception {
    // Prepare test data
    String oldPassword = "oldPassword";
    String newPassword = "newPassword";
    ChangePasswordRequest changePasswordRequest = new ChangePasswordRequest();
    changePasswordRequest.setOldPassword(oldPassword);
    changePasswordRequest.setNewPassword(newPassword);

    // Mock the behavior of userService.getUserLogin
    when(userService.getUserLogin()).thenReturn(new User());
    when(passwordEncoder.encode(newPassword)).thenReturn("encodedNewPassword");
    when(passwordEncoder.matches(oldPassword, "storedPassword")).thenReturn(true);

    when(userService.changePassword(anyString())).thenReturn(0);


    // Call the controller method
    ResponseEntity response = userController.changePassword(changePasswordRequest);

    // Assertions for the response
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertNotNull(response.getBody());
    assertTrue(response.getBody() instanceof MessageResponse);
    MessageResponse messageResponse = (MessageResponse) response.getBody();
    assertEquals("Change password successfully", messageResponse.getMessage());

    // Verify interactions
    verify(userService).getUserLogin();
    verify(userService).changePassword("encodedNewPassword");
    verify(passwordEncoder).encode(newPassword);
    verify(passwordEncoder).matches(oldPassword, "storedPassword");
  }
}
