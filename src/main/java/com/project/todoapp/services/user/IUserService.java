package com.project.todoapp.services.user;

import com.project.todoapp.models.User;
import java.util.List;
import java.util.Optional;

public interface IUserService {
  User findByEmail(String email);
  boolean existsByUsername(String username);
  boolean existsByEmail(String email);
  User createUser (User user);
  List<User> getUserList();

  Optional<User> updateLockStatus(String email, boolean isLocked);

  User getUserLogin();
}
