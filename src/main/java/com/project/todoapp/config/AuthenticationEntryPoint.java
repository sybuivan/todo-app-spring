package com.project.todoapp.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.todoapp.exception.RestError;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component("customAuthenticationEntryPoint")
public class AuthenticationEntryPoint implements
    org.springframework.security.web.AuthenticationEntryPoint {

  @Override
  public void commence(HttpServletRequest request, HttpServletResponse response,
      AuthenticationException authException) throws IOException, ServletException {
    RestError re = new RestError(HttpStatus.UNAUTHORIZED.toString(), "Authentication failed");

    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    OutputStream responseStream = response.getOutputStream();

    ObjectMapper mapper = new ObjectMapper();
    mapper.writeValue(responseStream, re);

    responseStream.flush();
  }
}

