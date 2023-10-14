package com.example.MyBookShopApp.data;

import com.example.MyBookShopApp.data.struct.Author;
import com.example.MyBookShopApp.data.struct.Book;
import lombok.*;
import org.springframework.data.domain.Page;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthorBooks {

  private Author author;
  private Page<Book> booksPageDto;
}
