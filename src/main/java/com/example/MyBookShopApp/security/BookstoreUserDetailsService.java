package com.example.MyBookShopApp.security;

import com.example.MyBookShopApp.data.struct.user.UserEntity;
import com.example.MyBookShopApp.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class BookstoreUserDetailsService implements UserDetailsService {

  public final UserService userService;

  @Autowired
  public BookstoreUserDetailsService( UserService userService ) {
    this.userService = userService;
  }

  @Override
  public UserDetails loadUserByUsername( String userEmail ) throws UsernameNotFoundException {
    UserEntity userEntity = userService.getUserByEmail( userEmail );
    if ( userEntity != null ) {
      return new BookstoreUserDetails( userEntity );
    } else {
      log.info( "user not found doh!" );
      throw new UsernameNotFoundException( "user not found doh!" );
    }
  }
}
