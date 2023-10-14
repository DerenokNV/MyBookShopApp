package com.example.MyBookShopApp.services;

import com.example.MyBookShopApp.data.struct.Book;
import com.example.MyBookShopApp.data.repository.Book2TagEntityRepository;
import com.example.MyBookShopApp.data.repository.TagRepository;
import com.example.MyBookShopApp.data.struct.book.links.Book2TagEntity;
import com.example.MyBookShopApp.data.struct.book.tag.Tag;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class TagService {

  private final TagRepository tagRepository;
  private final Book2TagEntityRepository book2TagEntityRepository;

  @Autowired
  public TagService( TagRepository tagRepository, Book2TagEntityRepository book2TagEntityRepository ) {
    this.tagRepository = tagRepository;
    this.book2TagEntityRepository = book2TagEntityRepository;
  }

  public List<Tag> getTagsData() {
    List<Tag> tagList = tagRepository.findAll();
    getTagBooksData();
    return tagList;
  }

  public Tag getTagsDataByTagId( Integer tagId ) {
    return tagRepository.findTagsById( tagId );
  }

  public Map<Tag,Long> getTagBooksData() {
    Map<Tag,Long> result = new HashMap<>();
    List<Tag> tagList = tagRepository.findAll();
    for ( Tag tag : tagList ) {
      List<Book2TagEntity> linkList = book2TagEntityRepository.findBook2TagByTagId( tag.getId() );
      result.put( tag, Long.valueOf( linkList.isEmpty() ? 0 : linkList.size() ) );
    }
    return result;
  }

  /**
   * Закиним книгам их тэги
   * @param bookList - список книг
   */
  public void addTadsInBook( List<Book> bookList ) {
    if ( bookList == null || bookList.isEmpty()) {
      return;
    }
    for (Book book : bookList) {
      List<Book2TagEntity> linkList = book2TagEntityRepository.findBook2TagByBookId( book.getId() );
      for (Book2TagEntity link : linkList) {
        Optional<Tag> oiptTag = tagRepository.findById( link.getTag().getId() );
        if ( oiptTag.isPresent() ) {
          book.addTag( oiptTag.get() );
        }
      }
    }
  }

}
