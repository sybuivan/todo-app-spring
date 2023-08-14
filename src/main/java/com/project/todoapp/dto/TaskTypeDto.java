package com.project.todoapp.dto;

import lombok.Getter;
import lombok.Setter;

public interface TaskTypeDto {
  Integer getTypeId();
  String getName();
  Integer getTotalTask();
}
