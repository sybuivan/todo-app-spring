package com.project.todoapp.exception;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
@AllArgsConstructor
public class ErrorDetails {
  private Date timestamp;
  private String message;
  private String details;
}
