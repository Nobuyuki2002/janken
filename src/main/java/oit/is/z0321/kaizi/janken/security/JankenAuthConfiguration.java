package oit.is.z0321.kaizi.janken.security;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
public class JankenAuthConfiguration {
  @Bean
  public InMemoryUserDetailsManager userDetailsService() {

    UserBuilder users = User.builder();

    // password: isdev
    UserDetails user1 = users
        .username("user1")
        .password("$2y$10$b9IkZbbPr7xHRuMUnwa9vuvAndWg9EhPRBuVouw.JOa0iJXyrtGgy")
        .roles("USER")
        .build();
    // password: isdev
    UserDetails user2 = users
        .username("user2")
        .password("$2y$10$tZItx1bUWsrcPz9q.JlYsunwlr0hF5.2IAJFTzuLv4b0p6STgEUpu")
        .roles("USER")
        .build();

    return new InMemoryUserDetailsManager(user1, user2);
  }

  @Bean
  PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http.formLogin();

    http.authorizeHttpRequests()
        .mvcMatchers("/janken/**").authenticated();

    http.logout().logoutSuccessUrl("/");
    return http.build();
  }
}
