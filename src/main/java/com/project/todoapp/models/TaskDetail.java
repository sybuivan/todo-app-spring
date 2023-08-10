package com.project.todoapp.models;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Setter
@Getter
@EntityListeners(AuditingEntityListener.class)
public class TaskDetail {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int taskDetailId;
  @NotBlank(message = "Name is a required field")
  private String name;

  @CreationTimestamp
  private LocalDateTime createdTime;

  @LastModifiedDate
  private LocalDateTime modifiedTime;

  @JsonBackReference
  @ManyToOne
  @JoinColumn(name = "taskId", nullable = false)
  private Task task;
}
