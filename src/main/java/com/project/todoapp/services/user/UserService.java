package com.project.todoapp.services.user;

import com.project.todoapp.constants.MessageEnum;
import com.project.todoapp.dto.UserTaskStatistics;
import com.project.todoapp.exception.ResourceNotFoundException;
import com.project.todoapp.mapper.UserMapper;
import com.project.todoapp.models.User;
import com.project.todoapp.payload.request.UpdateInfoRequest;
import com.project.todoapp.payload.response.ListResponse;
import com.project.todoapp.repositories.UserRepository;
import com.project.todoapp.utils.PageableCommon;
import java.rmi.AlreadyBoundException;
import java.util.Date;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
@Service
public class UserService implements IUserService<User> {

  private UserRepository userRepository;
  private PageableCommon pageableCommon;
  private UserMapper userMapper;

  @Transactional
  @Override
  public User findByEmail(String email) {
    return userRepository.findByEmail(email).orElse(null);
  }

  @Override
  public boolean isUsernameTaken(String userName) {
    return userRepository.existsByUsername(userName);
  }

  @Override
  public boolean isEmailTaken(String email) {
    return userRepository.existsByEmail(email);
  }

  @Override
  public User createUser(User user) {
    return userRepository.save(user);
  }

  @Override
  public ListResponse<User> getUserList(int page, int size, String querySearch, String filters,
      String sortBy,
      String sortDir) {


    Pageable pageable = pageableCommon.getPageable(page, size, sortBy, sortDir);
    Page<User> userPage = userRepository.getUserList(querySearch, filters, pageable);

    List<User> userList = userPage.getContent();

    ListResponse listResponse = new ListResponse<>();

    listResponse.setTotalData(
        userPage.getContent().size() == 0 ? 0 : (int) userPage.getTotalElements());
    listResponse.setTotalPage(
        userPage.getContent().size() == 0 ? 0 : (int) userPage.getTotalPages());
    listResponse.setPage(page);
    listResponse.setData(userMapper.toUsersDto(userList));
    listResponse.setTotalCurrentData(userList.size());

    return listResponse;
  }

  @Transactional
  @Override
  public User updateLockStatus(String email, boolean isLocked)
      throws AlreadyBoundException {
    User user = this.findByEmail(email);

    if (user == null) {
      throw new ResourceNotFoundException(
          MessageEnum.NOT_FOUND.getFormattedMessage("email", email));
    }

    System.out.println("isLocked: " + isLocked);

    if (user.isLocked() == isLocked) {
      throw new AlreadyBoundException("Email: " + email + (isLocked ? " is lock" : " not locked"));
    }
    userRepository.updateLockStatus(email, isLocked);
    user.setLocked(isLocked);

    return user;
  }

  @Override
  public int changePassword(String newPassword) {
    User user = this.getUserLogin();
    user.setPassword(newPassword);

    userRepository.save(user);
    return 0;
  }

  @Override
  @Transactional
  public User updateUserInfo(UpdateInfoRequest user) throws AlreadyBoundException {
    String email = this.getUserLogin().getEmail();
    User userFound = this.findByEmail(email);

    if (userFound == null) {
      throw new ResourceNotFoundException(
          MessageEnum.NOT_FOUND.getFormattedMessage("email", email));
    }

    boolean isEqualUserName = userFound.getUsername().equals(user.getUsername());

    if (!isEqualUserName) {
      if (this.isUsernameTaken(user.getUsername())) {
        throw new AlreadyBoundException(MessageEnum.ALREADY_EXIST.getFormattedField("Username"));
      }
    }

    userFound.setUsername(user.getUsername());
    userFound.setLastName(user.getLastName());
    userFound.setFirstName(user.getFirstName());

    return userRepository.save(userFound);
  }

  @Override
  public boolean resetPasswordByUser(String newPassword, String email) {
    User user = this.findByEmail(email);
    if (user == null) {
      throw new ResourceNotFoundException(
          MessageEnum.NOT_FOUND.getFormattedMessage("email", email));
    }

    user.setPassword(newPassword);

    userRepository.save(user);
    return true;
  }

  @Override
  public User getUserLogin() {
    UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
        .getPrincipal();
    return this.findByEmail(userDetails.getUsername());
  }

  @Override
  public List<UserTaskStatistics> getUserTaskStatistics(Date startDate, Date endDate) {
    return userRepository.getUserTaskStatistics(startDate, endDate);
  }
}
