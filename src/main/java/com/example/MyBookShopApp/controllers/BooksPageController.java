package com.example.MyBookShopApp.controllers;

import com.example.MyBookShopApp.Toolkit.Toolkit;
import com.example.MyBookShopApp.data.AuthorBooks;
import com.example.MyBookShopApp.data.BooksPageDto;
import com.example.MyBookShopApp.data.SearchTagDto;
import com.example.MyBookShopApp.data.SearchWordDto;
import com.example.MyBookShopApp.services.AuthorService;
import com.example.MyBookShopApp.services.BookService;
import com.example.MyBookShopApp.services.TagService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Controller
@RequestMapping("/books")
public class BooksPageController {

  private final BookService bookService;
  private final TagService tagService;

  private Logger logger = Logger.getLogger(BooksPageController.class);
  @Autowired
  public BooksPageController(BookService bookService, TagService tagService) {
    this.bookService = bookService;
    this.tagService = tagService;
  }

  @ModelAttribute( "searchWordDto" )
  public SearchWordDto searchWordDto() {
    return new SearchWordDto();
  }

  @GetMapping("/popular")
  public String popularPages( Model model ) {
   model.addAttribute( "searchResults", bookService.getPageOfPopularBooks( 0, 10 ) );
    return "/books/popular";
  }

  @GetMapping( "/popular/page" )
  @ResponseBody
  public BooksPageDto getNextPopularPage( @RequestParam("offset") Integer offset,
                                          @RequestParam("limit") Integer limit ) {
    return new BooksPageDto( bookService.getPageOfPopularBooks( offset, limit ).getContent() );
  }


  @GetMapping("/recent")
  public String recentPages( Model model ) {
    model.addAttribute( "searchResults", bookService.getPageOfRecentBooks( LocalDateTime.now().minusMonths(3 ), LocalDateTime.now(),0, 20 ) );
    return "/books/recent";
  }


  @GetMapping( "/recent/page" )
  @ResponseBody
  public BooksPageDto getNextRecentPage( @RequestParam("from") String dtFrom,
                                         @RequestParam("to") String dtTo,
                                         @RequestParam("offset") Integer offset,
                                         @RequestParam("limit") Integer limit ) {
    LocalDate dtBegin = LocalDate.parse( dtFrom, Toolkit.formatter );
    LocalDate dtEnd = LocalDate.parse( dtTo, Toolkit.formatter );
    return new BooksPageDto( bookService.getPageOfRecentBooks( dtBegin.atTime(0, 0 ), dtEnd.atTime(0,0), offset, limit ).getContent() );
  }

  @GetMapping(value = { "/tag", "/tag/{searchTag}" } )
  public String tagsPages( @PathVariable( value = "searchTag", required = false ) SearchTagDto searchTagDto,
                           Model model ) {
    Integer tagId = searchTagDto == null ? 1 : Integer.valueOf( searchTagDto.getTagId() );
    model.addAttribute( "searchResults", bookService.getAllBooksContainsTag( tagId,0, 5) );
    model.addAttribute( "objectTag", tagService.getTagsDataByTagId( tagId ) );
    return "/tags/index";
  }

  @GetMapping("/tag/page/{searchTag}")
  @ResponseBody
  public BooksPageDto getNextTagPage(  @RequestParam("offset") Integer offset,
                                       @RequestParam("limit") Integer limit,
                                       @PathVariable( value = "searchTag", required = false ) SearchTagDto searchTagDto ) {
    return new BooksPageDto( bookService.getAllBooksContainsTag( Integer.valueOf( searchTagDto.getTagId() ), offset, limit ).getContent() );
  }


}
