package com.project.todoapp.services.mail;

import com.project.todoapp.models.Mail;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import java.nio.charset.StandardCharsets;
import org.springframework.ui.freemarker.SpringTemplateLoader;
import org.thymeleaf.context.Context;

@Service
@AllArgsConstructor
public class MailService implements IMailService {

  private JavaMailSender mailSender;

  @Override
  public void send(Mail mail) throws MessagingException {
    MimeMessage message = mailSender.createMimeMessage();
    MimeMessageHelper helper = new MimeMessageHelper(message, true);
    helper.setTo(mail.getTo());
    helper.setFrom(mail.getFrom());
    helper.setSubject(mail.getSubject());
    helper.setText(mail.getHtmlContent() + mail.setUrlToken(
        (String) mail.getModel().get("resetUrl")), true);
    mailSender.send(message);
  }
}
