package com.example.MyBookShopApp.security;

import com.example.MyBookShopApp.data.struct.user.UserEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;

@Slf4j
public class BookstoreUserDetails implements UserDetails {

  private final UserEntity userEntity;

  public BookstoreUserDetails( UserEntity userEntity ) {
    this.userEntity = userEntity;
  }

  public UserEntity getUserEntity() {
    return userEntity;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    Collection<? extends GrantedAuthority> test = Arrays.asList( new SimpleGrantedAuthority("ROLE_USER" ) );
    log.info( "GrantedAuthority  = " + test );
    return test;
  }

  @Override
  public String getPassword() {
    log.info( "BookstoreUserDetails getPassword = " + userEntity.getUserContact().getCode() );
    return userEntity.getUserContact().getCode();
  }

  @Override
  public String getUsername() {
    log.info( "BookstoreUserDetails getUsername = " + userEntity.getUserContact().getContact() );
    return userEntity.getUserContact().getContact();
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }
}
