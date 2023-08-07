package com.project.todoapp.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.LastModifiedDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "tasks")
public class Task {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int taskId;
  private String name;
  private Date completeDate;

  @CreationTimestamp
  private LocalDateTime createdTime;

  @LastModifiedDate
  private LocalDateTime modifiedTime;

  @ManyToOne
  @JoinColumn(name = "userId", nullable = false)
  @JsonIgnore
  private User user;

  @OneToMany(mappedBy = "task")
  private List<TaskDetail> taskDetailList;
}
