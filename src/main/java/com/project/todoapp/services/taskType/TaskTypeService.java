package com.project.todoapp.services.taskType;

import com.project.todoapp.dto.TaskTypeDto;
import com.project.todoapp.models.TaskType;
import com.project.todoapp.models.User;
import com.project.todoapp.repositories.TaskTypeRepository;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TaskTypeService implements ITaskType<TaskType, User> {

  private TaskTypeRepository taskTypeRepository;

  @Override
  public TaskType createTaskType(TaskType taskType) {
    return taskTypeRepository.save(taskType);
  }

  @Override
  public boolean isExitsTaskType(User user, String name) {
    return taskTypeRepository.existsByNameAndUser(name, user);
  }

  @Override
  public TaskType findTypeById(User user, int typeId) {
    return taskTypeRepository.findByUserAndTypeId(user,typeId);
  }

  @Override
  public List<TaskTypeDto> getTypeTaskList(User user) {
    return taskTypeRepository.findByUser(user);
  }
}
