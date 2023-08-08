package com.project.todoapp.payload.response;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ListResponse<T> {
  private int page;
  private int totalData;
  private int totalPage;
  private int totalCurrentData;
  private List<T> data;
}
