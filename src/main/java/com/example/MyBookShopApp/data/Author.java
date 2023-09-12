package com.example.MyBookShopApp.data;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class Author {

  private Integer id;
  private String fio;
  private String description;
}
