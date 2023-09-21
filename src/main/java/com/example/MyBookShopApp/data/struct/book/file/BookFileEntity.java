package com.example.MyBookShopApp.data.struct.book.file;

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
@Table(name = "book_file")
public class BookFileEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @Column(columnDefinition = "VARCHAR(255) NOT NULL")
  private String hash;

  @Column(columnDefinition = "VARCHAR(255) NOT NULL")
  private String path;

  @Column(columnDefinition = "INT NOT NULL  DEFAULT 0")
  private int type_id;
}
