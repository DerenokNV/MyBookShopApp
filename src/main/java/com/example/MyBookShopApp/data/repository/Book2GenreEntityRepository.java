package com.example.MyBookShopApp.data.repository;

import com.example.MyBookShopApp.data.struct.book.links.Book2GenreEntity;
import com.example.MyBookShopApp.data.struct.book.links.Book2TagEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Book2GenreEntityRepository extends JpaRepository<Book2GenreEntity,Integer> {

  List<Book2GenreEntity> findBook2GenreByGenreId(Integer genreId );
}
