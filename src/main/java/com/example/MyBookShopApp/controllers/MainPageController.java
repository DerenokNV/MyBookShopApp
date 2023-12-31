package com.example.MyBookShopApp.controllers;

import com.example.MyBookShopApp.services.AuthorService;
import com.example.MyBookShopApp.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
public class MainPageController {

  private final BookService bookService;

  @Autowired
  public MainPageController( BookService bookService ) {
    this.bookService = bookService;
  }

  @GetMapping("/")
  public String mainPage( Model model ) {
    model.addAttribute( "bookData", bookService.getBooksData() );
    model.addAttribute( "booksList", bookService.getBooksData() );
    model.addAttribute( "searchPlaceholder", "new search Placeholder" );
    model.addAttribute( "serverTime", new SimpleDateFormat( "hh:mm:ss" ).format( new Date() ) );
    model.addAttribute( "placeholderTextPart2", "SERVER" );
    model.addAttribute( "messageTemplate", "searchbar.placeholder2" );
    return "index";
  }

}
