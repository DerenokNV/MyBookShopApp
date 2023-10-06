package com.example.MyBookShopApp.data.repository;

import com.example.MyBookShopApp.data.struct.genre.GenreEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GenreRepository extends JpaRepository<GenreEntity,Integer> {

}
