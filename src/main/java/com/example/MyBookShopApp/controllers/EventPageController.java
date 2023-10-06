package com.example.MyBookShopApp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class EventPageController {

  @GetMapping("/cart")
  public String popularPages() {
    return "cart";
  }

  @GetMapping("/postponed")
  public String postponedPages() {
    return "postponed";
  }

 /* @GetMapping("/search/")
  public String searchPages() {
    return "/search/index";
  }*/

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
