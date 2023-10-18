package com.example.MyBookShopApp.services;

import com.example.MyBookShopApp.data.repository.UserContactEntityRepository;
import com.example.MyBookShopApp.data.repository.UserEntityRepository;
import com.example.MyBookShopApp.data.struct.enums.ContactType;
import com.example.MyBookShopApp.data.struct.user.UserContactEntity;
import com.example.MyBookShopApp.data.struct.user.UserEntity;
import com.example.MyBookShopApp.security.ContactConfirmationPayload;
import com.example.MyBookShopApp.security.ContactConfirmationResponse;
import com.example.MyBookShopApp.security.RegistrationForm;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDateTime;

@Service
@Slf4j
public class BookstoreUserRegister {

    private final UserEntityRepository userEntityRepository;
    private final UserContactEntityRepository userContactEntityRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;


    @Autowired
    public BookstoreUserRegister( UserEntityRepository userEntityRepository, UserContactEntityRepository userContactEntityRepository,
                                  PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager ) {
        this.userEntityRepository = userEntityRepository;
        this.userContactEntityRepository = userContactEntityRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

    @Transactional
    public void registerNewUser( RegistrationForm registrationForm ) {
        if ( userEntityRepository.customGetUserByEmail( registrationForm.getEmail() ) != null ) {
            return;
        }

        UserEntity user = new UserEntity();
        user.setName( registrationForm.getName() );
        user.setHash( "user_aut" + Math.random() );
        user.setRegTime( LocalDateTime.now() );
        user.setBalance( 0 );
        Integer newIdUser = userEntityRepository.save( user ).getId();

        UserContactEntity userContact = new UserContactEntity();
        userContact.setUserId( newIdUser );
        userContact.setType(ContactType.EMAIL );
        userContact.setApproved( (short)0 );
        userContact.setCode( passwordEncoder.encode(registrationForm.getPassword()) );
        //userContact.setCodeTrails( 1 );
        //userContact.setCodeTime( LocalDateTime.now() );
        userContact.setContact( registrationForm.getEmail() );
        userContactEntityRepository.save( userContact );
    }

    public ContactConfirmationResponse login( ContactConfirmationPayload payload ) {
      ContactConfirmationResponse response = new ContactConfirmationResponse();
      try {
          Authentication authentication =
                  authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(payload.getContact(), payload.getCode()));

          SecurityContextHolder.getContext().setAuthentication(authentication);
      } catch ( Exception e ) {
        log.info( "Что то пошло не так = " + e );
      }
      response.setResult( true );

      return response;
    }

}
