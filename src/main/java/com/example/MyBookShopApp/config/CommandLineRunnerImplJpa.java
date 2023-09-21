package com.example.MyBookShopApp.config;

import com.example.MyBookShopApp.data.BookRepository;
import com.example.MyBookShopApp.dataExample.TestEntity;
import com.example.MyBookShopApp.dataExample.TestEntityCrudRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.util.Optional;
import java.util.logging.Logger;

//@Configuration
public class CommandLineRunnerImplJpa implements CommandLineRunner {

  TestEntityCrudRepository testEntityCrudRepository;
  BookRepository bookRepository;

  @Autowired
  public CommandLineRunnerImplJpa( TestEntityCrudRepository testEntityCrudRepository, BookRepository bookRepository ) {
    this.testEntityCrudRepository = testEntityCrudRepository;
    this.bookRepository = bookRepository;
  }

  @Override
  public void run(String... args) throws Exception {
    for ( int i=0; i<5; i++ ) {
      createTestEntity( new TestEntity() );
    }

    TestEntity readTestEntity = readTestEntityById( 3L );
    if ( readTestEntity != null ) {
      Logger.getLogger( CommandLineRunnerImplHibenate.class.getSimpleName() ).info( readTestEntity.toString() );
    } else {
      throw new NullPointerException();
    }

    TestEntity updateTestEntity = updateTestEntityById( 5L );
    if ( updateTestEntity != null ) {
      Logger.getLogger( CommandLineRunnerImplHibenate.class.getSimpleName() ).info( updateTestEntity.toString() );
    } else {
      throw new NullPointerException();
    }

    deletedTestEntityById( 4L );

    //Logger.getLogger( CommandLineRunnerImplJpa.class.getSimpleName() ).info( bookRepository.findBooksByName( "Karia" ).toString() );
    //Logger.getLogger( CommandLineRunnerImplJpa.class.getSimpleName() ).info( bookRepository.customFindAllBooks().toString() );

  }

  private void createTestEntity( TestEntity entity ) {
    entity.setData( entity.getClass().getSimpleName() + entity.hashCode() );
    testEntityCrudRepository.save( entity );
  }

  private TestEntity readTestEntityById( Long id ) {
    return testEntityCrudRepository.findById( id ).get();
  }

  private TestEntity updateTestEntityById( Long id ) {
    TestEntity findEntity = testEntityCrudRepository.findById( id ).get();
    findEntity.setData( "GOTO ASD" );
    testEntityCrudRepository.save( findEntity );
    return findEntity;
  }

  private void deletedTestEntityById( Long id ) {
    Optional<TestEntity> optional = testEntityCrudRepository.findById( id );
    if ( optional.isPresent() ) {
      testEntityCrudRepository.delete( optional.get() );
    }
  }
}
