package com.example.MyBookShopApp.data;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface BookRepository extends JpaRepository<Book,Integer> {

  Page<Book> findBookByPubDateAfter( LocalDateTime publicationDate, Pageable nextPage );
  Page<Book> findBookByPubDateBetween( LocalDateTime publicationDateFrom, LocalDateTime publicationDateTo, Pageable nextPage );

  Page<Book> findBookByTitleContaining( String bootTitle, Pageable nextPage );

  @Query( value = "select * from BOOKS b where b.is_bestseller = 1",
          countQuery = "select count(*) from BOOKS",
          nativeQuery = true )
  Page<Book> customFindBookByIsBestseller( Pageable nextPage );

  @Query( value = " select b.* " +
                  " from books b " +
                  "     inner join book2tag bt on b.id = bt.book_id" +
                  "                           and bt.tag_id = :tagId ",
          countQuery = "select count(*) from BOOKS",
          nativeQuery = true )
  Page<Book> customFindBookByIdTag( Integer tagId, Pageable nextPage );

  @Query( value = " select b.* " +
                  " from books b " +
                  "      inner join book2genre bt on b.id = bt.book_id" +
                  "                              and bt.genre_id = :genreId ",
          countQuery = "select count(*) from BOOKS",
          nativeQuery = true )
  Page<Book> customFindBookByIdGenre( Integer genreId, Pageable nextPage );

  @Query( value = " select b.* " +
                  " from books b " +
                  "      inner join book2author ba on ba.book_id = b.id " +
                  "                               and ba.author_id = :authorId ",
          countQuery = "select count(*) from BOOKS",
          nativeQuery = true )
  Page<Book> customFindBookByIdAuthor( Integer authorId, Pageable nextPage );
}
