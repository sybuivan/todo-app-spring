package com.project.todoapp.services.mail;

import com.project.todoapp.models.PasswordResetToken;
import java.util.Optional;

public interface IPasswordResetTokenService {
  Optional<PasswordResetToken> findByToken(String token);
  PasswordResetToken savePasswordToken(PasswordResetToken passwordResetToken);
  int deleteToken(String token);
}
