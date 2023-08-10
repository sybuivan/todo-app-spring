package com.project.todoapp.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
public class PasswordResetToken {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private int id;
  @Column(nullable = false, unique = true)
  private String token;
  @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
  private User user;
  @Column(nullable = false)
  private LocalDateTime expirationDate;
}
