package com.example.MyBookShopApp.controllers;

import com.example.MyBookShopApp.data.SearchWordDto;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class EventPageController {

  @ModelAttribute( "searchWordDto" )
  public SearchWordDto searchWordDto() {
    return new SearchWordDto();
  }

  @GetMapping("/signin")
  public String signinPages() {
    return "signin";
  }

  @GetMapping("/documents")
  public String documentsPages() {
    return "/documents/index";
  }

  @GetMapping("/about")
  public String aboutPages() {
    return "about";
  }

  @GetMapping("/faq")
  public String faqPages() {
    return "faq";
  }

  @GetMapping("/contacts")
  public String contactsPages() {
    return "contacts";
  }

}
