package com.example.MyBookShopApp.data.repository;

import com.example.MyBookShopApp.data.Book;
import com.example.MyBookShopApp.data.struct.book.links.Book2TagEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Book2TagEntityRepository extends JpaRepository<Book2TagEntity,Integer> {

  List<Book2TagEntity> findBook2TagByTagId( Integer tagId );

}
