package com.example.MyBookShopApp.controllers;

import com.example.MyBookShopApp.data.ApiResponse;
import com.example.MyBookShopApp.data.struct.Book;
import com.example.MyBookShopApp.errs.BookstoreApiWrongParameterException;
import com.example.MyBookShopApp.services.BookService;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
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

  @GetMapping("/books/by-title")
  @ApiOperation("get books by title")
  public ResponseEntity<ApiResponse<Book>> booksByTitle( @RequestParam("title") String title ) throws BookstoreApiWrongParameterException {
    ApiResponse<Book> response = new ApiResponse<>();
    List<Book> data = bookService.getBooksByTitle( title );
    response.setDebugMessage( "successful request" );
    response.setMessage( "data size: " + data.size() + " elements" );
    response.setStatus( HttpStatus.OK );
    response.setTimeStamp( LocalDateTime.now() );
    response.setData( data );
    return ResponseEntity.ok( response );
  }

  @ExceptionHandler(MissingServletRequestParameterException.class)
  public ResponseEntity<ApiResponse<Book>> handleMissingServletRequestParameterException( Exception exception ) {
    return new ResponseEntity<>( new ApiResponse<Book>( HttpStatus.BAD_REQUEST,
                                               "Missing required parameters",
                                                       exception),
                                 HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(BookstoreApiWrongParameterException.class)
  public ResponseEntity<ApiResponse<Book>> handleBookstoreApiWrongParameterException( Exception exception ) {
    return new ResponseEntity<>( new ApiResponse<Book>( HttpStatus.BAD_REQUEST,
                                                "Bad parameter value...",
                                                        exception ),
                                 HttpStatus.BAD_REQUEST );
  }
}
