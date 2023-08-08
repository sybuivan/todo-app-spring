package com.project.todoapp.mapper;

import com.project.todoapp.dto.TaskDetailDto;
import com.project.todoapp.models.TaskDetail;
import com.project.todoapp.payload.request.TaskDetailRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Qualifier;

@Mapper(componentModel = "spring")

public interface TaskMapper {
  TaskMapper INSTANCE = Mappers.getMapper(TaskMapper.class);
  TaskDetail dtoToTask(TaskDetailRequest taskDetailRequest);
  @Mapping(source = "taskDetail.createdTime", target = "created_at")
  TaskDetailDto taskToDto(TaskDetail taskDetail);
}
