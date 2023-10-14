package com.example.MyBookShopApp.Toolkit;

import org.apache.log4j.Logger;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Toolkit {

  public static Integer userId = 0;
  public static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

  public static boolean isBookSlugCash( String cash,String slug ) {
    if ( cash.isEmpty() ) {
      return false;
    }
    List<String> convertedCountriesList = Stream.of( cash.split("/" ) ).collect( Collectors.toList() );
    Optional opt = convertedCountriesList.stream().filter( x -> Objects.equals( x, slug ) ).findAny();

    return opt.isPresent();
  }

}
