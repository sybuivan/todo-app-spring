package com.project.todoapp.repositories;

import com.project.todoapp.dto.TaskTypeDto;
import com.project.todoapp.models.TaskType;
import com.project.todoapp.models.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskTypeRepository extends JpaRepository<TaskType, Integer> {

  @Query(value = "SELECT tt.name AS name, tt.typeId AS typeId, COUNT(t.taskId) AS totalTask "
      + "FROM TaskType tt "
      + "LEFT JOIN Task t ON tt.typeId = t.taskType.typeId "
      + "WHERE tt.user = :user "
      + "GROUP BY tt.name, tt.typeId")
  List<TaskTypeDto> findByUser(User user);

  boolean existsByNameAndUser(String name, User user);

  TaskType findByUserAndTypeId(User user, int typeId);
}
