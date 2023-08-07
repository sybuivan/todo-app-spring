package com.project.todoapp.repositories;

import com.project.todoapp.models.Task;
import com.project.todoapp.models.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Integer> {
   boolean existsByTaskId(int taskId);
   List<Task> findByUser(User user);
}
