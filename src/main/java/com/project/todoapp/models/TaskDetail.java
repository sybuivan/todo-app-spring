package com.project.todoapp.models;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;

@Entity
public class TaskDetail {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int taskDetailId;
  private String name;
  private LocalDateTime createdTime;
  private LocalDateTime modifiedTime;

  @ManyToOne
  @JoinColumn(name = "taskId",nullable = false)
  private Task task;
}
