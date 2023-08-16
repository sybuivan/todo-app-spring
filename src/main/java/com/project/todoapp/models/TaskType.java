package com.project.todoapp.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotEmpty;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TaskType {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int typeId;
  @NotEmpty(message = "name is not empty")
  private String name;


  @JsonManagedReference
  @OneToMany(mappedBy = "taskType", cascade = CascadeType.ALL,fetch = FetchType.EAGER, orphanRemoval = true)
  private List<Task> taskList;

  @ManyToOne
  @JoinColumn(name = "userId", nullable = false)
  @JsonIgnore
  private User user;
  
}
