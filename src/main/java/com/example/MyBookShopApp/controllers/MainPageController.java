package com.example.MyBookShopApp.controllers;

import com.example.MyBookShopApp.data.*;
import com.example.MyBookShopApp.data.struct.Book;
import com.example.MyBookShopApp.data.struct.book.tag.Tag;
import com.example.MyBookShopApp.errs.EmptySearchException;
import com.example.MyBookShopApp.services.BookService;
import com.example.MyBookShopApp.services.TagService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class MainPageController {

  private final BookService bookService;
  private final TagService tagService;
  private Logger logger = Logger.getLogger(MainPageController.class);

  @Autowired
  public MainPageController( BookService bookService, TagService tagService ) {
    this.bookService = bookService;
    this.tagService = tagService;
  }

  @GetMapping("/")
  public String mainPage( Model model ) {
    return "index";
  }

  @ModelAttribute( "recommendedBooks" )
  public List<Book> recommendedBooks() {
    return bookService.getPageOfRecommendedBooks( 0, 20 ).getContent();
  }

  @ModelAttribute( "recentBooks" )
  public List<Book> recentBooks() {
    //logger.info("!!!!!!!!!!!!!!!! recentBooks " );
    return bookService.getPageOfRecentBooks( LocalDateTime.now().minusMonths(3), LocalDateTime.now(), 0, 20 ).getContent();
  }

  @ModelAttribute( "popularBooks" )
  public List<Book> popularBooks() {
    return bookService.getPageOfPopularBooks( 0, 20 ).getContent();
  }

  @ModelAttribute( "searchWordDto" )
  public SearchWordDto searchWordDto() {
    return new SearchWordDto();
  }

  @ModelAttribute( "searchTagDto" )
  public SearchTagDto searchTagDto() { return new SearchTagDto(); }

  @ModelAttribute( "searchResults" )
  public List<Book> searchResults() {
    return new ArrayList();
  }

  @ModelAttribute( "tagsBook" )
  public List<Tag> tagsBook() {
    return tagService.getTagsData();
  }

  @ModelAttribute( "tagsBookMap" )
  public Map<Tag,Long> tagsBookMap() {
    return tagService.getTagBooksData();
  }

  @GetMapping( "/books/recommended" )
  @ResponseBody
  public BooksPageDto getBooksPage( @RequestParam("offset") Integer offset,
                                    @RequestParam("limit") Integer limit ) {
    return new BooksPageDto( bookService.getPageOfRecommendedBooks( offset, limit ).getContent() );
  }

  @GetMapping( value = { "/search", "/search/", "/search/{searchWord}"} )
  public String getSearchResults( @PathVariable( value = "searchWord", required = false ) SearchWordDto searchWordDto,
                                  Model model) throws EmptySearchException {
    if ( searchWordDto != null ) {
      model.addAttribute("searchWordDto", searchWordDto);
      model.addAttribute("searchResults", bookService.getPageOfSearchResultBooks(searchWordDto.getExample(), 0, 5).getContent());
      return "/search/index";
    } else {
      throw new EmptySearchException( "Поиск по null невозможен" );
    }
  }

  @GetMapping( "/search/page/{searchWord}" )
  @ResponseBody
  public BooksPageDto getNextSearchPage( @RequestParam("offset") Integer offset,
                                         @RequestParam("limit") Integer limit,
                                         @PathVariable( value = "searchWord", required = false ) SearchWordDto searchWordDto ) {
    return new BooksPageDto( bookService.getPageOfSearchResultBooks( searchWordDto.getExample(), offset, limit ).getContent() );
  }

}
