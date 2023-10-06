package com.example.MyBookShopApp.Toolkit;

import org.apache.log4j.Logger;

import java.time.format.DateTimeFormatter;

public class Toolkit {

  public static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
  private static final Logger logger = Logger.getLogger( Toolkit.class );

  public static Long calcSaleBook( String priceOld, String price ) {
    Long result = null;
    if ( priceOld == null || price == null ) {
      return result;
    }

    try {
      Double priceOldInt = Double.valueOf( priceOld );
      Double priceInt = Double.valueOf( price );
      result = Math.round( priceInt / priceOldInt * 100 );
    } catch ( Exception e ) {
      logger.info( "Error calculeted sale book, error " + e.getMessage() );
    }
    return result;
  }
}
