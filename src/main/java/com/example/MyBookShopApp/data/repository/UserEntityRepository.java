package com.example.MyBookShopApp.data.repository;

import com.example.MyBookShopApp.data.struct.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserEntityRepository extends JpaRepository<UserEntity, Integer> {
  UserEntity findUserById( Integer id );

  @Query( value = " select u.* " +
                  " from users u " +
                  "     join user_contact uc on u.id = uc.user_id " +
                  "                      and uc.contact = :userEmail ",
          nativeQuery = true )
  UserEntity customGetUserByEmail( String userEmail );

}
