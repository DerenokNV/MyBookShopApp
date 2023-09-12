package com.example.MyBookShopApp.controllers;

import com.example.MyBookShopApp.services.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AuthorsController {

  private final AuthorService authorService;

  @Autowired
  public AuthorsController( AuthorService authorService ) {
    this.authorService = authorService;
  }

  @GetMapping("/genres")
  public String genresPage() {
    return "/genres/index";
  }

  @GetMapping("/authors")
  public String authorPage( Model model ) {
    model.addAttribute( "authorData", authorService.getAuthorData() );
    return "/authors/index";
  }

  @GetMapping("/authors/slug")
  public String slugPage () {
    return "/authors/slug";
  }
}
