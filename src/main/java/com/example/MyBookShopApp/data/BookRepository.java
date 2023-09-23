package com.example.MyBookShopApp.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book,Integer> {

  //List<Book> findBooksByAuthorSetId( Integer id );

 /* List<Book> findBooksByName( String name );

  @Query("from Book")
  List<Book> customFindAllBooks();*/

}
