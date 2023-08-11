package com.project.todoapp.controllers;

import com.project.todoapp.constants.AppConstants;
import com.project.todoapp.mapper.UserMapper;
import com.project.todoapp.models.User;
import com.project.todoapp.payload.request.ChangePasswordRequest;
import com.project.todoapp.payload.request.LockUserRequest;
import com.project.todoapp.payload.request.ResetPasswordByUserRequest;
import com.project.todoapp.payload.response.CommonResponse;
import com.project.todoapp.payload.response.ListResponse;
import com.project.todoapp.payload.response.MessageResponse;
import com.project.todoapp.services.user.IUserService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import java.rmi.AlreadyBoundException;
import java.util.Date;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class UserController {

  private IUserService userService;
  private UserMapper userMapper;
  private PasswordEncoder passwordEncoder;

  @GetMapping("/admin/users")
  @RolesAllowed("ROLE_ADMIN")
  public ResponseEntity getUserList(
      @RequestParam(value = "page", required = false, defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
      @RequestParam(value = "size", required = false, defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size,
      @RequestParam(value = "sortBy", required = false, defaultValue = AppConstants.DEFAULT_SORT_BY_FIRST_NAME) String sortBy,
      @RequestParam(value = "querySearch", required = false, defaultValue = AppConstants.DEFAULT_QUERY_SEARCH) String querySearch,
      @RequestParam(value = "filters", required = false, defaultValue = AppConstants.DEFAULT_FILTER) String filters,
      @RequestParam(value = "sortDir", required = false, defaultValue = AppConstants.DEFAULT_SORT_DIRECTION) String sortDir) {

    ListResponse<User> listResponse = userService.getUserList(page, size, querySearch, filters,
        sortBy, sortDir);

    return ResponseEntity.status(HttpStatus.OK).body(listResponse);
  }


  @PostMapping("/admin/lock-user")
  @RolesAllowed("ROLE_ADMIN")
  public ResponseEntity updateLockStatus(@Valid @RequestBody LockUserRequest lockUserRequest)
      throws AlreadyBoundException {

    boolean isLocked = lockUserRequest.getIsLocked();
    User user = userService.updateLockStatus(lockUserRequest.getEmail(),
        isLocked);

    String message = isLocked ? "isLock" : "unLock" + " user successfully";

    return ResponseEntity.status(HttpStatus.OK)
        .body(new CommonResponse<>(message, userMapper.mapToUserDto(user)));
  }

  @PostMapping("/admin/reset-password-by-user")
  @RolesAllowed("ROLE_ADMIN")
  private ResponseEntity resetPasswordByUser(
      @Valid @RequestBody ResetPasswordByUserRequest resetPasswordByUserRequest) {
    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    String newPassword = passwordEncoder.encode(resetPasswordByUserRequest.getNewPassword());

    userService.resetPasswordByUser(newPassword, resetPasswordByUserRequest.getEmail());

    return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse(
        "reset password user: " + resetPasswordByUserRequest.getEmail() + " successfully"));
  }

  @GetMapping("/admin/user-task-statistics")
  @RolesAllowed("ROLE_ADMIN")
  public ResponseEntity getUserTaskStatistics(
      @RequestParam(name = "startDate", required = false, defaultValue = "") @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
      @RequestParam(name = "endDate", required = false, defaultValue = "") @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate) {
    return ResponseEntity.status(HttpStatus.OK)
        .body(userService.getUserTaskStatistics(startDate, endDate));
  }

  @PostMapping("/users/change-password")
  public ResponseEntity changePassword(
      @Valid @RequestBody ChangePasswordRequest changePasswordRequest) throws Exception {

    String password = userService.getUserLogin().getPassword();

    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    String newPassword = passwordEncoder.encode(changePasswordRequest.getNewPassword());

    boolean isMatch = passwordEncoder.matches(changePasswordRequest.getOldPassword(), password);

    if (!isMatch) {
      throw new Exception("Password does not match stored value");
    }

    userService.changePassword(newPassword);

    return ResponseEntity.status(HttpStatus.OK)
        .body(new MessageResponse("Change password successfully"));
  }
}
