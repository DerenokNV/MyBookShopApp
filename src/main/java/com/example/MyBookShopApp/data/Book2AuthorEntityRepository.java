package com.example.MyBookShopApp.data;

import com.example.MyBookShopApp.data.struct.book.links.Book2AuthorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Book2AuthorEntityRepository extends JpaRepository<Book2AuthorEntity,Integer> {

  List<Book2AuthorEntity> findBook2authorByBookId( Integer bookId );
}
