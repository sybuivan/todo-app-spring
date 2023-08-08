package com.project.todoapp.services.user;

import com.project.todoapp.models.User;
import com.project.todoapp.repositories.UserRepository;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
@Service
public class UserService implements IUserService {

  @Autowired
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
    System.out.println("Email: " + email);
    return userRepository.existsByEmail(email);
  }

  @Override
  public User createUser(User user) {
    return userRepository.save(user);
  }

  @Override
  public List<User> getUserList() {
    return userRepository.findAll();
  }

  @Transactional
  @Override
  public Optional<User> updateLockStatus(String email, boolean isLocked) {
    userRepository.updateLockStatus(email, isLocked);
    return userRepository.findByEmail(email);
  }

  @Override
  public User getUserLogin() {
    UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
        .getPrincipal();
    return this.findByEmail(userDetails.getUsername());
  }
}
