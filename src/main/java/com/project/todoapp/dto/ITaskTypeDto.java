package com.project.todoapp.dto;

import lombok.Getter;
import lombok.Setter;

public interface ITaskTypeDto {
  Integer getTypeId();
  String getName();
  Integer getTotalTask();
}
