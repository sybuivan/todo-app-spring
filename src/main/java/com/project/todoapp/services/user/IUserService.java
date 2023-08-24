package com.project.todoapp.services.user;

import com.project.todoapp.dto.UserTaskStatistics;
import com.project.todoapp.payload.request.UpdateInfoRequest;
import com.project.todoapp.payload.response.ListResponse;
import jakarta.validation.Valid;
import java.rmi.AlreadyBoundException;
import java.util.Date;
import java.util.List;

public interface IUserService<T> {

  T findByEmail(String email);


  boolean isUsernameTaken(String username);

  boolean isEmailTaken(String email);

  T createUser(T user);
  ListResponse<T> getUserList(int page, int size, String querySearch, String filters, String sortBy,
      String sortDir);

  T updateLockStatus(String email, boolean isLocked) throws AlreadyBoundException;

  int changePassword( String newPassword);

  T updateUserInfo(UpdateInfoRequest user) throws AlreadyBoundException;
  T getUserLogin();

  boolean resetPasswordByUser(String newPassword, String email);
  List<UserTaskStatistics> getUserTaskStatistics(Date startDate, Date endDate);
}
