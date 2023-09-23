package com.example.MyBookShopApp.services;

import com.example.MyBookShopApp.data.*;
import com.example.MyBookShopApp.data.struct.book.links.Book2AuthorEntity;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

  public List<Book> getBooksData() {
    List<Book> bookList =  bookRepository.findAll();
    for ( Book book : bookList ) {
      List<Book2AuthorEntity>  linkList = book2AuthorEntityRepository.findBook2authorByBookId( book.getId() );
      for ( Book2AuthorEntity link : linkList ) {
        Optional<Author> oiptAuthor = authorRepository.findById( link.getAuthor().getId() );
        if ( oiptAuthor.isPresent() ) {
          book.addAuthor( oiptAuthor.get() );
        }
      }
      if ( book.getDiscount() != 0 ) {
        book.setSale( (long) ( book.getPrice() - ( book.getDiscount() * book.getPrice() / 100 ) ) );
      }

      book.setAllAuthors();

      logger.info("BookService - getBooksData, params = " + book.getId() + " :: " + book.getTitle() + " :: " + book.getAuthorSet() );
    }

    return bookRepository.findAll();
  }

}
