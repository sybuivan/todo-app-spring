package com.project.todoapp.repositories;

import com.project.todoapp.models.Task;
import com.project.todoapp.models.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<Task, Integer> {
   boolean existsByTaskIdAndUser(int taskId, User user);
   List<Task> findByUser(User user);
}
