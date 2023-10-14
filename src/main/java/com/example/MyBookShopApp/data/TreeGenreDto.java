package com.example.MyBookShopApp.data;

import com.example.MyBookShopApp.data.struct.genre.GenreEntity;
import lombok.*;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TreeGenreDto {

  private Map<Integer,Object> treeModel;
  private Map<Integer, List<GenreEntity>> params;
}
