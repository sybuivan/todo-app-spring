package com.project.todoapp.models;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.Email;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.UniqueElements;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

@Entity
@Table(uniqueConstraints = {
    @UniqueConstraint(columnNames = {"username", "email"})
}, name = "users")
@Getter
@Setter
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int userId;
  private String username;
  private String password;
  private String firstName;
  private String lastName;
  @Email
  private String email;

  private boolean isLocked;

  @CreationTimestamp
  private LocalDateTime createdTime;

  @LastModifiedDate
  private LocalDateTime modifiedTime;

  @OneToMany(mappedBy = "user")
  private List<Task> taskList;

  @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
  @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id",
      referencedColumnName = "userId"), inverseJoinColumns = @JoinColumn(name = "role_id",
      referencedColumnName = "roleId")
  )
  private Set<Role> roles;
}
