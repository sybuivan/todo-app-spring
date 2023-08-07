package com.project.todoapp.payload.response;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class ListResponse<T> {
  private int page;
  private int totalData;
  private List<T> data;
}
