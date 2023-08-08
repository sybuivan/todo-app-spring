package com.project.todoapp.payload.response;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class JwtResponse {
  private String token;
  private int id;
  private String username;
  private String email;
  private List<String> roles;
}
