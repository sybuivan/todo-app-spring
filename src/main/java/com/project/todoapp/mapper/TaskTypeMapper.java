package com.project.todoapp.mapper;

import com.project.todoapp.dto.TaskDetailDto;
import com.project.todoapp.dto.TaskTypeDto;
import com.project.todoapp.dto.UserDto;
import com.project.todoapp.models.TaskDetail;
import com.project.todoapp.models.TaskType;
import com.project.todoapp.models.User;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")

public interface TaskTypeMapper {
  TaskTypeMapper INSTANCE = Mappers.getMapper(TaskTypeMapper.class);

//  @Mapping(source = "taskType.name", target = "nameType")
//  TaskTypeDto taskTypeToDto(TaskType taskType);
}
