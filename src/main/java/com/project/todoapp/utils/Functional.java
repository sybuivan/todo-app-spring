package com.project.todoapp.utils;

import org.springframework.stereotype.Component;

@Component
public class Functional {
  public boolean checkStringForEquality(String field1, String field2) {
    return field1.equals(field2);
  }
}
