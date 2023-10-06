package com.example.MyBookShopApp.data;

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
