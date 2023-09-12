package com.example.MyBookShopApp.services;

import com.example.MyBookShopApp.Toolkit.Toolkit;
import com.example.MyBookShopApp.data.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Service
public class BookService {

  private JdbcTemplate jdbcTemplate;

  @Autowired
  public BookService( JdbcTemplate jdbcTemplate ) {
    this.jdbcTemplate = jdbcTemplate;
  }

  public List<Book> getBooksData() {
    List<Book> books = jdbcTemplate.query( "select * " +
                                               "from BOOKS b" +
                                               "     inner join AUTHORS a on a.ID = b.AUTHOR ",
                                           (ResultSet rs, int rowNum ) -> {
       Book book = Book.builder()
              .id( rs.getInt( "id" ) )
              .author( rs.getString( "fio" ) )
              .title( rs.getString( "title" ) )
              .priceOld( rs.getString( "priceOld" ) )
              .price( rs.getString( "price" ) )
              .sale(Toolkit.calcSaleBook( rs.getString( "priceOld" ), rs.getString( "price" ) ) )
              .build();
      return book;
    });
    return new ArrayList<>( books );
  }
}
