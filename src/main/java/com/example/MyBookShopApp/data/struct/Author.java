package com.example.MyBookShopApp.data.struct;

import com.example.MyBookShopApp.data.struct.book.links.Book2AuthorEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table( name = "authors" )
@Schema(description = "Информация о авторе")
public class Author {

  @Id
  @GeneratedValue( strategy = GenerationType.IDENTITY )
  @Schema(description = "Идентификатор")
  private Integer id;

  @Column(columnDefinition = "VARCHAR(255)")
  @Schema(description = "Фотография")
  private String photo;

  @Column(columnDefinition = "VARCHAR(255) NOT NULL")
  @Schema(description = "ХЗ что")
  private String slug;

  @Column(columnDefinition = "VARCHAR(255) NOT NULL")
  @Schema(description = "ФИО автора")
  private String name;

  @Column(columnDefinition = "TEXT")
  @Schema(description = "Библиография автора")
  private String description;

  /*@ManyToMany(fetch = FetchType.EAGER, mappedBy = "authorSet", cascade = { CascadeType.ALL })
  @Transient
  private Set<Book> bookSet = new HashSet<>();*/
  @OneToMany(mappedBy = "authorId")
  @Transient
  Set<Book2AuthorEntity> author;

}
