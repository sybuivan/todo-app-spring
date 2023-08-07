package com.project.todoapp.controllers;

import com.project.todoapp.constants.Common;
import com.project.todoapp.exception.ResourceNotFoundException;
import com.project.todoapp.models.User;
import com.project.todoapp.payload.request.TaskRequest;
import com.project.todoapp.payload.response.CommonResponse;
import com.project.todoapp.payload.response.ListResponse;
import com.project.todoapp.services.user.IUserService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/admin")
public class UserController {

  private IUserService userService;
  @GetMapping("/users")
  @RolesAllowed("ROLE_ADMIN")
  public ResponseEntity getUserList() {
    List<User> userList = userService.getUserList();
    ListResponse listResponse = new ListResponse<>(Common.PAGE, userList.size(), userList);
    return ResponseEntity.status(HttpStatus.OK).body(listResponse);
  }

  @PostMapping("/lock-user/{email}")
  public ResponseEntity updateLockStatus(@PathVariable String email) {

    if(!userService.existsByEmail(email)) {
      throw new ResourceNotFoundException("User not found with email");
    }

    userService.updateLockStatus(email, true);

    return null;
  }
}
