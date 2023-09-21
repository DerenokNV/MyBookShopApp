package com.example.MyBookShopApp.data;

import com.example.MyBookShopApp.data.struct.book.links.Book2AuthorEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Set;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table( name = "authors" )
public class Author {

  @Id
  @GeneratedValue( strategy = GenerationType.IDENTITY )
  private Integer id;

  @Column(columnDefinition = "VARCHAR(255)")
  private String photo;

  @Column(columnDefinition = "VARCHAR(255) NOT NULL")
  private String slug;

  @Column(columnDefinition = "VARCHAR(255) NOT NULL")
  private String name;

  @Column(columnDefinition = "TEXT")
  private String description;

  /*@ManyToMany( mappedBy = "authorSet", cascade = { CascadeType.ALL } )
  private Set<Book> bookSet = new HashSet<>();*/
  @OneToMany(mappedBy = "authorId")
  @Transient
  Set<Book2AuthorEntity> author;

}
