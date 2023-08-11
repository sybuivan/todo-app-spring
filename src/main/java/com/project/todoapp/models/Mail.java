package com.project.todoapp.models;

import com.project.todoapp.constants.AppConstants;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;

@Setter
@Getter
public class Mail {

  private String from;
  private String to;
  private String subject;
  private String htmlContent;

  public Mail() {
    this.htmlContent = "<p>This mail reset password</p>";
  }

  public String setUrlToken(String url) {
    String emailContent = "<p>Please visit the link below to change your password: "
        + "<a href='" + AppConstants.BASE_URL_FRONT_END + url + "'>"
        + AppConstants.BASE_URL_FRONT_END + url + "</a></p>";
    return emailContent;
  }

  private Map<String, Object> model;
}
