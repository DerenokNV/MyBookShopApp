package com.example.MyBookShopApp.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity(debug = true)
@Slf4j
public class SecurityConfig {
  private final BookstoreUserDetailsService bookstoreUserDetailsService;

  @Autowired
  public SecurityConfig( BookstoreUserDetailsService bookstoreUserDetailsService ) {
    this.bookstoreUserDetailsService = bookstoreUserDetailsService;
  }

  /*@Bean
  public UserDetailsService customUserDetailsService() {
    return bookstoreUserDetailsService;
  }*/

  /*@Bean
  public DaoAuthenticationProvider authenticationProvider() {
    DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
    authProvider.setUserDetailsService( bookstoreUserDetailsService );
    authProvider.setPasswordEncoder( getPasswordEncoder() );
    return authProvider;
  }*/

  @Bean
  public AuthenticationManager authenticationManager( AuthenticationConfiguration authenticationConfiguration ) throws Exception {
    return authenticationConfiguration.getAuthenticationManager();
  }

  @Bean
  PasswordEncoder getPasswordEncoder(){
    return new BCryptPasswordEncoder();
  }

  @Bean
  //public SecurityFilterChain securityFilterChain( AuthenticationManager authenticationManager, HttpSecurity http ) throws Exception {
  public SecurityFilterChain securityFilterChain( HttpSecurity http ) throws Exception {
    http    //.csrf( csrf -> csrf.disable() )
            .authorizeHttpRequests( authorize -> {
              try {
                authorize
                        .requestMatchers("/my", "/profile").hasRole("USER")
                        .requestMatchers("/**").permitAll();
              } catch ( Exception e) {
                log.info( "ERRRRRR = "+ e.getMessage() );
              }
            })
            .formLogin( form -> form
               .loginPage( "/signin" )
               .failureUrl( "/signin" )
            );

    //http.authenticationProvider( authenticationProvider() );
    //http.authenticationManager( authenticationManager );

    return http.build();
  }

}
