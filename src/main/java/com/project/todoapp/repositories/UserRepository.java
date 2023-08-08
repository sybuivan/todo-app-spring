package com.project.todoapp.repositories;

import com.project.todoapp.models.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
  Optional<User> findByEmail(String email);

  boolean existsByEmail(String email);

  boolean existsByUsername(String username);

  @Modifying
  @Query("UPDATE User u SET u.isLocked = :isLocked WHERE u.email = :email")
  int updateLockStatus(@Param("email") String email, @Param("isLocked") boolean isLocked);

}
