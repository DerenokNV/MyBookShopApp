package com.example.MyBookShopApp.data;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BooksPageDto {

  private Integer count;
  private List<Book> books;

  public BooksPageDto( List<Book> books ) {
    this.books = books;
    this.count = books.size();
  }

}
