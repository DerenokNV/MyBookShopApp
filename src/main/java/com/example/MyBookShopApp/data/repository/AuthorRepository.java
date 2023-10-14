package com.example.MyBookShopApp.data.repository;

import com.example.MyBookShopApp.data.struct.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepository extends JpaRepository<Author,Integer> {

  Author findAuthorsById( Integer id );

}
