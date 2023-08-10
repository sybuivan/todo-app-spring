package com.project.todoapp.repositories;

import com.project.todoapp.models.User;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

  @Modifying
  @Query("Update User u set u.password = :password WHERE u.email = :email")
  int changePassword(@Param("password") String password, @Param("email") String email);

  @Query("SELECT u FROM User u WHERE (u.username LIKE %:querySearch% OR u.email LIKE %:querySearch%) "
      + "AND (:filter = 'ALL' "
      + "     OR (:filter = 'true' AND u.isLocked = true)"
      + "     OR (:filter = 'false' AND u.isLocked = false))")
  Page<User> getUserList(String querySearch, String filter, Pageable pageable);

}
