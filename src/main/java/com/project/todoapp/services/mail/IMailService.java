package com.project.todoapp.services.mail;

import com.project.todoapp.models.Mail;
import jakarta.mail.MessagingException;

public interface IMailService {
  void send(Mail mail) throws MessagingException;
}
