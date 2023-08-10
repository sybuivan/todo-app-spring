package com.project.todoapp.models;

import java.util.Map;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Mail {
  private String from;
  private String to;
  private String subject;
  private Map<String, Object> model;
}
