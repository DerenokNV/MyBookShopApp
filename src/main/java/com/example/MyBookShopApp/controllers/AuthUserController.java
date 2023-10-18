package com.example.MyBookShopApp.controllers;

import com.example.MyBookShopApp.data.SearchWordDto;
import com.example.MyBookShopApp.security.ContactConfirmationPayload;
import com.example.MyBookShopApp.security.ContactConfirmationResponse;
import com.example.MyBookShopApp.security.RegistrationForm;
import com.example.MyBookShopApp.services.BookstoreUserRegister;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@Slf4j
public class AuthUserController {

  public final BookstoreUserRegister bookstoreUserRegister;

  public AuthUserController( BookstoreUserRegister bookstoreUserRegister ) {
    this.bookstoreUserRegister = bookstoreUserRegister;
  }

  @ModelAttribute( "searchWordDto" )
  public SearchWordDto searchWordDto() {
    return new SearchWordDto();
  }

  @GetMapping("/signin")
  public String signinPages() {
    return "signin";
  }

  @GetMapping("/signup")
  public String handleSignUp( Model model ) {
    model.addAttribute( "regForm", new RegistrationForm() );
    return "signup";
  }

  @PostMapping("/requestContactConfirmation")
  @ResponseBody
  public ContactConfirmationResponse handleRequestContactConfirmation( @RequestBody ContactConfirmationPayload contactConfirmationPayload ) {
    ContactConfirmationResponse response = new ContactConfirmationResponse();
    response.setResult( true );
    return response;
  }

  @PostMapping("/approveContact")
  @ResponseBody
  public ContactConfirmationResponse handleApproveContact( @RequestBody ContactConfirmationPayload payload ) {
    ContactConfirmationResponse response = new ContactConfirmationResponse();
    response.setResult( true );
    return response;
  }

  @PostMapping("/reg")
  public String handleUserRegistration( RegistrationForm registrationForm, Model model ) {
    bookstoreUserRegister.registerNewUser(registrationForm);
    model.addAttribute("regOk", true);
    return "signin";
  }

  @PostMapping("/login")
  @ResponseBody
  public ContactConfirmationResponse handleLogin( @RequestBody ContactConfirmationPayload payload ) {
    return bookstoreUserRegister.login( payload );
  }

  @GetMapping("/my")
  public String handleMy() {
    log.info( "IN ContactConfirmationResponse /my" );
    return "my";
  }

  @GetMapping("/profile")
  public String handleProfile(Model model) {
    //model.addAttribute("curUsr", userRegister.getCurrentUser());
    return "profile";
  }

}
