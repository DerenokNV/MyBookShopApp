package com.example.MyBookShopApp.data;


import com.example.MyBookShopApp.data.struct.book.links.Book2AuthorEntity;
import com.example.MyBookShopApp.data.struct.book.links.Book2TagEntity;
import com.example.MyBookShopApp.services.BookService;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import jakarta.persistence.*;
import lombok.*;
import org.apache.log4j.Logger;

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
@ApiModel( description = "entity representing a book")
public class Book {

  @Id
  @GeneratedValue( strategy = GenerationType.IDENTITY )
  @ApiModelProperty( "id generated" )
  private Integer id;

  @Column(columnDefinition = "DATE NOT NULL")
  @ApiModelProperty( "date of book publication" )
  private LocalDateTime pubDate;


  /*@ManyToMany(fetch = FetchType.EAGER, cascade = { CascadeType.ALL })
  @JoinTable( name = "book2author",
              joinColumns = @JoinColumn( name = "book_id" ), //, referencedColumnName="id"
              inverseJoinColumns = @JoinColumn( name = "author_id" )
            )
  @Transient
  @JsonIgnore
  private Set<Author> authorSet = new HashSet<>();*/
  @OneToMany(mappedBy = "book")
  @JsonManagedReference
  Set<Book2AuthorEntity> linksBook;

  @Transient
  private Set<Author> authorSet = new HashSet<>();

  @Column(columnDefinition = "SMALLINT NOT NULL")
  @ApiModelProperty( "if isBestseller = 1 so the book is considered to be bestseller" )
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
  @ApiModelProperty( "price book" )
  private Integer price;

  @Column(columnDefinition = "SMALLINT NOT NULL DEFAULT 0")
  private short discount;

  @Transient
  private Long sale;

  @Transient
  private String authorsString;

  @OneToMany(mappedBy = "book")
  @JsonManagedReference
  @Transient
  Set<Book2TagEntity> tagsBook;

  @OneToMany(mappedBy = "book")
  @JsonManagedReference
  @Transient
  Set<Book2TagEntity> genresBook;

  public void addAuthor( Author author ) {
    this.authorSet.add(author);
    //author.getBookSet().add(this);
  }

  /*public void addAuthors( List<Author> authors ) {
    this.authorSet.addAll( authors );
    for ( Author author : authors ) {
    }
  }*/

  public Set<Author> getAuthors() {
    return authorSet;
  }

  public void setAllAuthors() {
    authorsString = authorSet == null && authorSet.isEmpty() ? "" : authorSet.stream().map( x -> x.getName() ).collect(Collectors.joining(","));
  }

}
