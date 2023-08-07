package com.project.todoapp.repositories;

import com.project.todoapp.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Integer> {
  Role findByName(String name);
}
