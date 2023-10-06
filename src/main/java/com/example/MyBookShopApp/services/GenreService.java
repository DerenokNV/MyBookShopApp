package com.example.MyBookShopApp.services;

import com.example.MyBookShopApp.data.TreeGenreDto;
import com.example.MyBookShopApp.data.repository.Book2GenreEntityRepository;
import com.example.MyBookShopApp.data.repository.GenreRepository;
import com.example.MyBookShopApp.data.struct.book.links.Book2GenreEntity;
import com.example.MyBookShopApp.data.struct.genre.GenreEntity;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class GenreService {

  private Logger logger = Logger.getLogger(BookService.class);

  private GenreRepository genreRepository;
  private Book2GenreEntityRepository book2GenreEntityRepository;

  @Autowired
  public GenreService( GenreRepository genreRepository, Book2GenreEntityRepository book2GenreEntityRepository ) {
    this.genreRepository = genreRepository;
    this.book2GenreEntityRepository = book2GenreEntityRepository;
  }

  /**
   * Получить жанры для отображения вложенности
   * @return
   */
  public TreeGenreDto getTreeGenres() {
    Map<Integer,Object> treeModel = new HashMap();
    List<GenreEntity> allGenres = genreRepository.findAll();
    if ( allGenres.isEmpty() ) {
      return new TreeGenreDto();
    }

    for ( GenreEntity gen : allGenres ) {
      List<Book2GenreEntity> allLinksOnIdGen = book2GenreEntityRepository.findBook2GenreByGenreId( gen.getId() );
      gen.addGenres( allLinksOnIdGen );
    }

    // закинули основных родителей
    allGenres.stream().filter( x -> x.getParentId() == null ).forEach( params ->  treeModel.put( params.getId(), null ) );

    List<GenreEntity> paramParentGenres = allGenres.stream()
                                                   .filter( x -> x.getParentId() != null )
                                                   .sorted( Comparator.comparing( GenreEntity::getParentId ) )
                                                   .toList();
    for ( GenreEntity gen : paramParentGenres ) {
      addGenresParams( treeModel, gen );
    }
    //logger.info( "TreeGenres treeModel = " + treeModel );

    if ( treeModel.isEmpty() || allGenres.isEmpty() ) {
      return new TreeGenreDto( new HashMap(), new HashMap() );
    } else {
      return new TreeGenreDto( treeModel, allGenres.stream().collect( Collectors.groupingBy( GenreEntity::getId ) ) );
    }

  }

  private void addGenresParams( Map<Integer,Object> result,  GenreEntity gen ) {
    if ( result.containsKey( gen.getParentId() ) ) {
      Map<Integer,Object> obj = (HashMap)result.get( gen.getParentId() );
      if ( obj == null ) {
        Map<Integer,Object> obj1 = new HashMap();
        obj1.put( gen.getId(), null );
        result.put( gen.getParentId(), obj1 );
      } else {
        ((HashMap)result.get( gen.getParentId() )).put( gen.getId(), null );
      }
    } else {
      for ( Map.Entry<Integer, Object> param : result.entrySet() ) {
        if ( param.getValue() == null ) {
          continue;
        }
        addGenresParams( (HashMap)param.getValue(), gen );
      }
    }
  }

  public LinkedList<GenreEntity> getTreeNames( Integer genreId ) {
    LinkedList<GenreEntity> result = new LinkedList();
    List<GenreEntity> allGenres = genreRepository.findAll();
    if (allGenres.isEmpty()) {
      return result;
    }

    Optional<GenreEntity> opt = allGenres.stream().filter( x -> x.getId() == genreId ).findFirst();
    if ( ! opt.isPresent() ) {
      return result;
    }

    GenreEntity parent = opt.get();
    while ( true ) {
      result.add( parent );
      if ( parent.getParentId() == null ) {
        break;
      } else {
        parent = getIdParent( allGenres, parent.getParentId() );
      }
    }

    Collections.reverse( result );
    return result;
  }

  private GenreEntity getIdParent( List<GenreEntity> allGenres, Integer parentId ) {
    Optional<GenreEntity> opt = allGenres.stream().filter( x -> x.getId() == parentId ).findFirst();
    if ( ! opt.isPresent() ) {
      return null;
    }
    return opt.get();
  }

}
