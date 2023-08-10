package com.project.todoapp.controllers;

import com.project.todoapp.constants.MessageEnum;
import com.project.todoapp.exception.ResourceNotFoundException;
import com.project.todoapp.models.Mail;
import com.project.todoapp.models.PasswordResetToken;
import com.project.todoapp.models.User;
import com.project.todoapp.payload.request.PasswordForgot;
import com.project.todoapp.payload.response.MessageResponse;
import com.project.todoapp.services.mail.IMailService;
import com.project.todoapp.services.mail.IPasswordResetTokenService;
import com.project.todoapp.services.user.IUserService;
import jakarta.validation.Valid;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/auth")
public class ForgotPasswordController {

  private IUserService userService;
  private IMailService emailService;
  private IPasswordResetTokenService passwordResetTokenService;

  @PostMapping("/forgot-password")
  ResponseEntity processPasswordForgot(@Valid @RequestBody PasswordForgot passwordForgot)
      throws Exception {

    User user = userService.findByEmail(passwordForgot.getEmail());
    if (user == null) {
      throw new ResourceNotFoundException(
          MessageEnum.NOT_FOUND.getFormattedMessage("email", passwordForgot.getEmail()));
    }

    PasswordResetToken token = new PasswordResetToken();
    token.setUser(user);
    token.setToken(UUID.randomUUID().toString());
    token.setExpirationDate(LocalDateTime.now().plusMinutes(30));

    token = passwordResetTokenService.savePasswordToken(token);

    if (token == null) {
      throw new Exception("Token not save");
    }

    Mail mail = new Mail();
    String url = "http://localhost:8081/api/v1/auth/reset-password?token=" + token.getToken();

    mail.setFrom("sybuivan1429@gmail.com");
    mail.setTo(user.getEmail());
    mail.setSubject("Password reset request");

    Map<String, Object> mailModel = new HashMap<>();

    mailModel.put("token", token);
    mailModel.put("user", user);
    mailModel.put("signature", "http://mohyehia.com");
    mailModel.put("resetUrl", url);
    mail.setModel(mailModel);

    emailService.send(mail);
    return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse(url));
  }
}
