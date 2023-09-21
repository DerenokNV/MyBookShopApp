package com.example.MyBookShopApp.services;

import com.example.MyBookShopApp.data.Author;
import com.example.MyBookShopApp.data.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AuthorService {

  private AuthorRepository authorRepository;

  @Autowired
  public AuthorService( AuthorRepository authorRepository ) {
    this.authorRepository = authorRepository;
  }

  public Map<String,List<Author>> getAuthorData() {
    return authorRepository.findAll().stream().collect(Collectors.groupingBy(x -> x.getName().substring(0, 1)));
  }

}
