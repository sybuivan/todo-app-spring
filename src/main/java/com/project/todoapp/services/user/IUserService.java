package com.project.todoapp.services.user;

import com.project.todoapp.dto.UserTaskStatistics;
import com.project.todoapp.models.User;
import com.project.todoapp.payload.response.ListResponse;
import java.rmi.AlreadyBoundException;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;

public interface IUserService<T> {

  T findByEmail(String email);

  boolean existsByUsername(String username);

  boolean existsByEmail(String email);

  T createUser(T user);


  ListResponse<T> getUserList(int page, int size, String querySearch, String filters, String sortBy,
      String sortDir);

  T updateLockStatus(String email, boolean isLocked) throws AlreadyBoundException;

  int changePassword( String newPassword);

  void resetPasswordByUser(String newPassword, String email);

  T getUserLogin();

  List<UserTaskStatistics> getUserTaskStatistics(Date startDate, Date endDate);
}
