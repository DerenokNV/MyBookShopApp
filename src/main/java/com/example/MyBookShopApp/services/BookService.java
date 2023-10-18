package com.example.MyBookShopApp.services;

import com.example.MyBookShopApp.Toolkit.Toolkit;
import com.example.MyBookShopApp.data.RatingBookDto;
import com.example.MyBookShopApp.data.ResultSaveDto;
import com.example.MyBookShopApp.data.ReviewInfoDto;
import com.example.MyBookShopApp.data.repository.*;
import com.example.MyBookShopApp.data.struct.Author;
import com.example.MyBookShopApp.data.struct.Book;
import com.example.MyBookShopApp.data.struct.book.links.Book2AuthorEntity;
import com.example.MyBookShopApp.data.struct.book.review.BookLikeEntity;
import com.example.MyBookShopApp.data.struct.book.review.BookReviewEntity;
import com.example.MyBookShopApp.data.struct.book.review.BookReviewLikeEntity;
import com.example.MyBookShopApp.errs.BookstoreApiWrongParameterException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class BookService {

  private final BookRepository bookRepository;
  private final AuthorRepository authorRepository;
  private final Book2AuthorEntityRepository book2AuthorEntityRepository;
  private final TagService tagService;

  private final BookLikeEntityRepository bookLikeEntityRepository;
  private final BookReviewEntityRepository bookReviewEntityRepository;
  private final UserEntityRepository userEntityRepository;
  private final BookReviewLikeEntityRepository bookReviewLikeEntityRepository;

  @Autowired
  public BookService( BookRepository bookRepository, AuthorRepository authorRepository, Book2AuthorEntityRepository book2AuthorEntityRepository,
                      TagService tagService, BookLikeEntityRepository bookLikeEntityRepository, BookReviewEntityRepository bookReviewEntityRepository,
                      UserEntityRepository userEntityRepository, BookReviewLikeEntityRepository bookReviewLikeEntityRepository ) {
    this.bookRepository = bookRepository;
    this.authorRepository = authorRepository;
    this.book2AuthorEntityRepository = book2AuthorEntityRepository;
    this.tagService = tagService;
    this.bookLikeEntityRepository = bookLikeEntityRepository;
    this.bookReviewEntityRepository = bookReviewEntityRepository;
    this.userEntityRepository = userEntityRepository;
    this.bookReviewLikeEntityRepository = bookReviewLikeEntityRepository;
  }

  public List<Book> getBooksByAuthor( String author ) {
    List<Book> result = new ArrayList();
    List<Book> booksList =  getBooksData();
    for( Book bookList : booksList ) {
      Set<Author> bookAuthor = bookList.getAuthors();
      Optional<Author> optAuthor = bookAuthor.stream().filter( x -> x.getName().contains( author ) ).findFirst();
      if ( optAuthor.isPresent() ) {
        result.add( bookList );
      }
    }

    return result;
  }

  /**
   * Получаем всех авторов
   * @param bookList
   */
  public void addAuthorsInBook( List<Book> bookList ) {
    if ( bookList == null || bookList.isEmpty()) {
      return;
    }
    for (Book book : bookList) {
      List<Book2AuthorEntity> linkList = book2AuthorEntityRepository.findBook2authorByBookId( book.getId() );
      for (Book2AuthorEntity link : linkList) {
        Optional<Author> oiptAuthor = authorRepository.findById( link.getAuthor().getId() );
        if ( oiptAuthor.isPresent() ) {
          book.addAuthor( oiptAuthor.get() );
        }
      }

      book.setAllAuthors();
    }
  }

  public List<Book> getBooksData() {
    List<Book> bookList = bookRepository.findAll();
    addAuthorsInBook( bookList );
    return bookList;
  }

  public Page<Book> getPageOfRecommendedBooks( Integer offset, Integer limit ) {
    Pageable nextPage = PageRequest.of( offset, limit );
    Page<Book> booksPage = bookRepository.findAll( nextPage );
    addAuthorsInBook( booksPage.getContent() );
    return bookRepository.findAll( nextPage );
  }

  public Page<Book> getPageOfRecentBooks( LocalDateTime dtFrom, LocalDateTime dtTo, Integer offset, Integer limit ) {
    Pageable nextPage = PageRequest.of( offset, limit );
    dtFrom = dtFrom == null ? LocalDateTime.now().minusMonths( 3 ) : dtFrom;
    dtTo = dtTo == null ? LocalDateTime.now() : dtTo;
    Page<Book> booksPage = bookRepository.findBookByPubDateBetween( dtFrom, dtTo, nextPage );
    addAuthorsInBook( booksPage.getContent() );
    try {
      return booksPage;
    } catch ( Exception ex ) {
      return getPageOfRecommendedBooks( offset, limit );
    }
  }

  public Page<Book> getPageOfPopularBooks( Integer offset, Integer limit ) {
    Pageable nextPage = PageRequest.of( offset, limit );
    Page<Book> booksPage = bookRepository.customFindBookByIsBestseller( nextPage );
    addAuthorsInBook( booksPage.getContent() );
    try {
      return booksPage;
    } catch ( Exception ex ) {
      return getPageOfRecommendedBooks( offset, limit );
    }
  }

  public Page<Book> getPageOfSearchResultBooks( String searchWord, Integer offset, Integer limit ) {
    Pageable nextPage = PageRequest.of( offset, limit );
    return bookRepository.findBookByTitleContaining( searchWord, nextPage);
  }

  /**
   * Получить книги по Названию ( title% )
   * @param title - искомое название
   * @return - список книг
   * @throws BookstoreApiWrongParameterException - ошибка
   */
  public List<Book> getBooksByTitle( String title ) throws BookstoreApiWrongParameterException {
    if ( title == null || title.isEmpty() ) {
      throw new BookstoreApiWrongParameterException( "Wrong values passed to one or more parameters" );
    } else {
      List<Book> data = bookRepository.findBooksByTitleContaining(title);
      if ( data != null && ! data.isEmpty() ){
        return data;
      } else {
        throw new BookstoreApiWrongParameterException( "No data found with specified parameters..." );
      }
    }
  }

  /**
   * Тут мы получаем пачками книги по тегам
   * @param tagId - ID тэга
   * @param offset - сдвиг
   * @param limit - лимит
   * @return - пачка книг
   */
  public Page<Book> getAllBooksContainsTag( Integer tagId, Integer offset, Integer limit ) {
    Pageable nextPage = PageRequest.of( offset, limit );
    return bookRepository.customFindBookByIdTag( tagId, nextPage );
  }

  /**
   * Тут мы получаем пачками книги по жанрам
   * @param genreId - id жанра
   * @param offset - сдвиг
   * @param limit - лимит
   * @return - пачка книг
   */
  public Page<Book> getAllBooksContainsGenre( Integer genreId, Integer offset, Integer limit ) {
    Pageable nextPage = PageRequest.of( offset, limit );
    return bookRepository.customFindBookByIdGenre( genreId, nextPage );
  }

  public Book getBookBySlug( String slug ) {
    Book result = bookRepository.findBookBySlug( slug );
    addAuthorsInBook( Arrays.asList( result ) );
    tagService.addTadsInBook( Arrays.asList( result ) );
    return result;
  }

  public List<Book> getBooksBySlugIn( String[] cookieSlugs ) {
    List<Book> result = bookRepository.findBooksBySlugIn( cookieSlugs );
    addAuthorsInBook( result );
    return result;
  }

  /**
   * Получить данные по оценкам и рейтингу книги
   * @param slug - мнемонический идентификатор книги
   * @return - объект рейтинга книги, (все оценки, среднее и сумма)
   */
  public RatingBookDto getLikeBookById( String slug ) {
    //Integer rating = bookLikeEntityRepository.customGetBookLikeByBookId( bookId );
    Book book = getBookBySlug( slug );
    List<BookLikeEntity> list = bookLikeEntityRepository.findAllByBookId( book.getId() );
    if ( list == null || list.isEmpty() ) {
      return new RatingBookDto();
    }
    Map<Integer,List<BookLikeEntity>> map = list.stream().collect(Collectors.groupingBy( BookLikeEntity::getCountLike ) );
    Long rating = list.stream().mapToLong(BookLikeEntity::getCountLike).sum() / list.size();
    Integer allRating = list.size();

    return new RatingBookDto( map, rating, allRating );
  }

  /**
   * Сохраняем оценки для книг
   * @param bookSlug - мнемонический идентификатор книги
   * @param value - кол-во зведочек
   * @return - успех/(неуспех+сообщ об ошибке)
   */
  public ResultSaveDto saveBookLike( String bookSlug, String value ) {
    Book book = getBookBySlug( bookSlug );
    BookLikeEntity result = new BookLikeEntity( -1, book.getId(), 1, LocalDateTime.now(), Integer.parseInt( value ) );
    try {
      bookLikeEntityRepository.save( result );
      return new ResultSaveDto(  true, null );
    } catch ( Exception ex ){
      return new ResultSaveDto(  false, ex.getMessage() );
    }
  }

  /**
   * Сохранит отзыв на книгу
   * @param bookSlug - мнемонический идентификатор книги
   * @param value - отзыв
   * @return - успех/(неуспех+сообщ об ошибке)
   */
  public ResultSaveDto saveBookReview( String bookSlug, String value ) {
    Book book = getBookBySlug( bookSlug );
    BookReviewEntity result = new BookReviewEntity( -1, book.getId(), 2, LocalDateTime.now(), value );
    try {
      bookReviewEntityRepository.save( result );
      return new ResultSaveDto(  true, null );
    } catch ( Exception ex ){
      return new ResultSaveDto(  false, ex.getMessage() );
    }
  }

  /**
   * Возвращаем комментарии к книгам
   * @param slug - мнемонический идентификатор книги
   * @return - список ( комментарий + пользователь + лайки/дизлайки)
   */
  public List<ReviewInfoDto> getReviewBookById(String slug ) {
    List<ReviewInfoDto> result = new ArrayList<>();

    Book book = getBookBySlug(slug);
    List<BookReviewEntity> reviews = bookReviewEntityRepository.findAllByBookId( book.getId() );

    if ( reviews.isEmpty() ) {
      return result;
    }

    // подцепим информацию о пользователе
    for ( BookReviewEntity res : reviews ) {
      ReviewInfoDto param = new ReviewInfoDto();
      param.setReview( res );
      param.setUser( userEntityRepository.findUserById( res.getUserId() ) );
      result.add( param );
    }

    // лайки и дизлайки комментариев
    for ( ReviewInfoDto res : result ) {
      List<BookReviewLikeEntity> reviewLike = bookReviewLikeEntityRepository.findBookReviewLikeByReviewId( res.getReview().getId() );
      if ( reviewLike == null || reviewLike.isEmpty() ) {
        continue;
      }
      res.setLike( reviewLike.stream().filter( x -> x.getValue() == 1 ).collect( Collectors.toSet() ) );
      res.setLike( reviewLike.stream().filter( x -> x.getValue() != 1 ).collect( Collectors.toSet() ) );
    }

    return result;
  }

  /**
   * Сохраняем лайки на отзывы
   * @param reviewid - код отзыва
   * @param value - лайк или дизлайк (1/-1)
   * @return - успех или нет+сообщение об ошибке
   */
  public ResultSaveDto saveBookReviewLike( String reviewid, String value ) {
    BookReviewLikeEntity param = new BookReviewLikeEntity( -1, Integer.parseInt( reviewid ), Toolkit.userId, LocalDateTime.now(), Integer.valueOf( value ).shortValue() );

    try {
      bookReviewLikeEntityRepository.save( param );
      return new ResultSaveDto(  true, null );
    } catch ( Exception ex ){
      return new ResultSaveDto(  false, ex.getMessage() );
    }
  }

}
