package com.example.MyBookShopApp.data;

import com.example.MyBookShopApp.data.struct.book.review.BookReviewEntity;
import com.example.MyBookShopApp.data.struct.book.review.BookReviewLikeEntity;
import com.example.MyBookShopApp.data.struct.user.UserEntity;
import lombok.*;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewInfoDto {

  BookReviewEntity review;
  UserEntity user;
  Set<BookReviewLikeEntity> like;
  Set<BookReviewLikeEntity> dislike;
}
