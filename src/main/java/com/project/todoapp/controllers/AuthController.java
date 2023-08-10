package com.project.todoapp.controllers;

import com.project.todoapp.config.JwtConfig;
import com.project.todoapp.constants.StatusEnum;
import com.project.todoapp.exception.ResourceNotFoundException;
import com.project.todoapp.models.Role;
import com.project.todoapp.models.User;
import com.project.todoapp.payload.request.LoginRequest;
import com.project.todoapp.payload.request.RegisterRequest;
import com.project.todoapp.payload.response.CommonResponse;
import com.project.todoapp.payload.response.JwtResponse;
import com.project.todoapp.payload.response.MessageResponse;
import com.project.todoapp.repositories.RoleRepository;
import com.project.todoapp.services.user.IUserService;
import jakarta.validation.Valid;
import java.rmi.AlreadyBoundException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@AllArgsConstructor
public class AuthController {

  private IUserService userService;
  private RoleRepository roleRepository;
  private PasswordEncoder passwordEncoder;
  private AuthenticationManager authenticationManager;
  private JwtConfig jwtConfig;

  @PostMapping("/register")
  public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterRequest registerRequest)
      throws AlreadyBoundException {

    // add check for email exists in DB
    if (userService.existsByEmail(registerRequest.getEmail())) {
      throw new AlreadyBoundException("Email is already taken!");
    }

    if (userService.existsByUsername(registerRequest.getUsername())) {
      throw new AlreadyBoundException("Username is already taken!");
    }

    User user = new User();
    user.setFirstName(registerRequest.getFirstName());
    user.setLastName(registerRequest.getLastName());
    user.setUsername(registerRequest.getUsername());
    user.setEmail(registerRequest.getEmail());
    user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));

    Role roles = roleRepository.findByName(
        com.project.todoapp.constants.Role.ROLE_USER);

    user.setRoles(Collections.singleton(roles));
    User userResult = userService.createUser(user);
    CommonResponse<User> userCommonResponse =
        new CommonResponse<>("User registered successfully", userResult);

    return ResponseEntity.status(HttpStatus.CREATED).body(userCommonResponse);
  }

  @PostMapping("/login")
  public ResponseEntity login(@Valid @RequestBody LoginRequest loginRequest) {
    User user = userService.findByEmail(loginRequest.getEmail());

    if (user == null) {
      throw new ResourceNotFoundException("User not found with userName or email");
    }

    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    boolean isMatch = passwordEncoder.matches(loginRequest.getPassword(), user.getPassword());

    if (isMatch) {
      Authentication authentication = authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(loginRequest.getEmail(),
              loginRequest.getPassword()));

      SecurityContextHolder.getContext().setAuthentication(authentication);

      List<String> roleList = authentication.getAuthorities().stream()
          .map(GrantedAuthority::getAuthority)
          .collect(Collectors.toList());

      final String token = jwtConfig.generateToken(loginRequest.getEmail(), roleList);

//      boolean isExitsUserToken = refreshTokenService.existsByUserId(user.get().getId());
//      if (isExitsUserToken) {
//        refreshTokenService.deleteByUserId(user.get().getId());
//      }

//      RefreshToken refreshToken = refreshTokenService.createRefreshToken(user.get().getId());

//      Claims claims = jwtConfig.extractAllClaims(token);

      return ResponseEntity.ok(
          new JwtResponse(token,user.getUserId(), user.getUsername(), user.getEmail(), roleList));

    }
    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body(new MessageResponse("Password does not match stored value"));
  }
}
