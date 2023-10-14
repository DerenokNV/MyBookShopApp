package com.example.MyBookShopApp.controllers;

import com.example.MyBookShopApp.Toolkit.Toolkit;
import com.example.MyBookShopApp.data.ResultSaveDto;
import com.example.MyBookShopApp.data.repository.BookLikeEntityRepository;
import com.example.MyBookShopApp.data.struct.Book;
import com.example.MyBookShopApp.data.SearchWordDto;
import com.example.MyBookShopApp.services.BookService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
@RequestMapping("/book")
public class BookshopCartController {

  @ModelAttribute( "searchWordDto" )
  public SearchWordDto searchWordDto() {
    return new SearchWordDto();
  }

  @ModelAttribute(name = "bookCart")
  public List<Book> bookCart() {
    return new ArrayList<>();
  }
  private final BookService bookService;
  private final BookLikeEntityRepository bookLikeEntityRepository;

  @Autowired
  public BookshopCartController( BookService bookService, BookLikeEntityRepository bookLikeEntityRepository ) {
    this.bookService = bookService;
    this.bookLikeEntityRepository = bookLikeEntityRepository;
  }


  @GetMapping("/cart")
  public String handleCartRequest( @CookieValue(value = "cartContents", required = false) String cartContents,
                                  Model model) {
    if (cartContents == null || cartContents.equals("")) {
      model.addAttribute("isCartEmpty", true);
    } else {
      model.addAttribute("isCartEmpty", false);
      cartContents = cartContents.startsWith("/") ? cartContents.substring(1) : cartContents;
      cartContents = cartContents.endsWith("/") ? cartContents.substring(0, cartContents.length() - 1) : cartContents;
      String[] cookieSlugs = cartContents.split("/");
      List<Book> booksFromCookieSlugs = bookService.getBooksBySlugIn( cookieSlugs );
      model.addAttribute("bookCart", booksFromCookieSlugs);
    }

    return "cart";
  }

  @GetMapping("/postponed")
  public String handleKeptRequest( @CookieValue(value = "keptContents", required = false) String keptContents,
                                   Model model) {
    if (keptContents == null || keptContents.equals("")) {
      model.addAttribute("isKeptEmpty", true);
    } else {
      model.addAttribute("isKeptEmpty", false);
      keptContents = keptContents.startsWith("/") ? keptContents.substring(1) : keptContents;
      keptContents = keptContents.endsWith("/") ? keptContents.substring(0, keptContents.length() - 1) : keptContents;
      String[] cookieSlugs = keptContents.split("/");
      List<Book> booksFromCookieSlugs = bookService.getBooksBySlugIn( cookieSlugs );
      model.addAttribute("bookCart", booksFromCookieSlugs);
    }

    return "postponed";
  }

  @PostMapping("/changeBookStatus/{slug}")
  public String handleChangeBookStatus( @PathVariable("slug") String slug,
                                        @CookieValue(name = "cartContents", required = false) String cartContents,
                                        @CookieValue(name = "keptContents", required = false) String keptContents,
                                        @RequestParam("status") String status,
                                        HttpServletResponse response,
                                        Model model ) {
    boolean thisCart = Objects.equals( "CART", status );
    addCookies( response, thisCart ? cartContents : keptContents, thisCart, slug );
    removeParamCookies( response, thisCart ? keptContents : cartContents, slug, thisCart ? "keptContents" : "cartContents" );
    model.addAttribute("isCartEmpty", ! ( cartContents == null || cartContents.equals("") ) );
    model.addAttribute("isKeptEmpty", ! ( keptContents == null || keptContents.equals("") ) );

    return "redirect:/books/" + slug;
  }

  private void addCookies( HttpServletResponse response, String contents, boolean thisCart, String slug ) {
    String nameCookie = thisCart ? "cartContents" : "keptContents";
    if ( contents == null || contents.equals("") ) {
      Cookie cookie = new Cookie( nameCookie, slug );
      setCookie( response, cookie );
    } else if ( ! Toolkit.isBookSlugCash( contents, slug ) ) {
      StringJoiner stringJoiner = new StringJoiner("/");
      stringJoiner.add( contents ).add( slug );
      Cookie cookie = new Cookie( nameCookie, stringJoiner.toString() );
      setCookie( response, cookie );
    }
  }

  private void setCookie( HttpServletResponse response, Cookie cookie ) {
    cookie.setPath( "/" );
    cookie.setHttpOnly( true );
    response.addCookie( cookie );
  }

  private void removeParamCookies( HttpServletResponse response, String contents, String slug, String nameCookie ) {
    if ( contents == null || contents.equals( "" ) ) {
      return;
    }
    ArrayList<String> cookieBooks = new ArrayList<>( Arrays.asList( contents.split("/" ) ) );
    cookieBooks.remove(slug);
    Cookie cookie = new Cookie(nameCookie, String.join("/", cookieBooks));
    setCookie( response, cookie );
  }

  @PostMapping("/changeBookStatus/cart/remove/{slug}")
  public String handleRemoveBookFromCartRequest( @PathVariable("slug") String slug,
                                                 @CookieValue(name = "cartContents", required = false) String cartContents,
                                                 HttpServletResponse response,
                                                 Model model) {
    if ( cartContents != null && ! cartContents.equals( "" ) ) {
      ArrayList<String> cookieBooks = new ArrayList<>( Arrays.asList( cartContents.split("/" ) ) );
      cookieBooks.remove(slug);
      Cookie cookie = new Cookie("cartContents", String.join("/", cookieBooks));
      cookie.setPath("/");
      //cookie.setHttpOnly(true);
      response.addCookie(cookie);
      model.addAttribute("isCartEmpty", false);
    } else {
      model.addAttribute("isCartEmpty", true);
    }

    return "redirect:/book/cart";
  }

  @PostMapping("/changeBookStatus/like")
  public ResponseEntity<ResultSaveDto> handleChangeBookLike( @RequestParam("bookId") String bookSlug,
                                                             @RequestParam("value") String value ) {
    return new ResponseEntity<>( bookService.saveBookLike( bookSlug, value ), HttpStatus.OK );
  }

  @PostMapping("/bookReview")
  public ResponseEntity<ResultSaveDto> handleChangeBookReview( @RequestParam("bookId") String bookSlug,
                                                               @RequestParam("text") String value ) {
    return new ResponseEntity<>( bookService.saveBookReview( bookSlug, value ), HttpStatus.OK );
  }

  @PostMapping("/rateBookReview")
  public ResponseEntity<ResultSaveDto> handleChangeBookReviewLike( @RequestParam("reviewid") String reviewid,
                                                                   @RequestParam("value") String value ) {
    return new ResponseEntity<>( bookService.saveBookReviewLike( reviewid, value ), HttpStatus.OK );
  }

}
