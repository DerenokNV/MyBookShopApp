package com.example.MyBookShopApp.services;

import com.example.MyBookShopApp.data.Author;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AuthorService {

  private JdbcTemplate jdbcTemplate;

  @Autowired
  public AuthorService( JdbcTemplate jdbcTemplate ) {
    this.jdbcTemplate = jdbcTemplate;
  }

  public Map<String,List<Author>> getAuthorData() {
    List<Author> authors = jdbcTemplate.query( "select * from AUTHORS a ", ( ResultSet rs, int rowNum ) -> {
              Author author = Author.builder()
                      .id( rs.getInt( "id" ) )
                      .fio( rs.getString( "fio" ) )
                      .description( rs.getString( "description" ) )
                      .build();
              return author;
            });
    return authors.stream().collect( Collectors.groupingBy( x -> x.getFio().substring( 0, 1 ) ) );
  }

}
