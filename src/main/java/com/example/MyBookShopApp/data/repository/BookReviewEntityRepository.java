package com.example.MyBookShopApp.data.repository;

import com.example.MyBookShopApp.data.struct.book.review.BookReviewEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookReviewEntityRepository extends JpaRepository<BookReviewEntity, Integer> {

  List<BookReviewEntity> findAllByBookId( Integer bookId );
}
