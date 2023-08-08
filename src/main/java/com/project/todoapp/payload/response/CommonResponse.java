package com.project.todoapp.payload.response;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class CommonResponse<T> extends MessageResponse{
  private T data;

  public CommonResponse(@NotNull String message, T data) {
    super(message);
    this.data = data;
  }
}
