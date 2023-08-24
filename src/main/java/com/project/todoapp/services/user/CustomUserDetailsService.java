package com.project.todoapp.services.user;

import com.project.todoapp.models.User;
import com.project.todoapp.repositories.UserRepository;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

  private UserRepository userRepository;
  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    User user = userRepository.findByEmail(email).orElse(null);

    System.out.println("Vao loadUser");

    boolean enabled = true;
    boolean accountNonExpired = true;
    boolean credentialsNonExpired = true;
    boolean isAccountNonLocked = !user.isLocked();

    Set<GrantedAuthority> authorities = user
        .getRoles()
        .stream()
        .map((role) -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toSet());

    return new org.springframework.security.core.userdetails.User(user.getEmail(),
        user.getPassword(),
        enabled,
        accountNonExpired,
        credentialsNonExpired,
        isAccountNonLocked,
        authorities);
  }
}
