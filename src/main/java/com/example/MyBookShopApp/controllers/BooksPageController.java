package com.example.MyBookShopApp.controllers;

import com.example.MyBookShopApp.Toolkit.Toolkit;
import com.example.MyBookShopApp.data.*;
import com.example.MyBookShopApp.data.repository.BookRepository;
import com.example.MyBookShopApp.data.struct.Book;
import com.example.MyBookShopApp.services.BookService;
import com.example.MyBookShopApp.services.ResourceStorage;
import com.example.MyBookShopApp.services.TagService;
import lombok.extern.slf4j.Slf4j;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Controller
@Slf4j
@RequestMapping("/books")
public class BooksPageController {

  private final BookService bookService;
  private final TagService tagService;
  private final ResourceStorage storage;
  private final BookRepository bookRepository;
  @Autowired
  public BooksPageController( BookService bookService, TagService tagService, ResourceStorage storage, BookRepository bookRepository ) {
    this.bookService = bookService;
    this.tagService = tagService;
    this.storage = storage;
    this.bookRepository = bookRepository;
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
  public BooksPageDto getNextTagPage( @RequestParam("offset") Integer offset,
                                      @RequestParam("limit") Integer limit,
                                      @PathVariable( value = "searchTag", required = false ) SearchTagDto searchTagDto ) {
    return new BooksPageDto( bookService.getAllBooksContainsTag( Integer.valueOf( searchTagDto.getTagId() ), offset, limit ).getContent() );
  }

  @GetMapping( "/{slug}" )
  public String bookPage( @PathVariable( value = "slug", required = false ) String slug,
                          Model model ) {
    model.addAttribute( "slugBook", bookService.getBookBySlug( slug ) );
    model.addAttribute( "rating", bookService.getLikeBookById( slug ) );
    model.addAttribute( "reviewBook", bookService.getReviewBookById( slug ) );

    if ( Toolkit.userId == 0 ) {
      return "/books/slug";
    } else {
      return "/books/slugmy";
    }
  }

  @PostMapping( "/{slug}/img/save" )
  public String saveNewBookImage( @RequestParam("file") MultipartFile file,
                                  @PathVariable( "slug" ) String slug ) throws IOException {
    String savePath = storage.saveNewBookImage( file, slug );
    Book bookToUpdate = bookService.getBookBySlug( slug );
    bookToUpdate.setImage( savePath );
    bookRepository.save( bookToUpdate ); //save new path in db here
    return ("redirect:/books/" + slug );
  }

  @GetMapping("/download/{hash}")
  public ResponseEntity<ByteArrayResource> bookFile(@PathVariable("hash")String hash) throws IOException{
    Path path = storage.getBookFilePath(hash);
    Logger.getLogger(this.getClass().getSimpleName()).info("book file path: "+path);

    MediaType mediaType = storage.getBookFileMime(hash);
    Logger.getLogger(this.getClass().getSimpleName()).info("book file mime type: "+mediaType);

    byte[] data = storage.getBookFileByteArray(hash);
    Logger.getLogger(this.getClass().getSimpleName()).info("book file data len: "+data.length);

    return ResponseEntity.ok()
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename="+path.getFileName().toString())
            .contentType(mediaType)
            .contentLength(data.length)
            .body(new ByteArrayResource(data));
  }

  @PostMapping("/test")
  public String testUser( @RequestParam("value") Boolean value ) {
    log.info( "is USER ID  = " + value );
    Toolkit.userId = value ? 1 : 0;
    return ("redirect:/" );
  }

}
