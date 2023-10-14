package com.example.MyBookShopApp.data.repository;

import com.example.MyBookShopApp.data.struct.book.review.BookReviewLikeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookReviewLikeEntityRepository extends JpaRepository<BookReviewLikeEntity,Integer> {

  List<BookReviewLikeEntity> findBookReviewLikeByReviewId( Integer reviewId );
}
