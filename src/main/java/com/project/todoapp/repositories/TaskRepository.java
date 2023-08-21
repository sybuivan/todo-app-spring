package com.project.todoapp.repositories;

import com.project.todoapp.dto.ITaskDto;
import com.project.todoapp.models.Task;
import com.project.todoapp.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<Task, Integer> {
   boolean existsByTaskIdAndUser(int taskId, User user);

   @Query(value = "SELECT t.name as name, t.task_id as taskId,"
       + "t.due_date as dueDate, tt.name as taskTypeName, t.complete_date as completeDate,"
       + " COUNT(td.task_detail_id) as totalSubTasks " +
       "FROM tasks t " +
       "LEFT JOIN task_detail td ON t.task_id = td.task_id " +
       "LEFT JOIN task_type tt ON t.type_id = tt.type_id " +
       "WHERE t.user_id = :userId " +
       "AND t.name LIKE %:name% " +
       "AND (CASE WHEN :typeId = 0 THEN 1 ELSE t.type_id = :typeId END) " +
       "AND (CASE WHEN :filter = 'ALL' THEN 1 " +
       "WHEN :filter = 'COMPLETED' AND t.complete_date IS NOT NULL THEN 1 " +
       "WHEN :filter = 'INCOMPLETE' AND t.complete_date IS NULL THEN 1 " +
       "ELSE 0 END) = 1 " +
       "GROUP BY t.task_id",
       countQuery = "SELECT COUNT(*) FROM tasks t WHERE t.user_id = :userId " +
           "AND (CASE WHEN :filter = 'ALL' THEN 1 " +
           "WHEN :filter = 'COMPLETED' AND t.complete_date IS NOT NULL THEN 1 " +
           "WHEN :filter = 'INCOMPLETE' AND t.complete_date IS NULL THEN 1 " +
           "ELSE 0 END) = 1",
       nativeQuery = true)
   Page<ITaskDto> findTasksByUserWithFilter(int userId, String name, int typeId, String filter, Pageable pageable);


   int deleteByTaskIdAndUser(int taskId, User user);

}
