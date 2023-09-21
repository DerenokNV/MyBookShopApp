package com.example.MyBookShopApp.data;


import com.example.MyBookShopApp.data.struct.book.links.Book2AuthorEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table( name = "books" )
public class Book {

  @Id
  @GeneratedValue( strategy = GenerationType.IDENTITY )
  private Integer id;

  @Column(columnDefinition = "DATE NOT NULL")
  private LocalDateTime pubDate;


  /*@ManyToMany(cascade = { CascadeType.ALL })
  @JoinTable( name = "book2author",
              joinColumns = @JoinColumn( name = "book_id" ),
              inverseJoinColumns = @JoinColumn( name = "author_id" )
            )
  private Set<Author> authorSet = new HashSet<>();*/
  @OneToMany(mappedBy = "book")
  Set<Book2AuthorEntity> linksBook;

  @Transient
  private Set<Author> authorSet = new HashSet<>();

  @Column(columnDefinition = "SMALLINT NOT NULL")
  private short isBestseller;

  @Column(columnDefinition = "VARCHAR(255) NOT NULL")
  private String slug;

  @Column(columnDefinition = "VARCHAR(255) NOT NULL")
  private String title;

  @Column(columnDefinition = "VARCHAR(255)")
  private String image;

  @Column(columnDefinition = "TEXT")
  private String description;

  @Column(columnDefinition = "INT NOT NULL")
  private Integer price;

  @Column(columnDefinition = "SMALLINT NOT NULL DEFAULT 0")
  private short discount;

  @Transient
  private Long sale;

  @Transient
  private String authorSetString;

  public void addAuthor( Author author ) {
    this.authorSet.add(author);
  }

  public void addAuthors( List<Author> authors ) {
    this.authorSet.addAll( authors );
    for ( Author author : authors ) {
    }
  }

  public String setAllAuthors() {
    return authorSet == null && authorSet.isEmpty() ? "" : authorSet.stream().map( x -> x.getName() ).collect(Collectors.joining(","));
  }

}
