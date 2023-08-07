package com.project.todoapp.services.user;

import com.project.todoapp.models.User;
import com.project.todoapp.repositories.UserRepository;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class UserService implements IUserService {

  private UserRepository userRepository;

  @Override
  public User findByEmail(String email) {
    return userRepository.findByEmail(email).orElse(null);
  }

  @Override
  public boolean existsByUsername(String userName) {
    return userRepository.existsByUsername(userName);
  }

  @Override
  public boolean existsByEmail(String email) {
    return userRepository.existsByUsername(email);
  }

  @Override
  public User createUser(User user) {
    return userRepository.save(user);
  }

  @Override
  public List<User> getUserList() {
    return userRepository.findAll();
  }

  @Override
  public ResponseEntity updateLockStatus(String email, boolean isLocked) {
    return null;
  }
}
