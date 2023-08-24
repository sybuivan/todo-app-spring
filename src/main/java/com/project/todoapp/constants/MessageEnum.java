package com.project.todoapp.constants;

public enum MessageEnum {
  REQUIRED("%s is a required field."),
  CREATE("Create %s successfully"),
  NOT_FOUND("The %s with ID %s was not found."),
  ALREADY_EXIST("%s is already taken"),
  UPDATE("Update %s successfully"),
  ERROR("An error occurred. %s"),
  SUCCESS("Operation successful.");

  private final String message;

  MessageEnum(String message) {
    this.message = message;
  }

  public String getMessage() {
    return message;
  }

  public String getFormattedMessage(Object fieldName, Object args2) {
    return String.format(message, fieldName, args2);
  }

  public String getFormattedField(Object field){
    return String.format(message, field);
  }
}
