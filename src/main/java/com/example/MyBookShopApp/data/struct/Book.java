package com.example.MyBookShopApp.data.struct;


import com.example.MyBookShopApp.data.struct.book.file.BookFileEntity;
import com.example.MyBookShopApp.data.struct.book.links.Book2AuthorEntity;
import com.example.MyBookShopApp.data.struct.book.links.Book2TagEntity;
import com.example.MyBookShopApp.data.struct.book.tag.Tag;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
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

  private String slug;

  private String title;

  private String image;

  private String description;

  @Column(columnDefinition = "INT NOT NULL")
  @ApiModelProperty( "price book" )
  private Integer price;

  @Column(columnDefinition = "SMALLINT NOT NULL DEFAULT 0")
  private short discount;

  @Transient
  private String authorsString;

  @OneToMany(mappedBy = "book")
  @JsonManagedReference
  @Transient
  Set<Book2TagEntity> tagsBook;

  @Transient
  @JsonProperty
  private Set<Tag> valueTagsBook = new HashSet<>();

  @OneToMany(mappedBy = "book")
  @JsonManagedReference
  @Transient
  Set<Book2TagEntity> genresBook;

  @OneToMany(mappedBy = "book")
  private List<BookFileEntity> bookFileList = new ArrayList<>();

  @JsonProperty("discountPrice")
  public Integer discountPrice() {
    return getDiscount() != 0 ? getPrice() - ( getDiscount() * getPrice() / 100 ) : null;
  };

  public void addAuthor( Author author ) {
    this.authorSet.add(author);
    //author.getBookSet().add(this);
  }

  public void addTag( Tag tag ) {
    this.valueTagsBook.add( tag );
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
