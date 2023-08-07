package com.project.todoapp.payload.request;

import com.project.todoapp.constants.MessageEnum;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterRequest {

  //  @NotBlank(message = MessageEnum.REQUIRED.getFormattedMessage("username"))
  @NotBlank(message = "username is a required field.")
  private String username;

  @NotBlank(message = "password is a required field.")
  private String password;

  @NotBlank(message = "firstName is a required field.")
  private String firstName;

  @NotBlank(message = "lastName is a required field.")
  private String lastName;

  @NotBlank(message = "email is a required field.")
  @Email(message = "Incorrect email format")
  private String email;
}
