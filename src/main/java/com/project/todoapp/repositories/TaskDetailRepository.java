package com.project.todoapp.repositories;

import com.project.todoapp.models.TaskDetail;
import com.project.todoapp.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskDetailRepository extends JpaRepository<TaskDetail, Integer> {

  @Query(value = "SELECT EXISTS (SELECT 1 FROM TaskDetail td JOIN td.task t JOIN t.user u "
      + "WHERE td.taskDetailId = :taskDetailId AND u.userId = :userId)", nativeQuery = false)
  boolean existsByTaskDetailIdAndUserId(@Param("taskDetailId") Integer taskDetailId,
      @Param("userId") Integer userId);
}
