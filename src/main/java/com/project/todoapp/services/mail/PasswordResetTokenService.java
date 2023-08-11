package com.project.todoapp.services.mail;

import com.project.todoapp.repositories.PasswordResetTokenRepository;
import com.project.todoapp.models.PasswordResetToken;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class PasswordResetTokenService implements IPasswordResetTokenService {

  private PasswordResetTokenRepository passwordResetTokenRepository;


  @Override
  public Optional<PasswordResetToken> findByToken(String token) {
    return passwordResetTokenRepository.findByToken(token);
  }

  @Override
  public PasswordResetToken savePasswordToken(PasswordResetToken passwordResetToken) {
    return passwordResetTokenRepository.save(passwordResetToken);
  }

  @Transactional
  @Override
  public int deleteToken(String token) {
    return passwordResetTokenRepository.deleteByToken(token);
  }
}
