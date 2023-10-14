package com.example.MyBookShopApp.data.struct.book.tag;

import com.example.MyBookShopApp.data.struct.book.links.Book2AuthorEntity;
import com.example.MyBookShopApp.data.struct.book.links.Book2TagEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table( name = "tags" )
@ApiModel( description = "entity representing tags a book")
public class Tag {

  @Id
  @GeneratedValue( strategy = GenerationType.IDENTITY )
  @ApiModelProperty( "id generated" )
  private Integer id;

  @Column(columnDefinition = "VARCHAR(255) NOT NULL")
  private String description;

  @OneToMany(mappedBy = "tag")
  @Transient
  Set<Book2TagEntity> tags;
}
