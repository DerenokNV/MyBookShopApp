package com.example.MyBookShopApp.data.struct.book.file;

import com.example.MyBookShopApp.data.struct.Book;
import com.example.MyBookShopApp.data.struct.enums.BookFileType;
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

  private String hash;

  private String path;

  @Column(name="type_id")
  private int typeId;

  @ManyToOne
  @JoinColumn(name="book_id", referencedColumnName = "id")
  private Book book;

  public String getBookFileExtensionString(){
    return BookFileType.getExtensionStringByTypeID( typeId );
  }
}
