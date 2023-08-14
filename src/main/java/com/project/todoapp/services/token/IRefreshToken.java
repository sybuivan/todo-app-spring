package com.project.todoapp.services.token;

import com.project.todoapp.models.RefreshToken;
import java.util.Optional;

public interface IRefreshToken {

  public RefreshToken findByToken(String token);

  RefreshToken createRefreshToken(int userId);

  RefreshToken verifyExpiration(RefreshToken token);

  int deleteByUserId(int userId);

  boolean existsByUserId(int userId);
}
