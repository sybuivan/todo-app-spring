package com.project.todoapp.repositories;

import com.project.todoapp.models.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Integer> {
  RefreshToken findByToken(String token);

  int deleteByUserUserId(int userId);

  boolean existsByUserUserId(int userId);
}
