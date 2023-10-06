package com.example.MyBookShopApp.controllers;

import com.example.MyBookShopApp.data.Book;
import com.example.MyBookShopApp.services.BookService;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping( "/api" )
public class BookRestApiController {

  private final BookService bookService;

  public BookRestApiController(BookService bookService) {
    this.bookService = bookService;
  }

  @GetMapping( "/books/by-author" )
  @ApiOperation("get all books by a given author")
  public ResponseEntity<List<Book>> booksByAuthor( @RequestParam("author") String authorName ) {
    return ResponseEntity.ok( bookService.getBooksByAuthor( authorName ) );
  }
}
