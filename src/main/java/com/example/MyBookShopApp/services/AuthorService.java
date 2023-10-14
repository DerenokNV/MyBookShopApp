package com.example.MyBookShopApp.services;

import com.example.MyBookShopApp.data.*;
import com.example.MyBookShopApp.data.repository.AuthorRepository;
import com.example.MyBookShopApp.data.repository.BookRepository;
import com.example.MyBookShopApp.data.struct.Author;
import com.example.MyBookShopApp.data.struct.Book;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AuthorService {

  private Logger logger = Logger.getLogger(AuthorService.class);
  private AuthorRepository authorRepository;
  private BookRepository bookRepository;

  @Autowired
  public AuthorService( AuthorRepository authorRepository, BookRepository bookRepository ) {
    this.bookRepository = bookRepository;
    this.authorRepository = authorRepository;
  }

  public Map<String,List<Author>> getAuthorData() {
    return authorRepository.findAll().stream().collect(Collectors.groupingBy(x -> x.getName().substring(0, 1)));
  }

  public AuthorBooks getAuthorBooksData( Integer authorId, Integer offset, Integer limit ) {
    Author author = authorRepository.findAuthorsById( authorId );
    return new AuthorBooks( author, getAuthorPageBooksData( authorId, offset, limit ) );
  }

  public Page<Book> getAuthorPageBooksData(Integer authorId, Integer offset, Integer limit ) {
    Pageable nextPage = PageRequest.of( offset, limit );
    return bookRepository.customFindBookByIdAuthor( authorId, nextPage );
  }

}
