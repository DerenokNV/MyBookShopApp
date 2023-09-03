package com.example.MyBookShopApp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/books")
public class BooksPageController {

  @GetMapping("/popular")
  public String popularPages() {
    return "/books/popular";
  }

  @GetMapping("/recent")
  public String recentPages() {
    return "/books/recent";
  }
}
