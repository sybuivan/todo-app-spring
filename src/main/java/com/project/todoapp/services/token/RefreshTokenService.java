package com.project.todoapp.services.token;

import com.project.todoapp.constants.MessageEnum;
import com.project.todoapp.exception.ResourceNotFoundException;
import com.project.todoapp.exception.TokenRefreshException;
import com.project.todoapp.models.RefreshToken;
import com.project.todoapp.repositories.RefreshTokenRepository;
import com.project.todoapp.repositories.UserRepository;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
//@AllArgsConstructor
public class RefreshTokenService implements IRefreshToken {

//  @Value("${jwt.refreshExpirationMs}")
  private Long refreshTokenDurationMs =20000L;

  @Autowired
  private RefreshTokenRepository refreshTokenRepository;
  @Autowired
  private UserRepository userRepository;

  @Override
  public RefreshToken findByToken(String token) {
    RefreshToken refreshToken = refreshTokenRepository.findByToken(token);
    if (refreshToken == null) {
      throw new ResourceNotFoundException(
          MessageEnum.NOT_FOUND.getFormattedMessage("token", token));
    }

    return refreshToken;
  }

  @Override
  public RefreshToken createRefreshToken(int userId) {
    RefreshToken refreshToken = new RefreshToken();

    refreshToken.setUser(userRepository.findById(userId).get());
    refreshToken.setExpiryDate(Instant.now().plusMillis(refreshTokenDurationMs));
    refreshToken.setToken(UUID.randomUUID().toString());

    refreshToken = refreshTokenRepository.save(refreshToken);
    return refreshToken;
  }

  @Override
  public RefreshToken verifyExpiration(RefreshToken token) {
    if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
      refreshTokenRepository.delete(token);
      throw new TokenRefreshException(token.getToken(),
          "Refresh token was expired. Please make a new sign in request");
    }
    return token;
  }

  @Transactional
  @Override
  public int deleteByUserId(int userId) {
    return refreshTokenRepository.deleteByUserUserId(userId);
  }

  @Override
  public boolean existsByUserId(int userId) {
    return refreshTokenRepository.existsByUserUserId(userId);
  }
}
