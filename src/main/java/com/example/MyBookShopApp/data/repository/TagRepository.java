package com.example.MyBookShopApp.data.repository;

import com.example.MyBookShopApp.data.struct.book.tag.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TagRepository extends JpaRepository<Tag,Integer> {

  Tag findTagsById(Integer id );
}
