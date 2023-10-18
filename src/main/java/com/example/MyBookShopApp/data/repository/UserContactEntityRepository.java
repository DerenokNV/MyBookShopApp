package com.example.MyBookShopApp.data.repository;

import com.example.MyBookShopApp.data.struct.user.UserContactEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserContactEntityRepository extends JpaRepository<UserContactEntity, Integer> {

  UserContactEntity findUserContactByUserId( Integer userId );

}
