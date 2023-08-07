package com.project.todoapp.constants;

public enum MessageEnum {
  REQUIRED("%s is a required field."),
  NOT_FOUND("The %s with ID %d was not found."),
  SUCCESS("Operation successful.");

  private final String message;

  MessageEnum(String message) {
    this.message = message;
  }

  public String getMessage() {
    return message;
  }

  public String getFormattedMessage(String fieldName) {
    return fieldName + message;
  }
}
