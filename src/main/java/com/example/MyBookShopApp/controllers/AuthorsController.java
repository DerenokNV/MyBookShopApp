package com.example.MyBookShopApp.controllers;

import com.example.MyBookShopApp.data.*;
import com.example.MyBookShopApp.services.AuthorService;
import com.example.MyBookShopApp.services.BookService;
import com.example.MyBookShopApp.services.GenreService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class AuthorsController {

  private Logger logger = Logger.getLogger(AuthorsController.class);
  private final AuthorService authorService;
  private final GenreService genreService;
  private final BookService bookService;

  @Autowired
  public AuthorsController( AuthorService authorService, GenreService genreService, BookService bookService ) {
    this.authorService = authorService;
    this.genreService = genreService;
    this.bookService = bookService;
  }

  @GetMapping("/genres/index")
  public String genresPage( Model model ) {
    TreeGenreDto obj = genreService.getTreeGenres();
    model.addAttribute( "genresModel", obj.getTreeModel() );
    model.addAttribute( "genresInfo", obj.getParams() );
    model.addAttribute( "searchWordDto", new SearchWordDto() );
    return "/genres/index";
  }

  @GetMapping("/authors")
  public String authorPage( Model model ) {
    model.addAttribute( "searchWordDto", new SearchWordDto() );
    model.addAttribute( "authorData", authorService.getAuthorData() );
    return "/authors/index";
  }

  @GetMapping("/authors/slug/{searchAuthor}")
  public String slugPage ( @PathVariable( value = "searchAuthor", required = false ) SearchTagDto searchTagDto,
                           Model model ) {
    Integer authorId = searchTagDto == null ? 1 : Integer.valueOf( searchTagDto.getTagId() );
    AuthorBooks info = authorService.getAuthorBooksData( authorId, 0, 20 );
    model.addAttribute( "searchWordDto", new SearchWordDto() );
    model.addAttribute( "authorBooks", info.getBooksPageDto().getContent() );
    model.addAttribute( "authorInfo", info.getAuthor() );
    return "/authors/slug";
  }

  @GetMapping( "/authors/slug/slider/{searchAuthor}" )
  @ResponseBody
  public BooksPageDto getBooksPage( @PathVariable( value = "searchAuthor", required = false ) SearchTagDto searchTagDto,
                                    @RequestParam("offset") Integer offset,
                                    @RequestParam("limit") Integer limit ) {
    Integer authorId = searchTagDto == null ? 1 : Integer.valueOf( searchTagDto.getTagId() );
    AuthorBooks info = authorService.getAuthorBooksData( authorId, offset, limit );
    return new BooksPageDto( info.getBooksPageDto().getContent() );
  }

  @GetMapping("/books/author/{searchAuthor}")
  public String autorsBooks( @PathVariable( value = "searchAuthor", required = false ) SearchTagDto searchTagDto,
                                  Model model ) {
    Integer authorId = searchTagDto == null ? 1 : Integer.valueOf( searchTagDto.getTagId() );
    AuthorBooks info = authorService.getAuthorBooksData( authorId, 0, 5 );
    model.addAttribute( "searchWordDto", new SearchWordDto() );
    model.addAttribute( "searchResults", info.getBooksPageDto().getContent() );
    model.addAttribute( "authorInfo", info.getAuthor() );
    return "/books/author";
  }

  @GetMapping("/books/author/page/{searchAuthor}")
  @ResponseBody
  public BooksPageDto getNextAutorsBooksPage( @RequestParam("offset") Integer offset,
                                              @RequestParam("limit") Integer limit,
                                              @PathVariable( value = "searchAuthor", required = false ) SearchTagDto searchTagDto ) {
    return new BooksPageDto( authorService.getAuthorPageBooksData( Integer.valueOf( searchTagDto.getTagId() ), offset, limit ).getContent() );
  }

  @GetMapping("/genres/slug/{searchGen}")
  public String genrePages( @PathVariable( value = "searchGen", required = false ) SearchTagDto searchTagDto,
                            Model model ) {
    Integer genreId = searchTagDto == null ? 1 : Integer.valueOf( searchTagDto.getTagId() );
    model.addAttribute( "searchWordDto", new SearchWordDto() );
    model.addAttribute( "breadcrumbNames", genreService.getTreeNames( genreId ) );
    model.addAttribute( "searchResults", bookService.getAllBooksContainsGenre( genreId,0, 5) );
    return "/genres/slug";
  }


  @GetMapping("/genres/slug/page/{searchGen}")
  @ResponseBody
  public BooksPageDto getNextTagPage(@RequestParam("offset") Integer offset,
                                     @RequestParam("limit") Integer limit,
                                     @PathVariable( value = "searchGen", required = false ) SearchTagDto searchTagDto ) {
    return new BooksPageDto( bookService.getAllBooksContainsGenre( Integer.valueOf( searchTagDto.getTagId() ), offset, limit ).getContent() );
  }

}
