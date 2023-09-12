package com.example.MyBookShopApp.controllers;

import com.example.MyBookShopApp.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/books")
public class BooksPageController {

  private final BookService bookService;

  @Autowired
  public BooksPageController( BookService bookService ) {
    this.bookService = bookService;
  }

  @GetMapping("/popular")
  public String popularPages( Model model ) {
    model.addAttribute( "booksList", bookService.getBooksData() );
    return "/books/popular";
  }

  @GetMapping("/recent")
  public String recentPages( Model model ) {
    model.addAttribute( "booksList", bookService.getBooksData() );
    return "/books/recent";
  }
}
