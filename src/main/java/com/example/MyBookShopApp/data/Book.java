package com.example.MyBookShopApp.data;


import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Builder
@Getter
public class Book {

  private Integer id;
  private String author;
  private String title;
  private String priceOld;
  private String price;
  private Long sale;

}
