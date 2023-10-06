package com.example.MyBookShopApp.services;

import com.example.MyBookShopApp.data.*;
import com.example.MyBookShopApp.data.struct.book.links.Book2AuthorEntity;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class BookService {

  private BookRepository bookRepository;
  private AuthorRepository authorRepository;
  private Book2AuthorEntityRepository book2AuthorEntityRepository;
  private Logger logger = Logger.getLogger(BookService.class);

  @Autowired
  public BookService( BookRepository bookRepository, AuthorRepository authorRepository, Book2AuthorEntityRepository book2AuthorEntityRepository) {
    this.bookRepository = bookRepository;
    this.authorRepository = authorRepository;
    this.book2AuthorEntityRepository = book2AuthorEntityRepository;
  }

  public List<Book> getBooksByAuthor( String author ) {
    List<Book> result =  new ArrayList();
    List<Book> booksList =  getBooksData();
    for( Book bookList : booksList ) {
      Set<Author> bookAuthor = bookList.getAuthors();
      Optional<Author> optAuthor = bookAuthor.stream().filter( x -> x.getName().contains( author ) ).findFirst();
      if ( optAuthor.isPresent() ) {
        result.add( bookList );
      }
    }

    return result == null ? new ArrayList() : result;
  }

  public void addAuthorsInBook( List<Book> bookList ) {
    for (Book book : bookList) {
      List<Book2AuthorEntity> linkList = book2AuthorEntityRepository.findBook2authorByBookId(book.getId());
      for (Book2AuthorEntity link : linkList) {
        Optional<Author> oiptAuthor = authorRepository.findById( link.getAuthor().getId() );
        if (oiptAuthor.isPresent()) {
          book.addAuthor(oiptAuthor.get());
        }
      }
      if (book.getDiscount() != 0) {
        book.setSale((long) (book.getPrice() - (book.getDiscount() * book.getPrice() / 100)));
      }
      book.setAllAuthors();
    }
  }

  public List<Book> getBooksData() {
    List<Book> bookList = bookRepository.findAll();
    addAuthorsInBook( bookList );
    return bookList;
  }

  public Page<Book> getPageOfRecommendedBooks( Integer offset, Integer limit ) {
    Pageable nextPage = PageRequest.of( offset, limit );
    Page<Book> booksPage = bookRepository.findAll( nextPage );
    addAuthorsInBook( booksPage.getContent() );
    return bookRepository.findAll( nextPage );
  }

  public Page<Book> getPageOfRecentBooks( LocalDateTime dtFrom, LocalDateTime dtTo, Integer offset, Integer limit ) {
    Pageable nextPage = PageRequest.of( offset, limit );
    //logger.info( "getPageOfRecentBooks = " + dtFrom + " :: " + dtTo );
    dtFrom = dtFrom == null ? LocalDateTime.now().minusMonths( 3 ) : dtFrom;
    dtTo = dtTo == null ? LocalDateTime.now() : dtTo;
    Page<Book> booksPage = bookRepository.findBookByPubDateBetween( dtFrom, dtTo, nextPage );
    addAuthorsInBook( booksPage.getContent() );
    try {
      return booksPage;
    } catch ( Exception ex ) {
      return getPageOfRecommendedBooks( offset, limit );
    }
  }

  public Page<Book> getPageOfPopularBooks( Integer offset, Integer limit ) {
    Pageable nextPage = PageRequest.of( offset, limit );
    Page<Book> booksPage = bookRepository.customFindBookByIsBestseller( nextPage );
    addAuthorsInBook( booksPage.getContent() );
    try {
      return booksPage;
    } catch ( Exception ex ) {
      return getPageOfRecommendedBooks( offset, limit );
    }
  }

  public Page<Book> getPageOfSearchResultBooks( String searchWord, Integer offset, Integer limit ) {
    Pageable nextPage = PageRequest.of( offset, limit );
    return bookRepository.findBookByTitleContaining( searchWord, nextPage);
  }

  /**
   * Тут мы получаем пачками книги по тегам
   * @param tagId
   * @param offset
   * @param limit
   * @return
   */
  public Page<Book> getAllBooksContainsTag( Integer tagId, Integer offset, Integer limit ) {
    Pageable nextPage = PageRequest.of( offset, limit );
    return bookRepository.customFindBookByIdTag( tagId, nextPage );
  }

  /**
   * Тут мы получаем пачками книги по жанрам
   * @param genreId
   * @param offset
   * @param limit
   * @return
   */
  public Page<Book> getAllBooksContainsGenre( Integer genreId, Integer offset, Integer limit ) {
    Pageable nextPage = PageRequest.of( offset, limit );
    return bookRepository.customFindBookByIdGenre( genreId, nextPage );
  }
}
