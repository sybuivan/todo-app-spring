package com.project.todoapp.controllers;

import com.project.todoapp.constants.AppConstants;
import com.project.todoapp.constants.Common;
import com.project.todoapp.dto.UserDto;
import com.project.todoapp.exception.ResourceNotFoundException;
import com.project.todoapp.mapper.UserMapper;
import com.project.todoapp.models.User;
import com.project.todoapp.payload.request.LockUserRequest;
import com.project.todoapp.payload.request.LoginRequest;
import com.project.todoapp.payload.request.TaskRequest;
import com.project.todoapp.payload.response.CommonResponse;
import com.project.todoapp.payload.response.ListResponse;
import com.project.todoapp.services.user.IUserService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import java.rmi.AlreadyBoundException;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/admin")
public class UserController {

  @Autowired
  private IUserService userService;
  private UserMapper userMapper;

  @GetMapping("/users")
  @RolesAllowed("ROLE_ADMIN")
  public ResponseEntity getUserList(
      @RequestParam(value = "page", required = false, defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
      @RequestParam(value = "size", required = false, defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size,
      @RequestParam(value = "sortBy", required = false, defaultValue = AppConstants.DEFAULT_SORT_BY_FIRST_NAME) String sortBy,
      @RequestParam(value = "sortDir", required = false, defaultValue = AppConstants.DEFAULT_SORT_DIRECTION) String sortDir) {

    ListResponse<User> listResponse = userService.getUserList(page, size, "", sortBy, sortDir);

    return ResponseEntity.status(HttpStatus.OK).body(listResponse);
  }

  @PostMapping("/lock-user")
  public ResponseEntity updateLockStatus(@Valid @RequestBody LockUserRequest lockUserRequest)
      throws AlreadyBoundException {

    boolean isLocked = lockUserRequest.getIsLocked();
    User user = userService.updateLockStatus(lockUserRequest.getEmail(),
        isLocked);

    String message = isLocked ? "isLock" : "unLock" + " user successfully";

    return ResponseEntity.status(HttpStatus.OK)
        .body(new CommonResponse<>(message, userMapper.mapToUserDto(user)));
  }
}
