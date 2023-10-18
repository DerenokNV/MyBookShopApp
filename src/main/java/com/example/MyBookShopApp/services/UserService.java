package com.example.MyBookShopApp.services;

import com.example.MyBookShopApp.data.repository.UserContactEntityRepository;
import com.example.MyBookShopApp.data.repository.UserEntityRepository;
import com.example.MyBookShopApp.data.struct.user.UserContactEntity;
import com.example.MyBookShopApp.data.struct.user.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserService {

  private final UserEntityRepository userEntityRepository;
  private final UserContactEntityRepository userContactEntityRepository;

  @Autowired
  public UserService( UserEntityRepository userEntityRepository, UserContactEntityRepository userContactEntityRepository ) {
    this.userEntityRepository = userEntityRepository;
    this.userContactEntityRepository = userContactEntityRepository;
  }

  public UserEntity getUserByEmail( String userEmail ) {
    UserEntity result = userEntityRepository.customGetUserByEmail( userEmail );
    if ( result == null ) {
      return null;
    }
    UserContactEntity userContact = userContactEntityRepository.findUserContactByUserId( result.getId() );
    if ( userContact != null ) {
      result.setUserContact( userContact );
    }

    return result;
  }

}
