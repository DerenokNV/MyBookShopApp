package com.example.MyBookShopApp.data;

import com.example.MyBookShopApp.data.struct.book.review.BookLikeEntity;
import lombok.*;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RatingBookDto {

  private Map<Integer,List<BookLikeEntity>> allocationRating;
  private Integer avgRating;
  private Integer allRating;

}
