package com.example.MyBookShopApp.data.struct.book.links;

import com.example.MyBookShopApp.data.Book;
import com.example.MyBookShopApp.data.struct.book.tag.Tag;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "book2tag")
public class Book2TagEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @ManyToOne
  @JoinColumn(name = "book_id")
  @JsonBackReference
  private Book book;

  @ManyToOne
  @JoinColumn(name = "tag_id")
  private Tag tag;
}
