package com.example.MyBookShopApp.data;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResultSaveDto {

  private Boolean result;
  private String textErr;
}
