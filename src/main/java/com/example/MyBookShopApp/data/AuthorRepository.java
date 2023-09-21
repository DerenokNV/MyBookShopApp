package com.example.MyBookShopApp.data;

import com.example.MyBookShopApp.data.struct.book.links.Book2AuthorEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AuthorRepository extends JpaRepository<Author,Integer> {

  List<Author> findAuthorsByBookSetId( Integer id );

}
