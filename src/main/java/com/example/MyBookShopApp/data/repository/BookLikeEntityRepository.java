package com.example.MyBookShopApp.data.repository;

import com.example.MyBookShopApp.data.struct.book.review.BookLikeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public interface BookLikeEntityRepository extends JpaRepository<BookLikeEntity, Integer> {

  @Query( value = " select trunc ( sum( bl.count_like ) / count( bl.count_like ) ) as rating " +
                  " from book_like bl " +
                  " where bl.book_id = :bookId ",
          nativeQuery = true )
  Integer customGetBookLikeByBookId( Integer bookId );

  List<BookLikeEntity> findAllByBookId(Integer bookId );
}
