package com.project.todoapp.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotBlank;
import java.time.Instant;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "refreshtoken")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RefreshToken {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @NotBlank(message = "Refresh token not empty")
  private String token;
  private Instant expiryDate;

  @OneToOne
  @JoinColumn(name = "user_id", referencedColumnName = "userId")
  private User user;

}

