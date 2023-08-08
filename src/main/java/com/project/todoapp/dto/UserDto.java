package com.project.todoapp.dto;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

  @Id
  private int userId;
  private String username;
  private String email;
  private String firstName;
  private String lastName;
  private boolean isLocked;
}
