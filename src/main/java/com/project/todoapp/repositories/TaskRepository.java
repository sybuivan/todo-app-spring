package com.project.todoapp.repositories;

import com.project.todoapp.models.Task;
import com.project.todoapp.models.User;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<Task, Integer> {
   boolean existsByTaskIdAndUser(int taskId, User user);

   @Query(value = "SELECT t1_0.* FROM tasks t1_0 WHERE t1_0.user_id = :userId " +
       "AND t1_0.name like %:name%" +
       "AND (CASE WHEN :typeId = 0 THEN 1 ELSE t1_0.type_id = :typeId END) " +
       "AND (CASE WHEN :filter = 'ALL' THEN 1 " +
       "WHEN :filter = 'COMPLETED' AND t1_0.complete_date IS NOT NULL THEN 1 " +
       "WHEN :filter = 'INCOMPLETE' AND t1_0.complete_date IS NULL THEN 1 " +
       "ELSE 0 END) = 1",
       countQuery = "SELECT COUNT(*) FROM tasks t1_0 WHERE t1_0.user_id = :userId  " +
           "AND (CASE WHEN :filter = 'ALL' THEN 1 " +
           "WHEN :filter = 'COMPLETED' AND t1_0.complete_date IS NOT NULL THEN 1 " +
           "WHEN :filter = 'INCOMPLETE' AND t1_0.complete_date IS NULL THEN 1 " +
           "ELSE 0 END) = 1",
       nativeQuery = true)
   Page<Task> findTasksByUserWithFilter(int userId, String name, int typeId, String filter, Pageable pageable);
}
