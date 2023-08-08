package com.project.todoapp.config;

import com.project.todoapp.services.user.CustomUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@Lazy
public class JwtAuthenticationFilter extends OncePerRequestFilter {

  @Autowired
  private JwtConfig jwtConfig;

  @Autowired
  private CustomUserDetailsService customUserDetailsService;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {
    String jwtToken = parseJwt(request);

    if (jwtToken == null) {
      filterChain.doFilter(request, response);
      return;
    }

    String userEmail = jwtConfig.extractUsername(jwtToken);

    UserDetails userDetails = customUserDetailsService.loadUserByUsername(userEmail);
    boolean isAccountLocked = !userDetails.isAccountNonLocked();

//    check isAccountLocked and send message
    if(isAccountLocked) {
      sendAccountLockedResponse(response);
      return;
    }

    if (jwtConfig.validateToken(jwtToken, userDetails)) {
      UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
          userDetails,
          null, userDetails.getAuthorities());
      authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
      SecurityContextHolder.getContext().setAuthentication(authToken);
    }

    filterChain.doFilter(request, response);
  }

  private String parseJwt(HttpServletRequest request) {
    String headerAuth = request.getHeader("Authorization");

    if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
      return headerAuth.substring(7, headerAuth.length());
    }

    return null;
  }
  private void sendAccountLockedResponse(HttpServletResponse response) throws IOException {
    response.setStatus(HttpStatus.UNAUTHORIZED.value());
    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
    String message = "Account has been locked.";
    response.getWriter().write("{\"message\": \"" + message + "\"}");
    response.getWriter().flush();
  }
}
