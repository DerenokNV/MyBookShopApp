package com.example.MyBookShopApp.config;

import com.example.MyBookShopApp.data.TestEntity;
import com.example.MyBookShopApp.data.TestEntityDao;
import jakarta.persistence.EntityManagerFactory;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.util.logging.Logger;

@Configuration
public class CommandLineRunnerImpl implements CommandLineRunner {

  EntityManagerFactory entityManagerFactory;
  TestEntityDao testEntityDao;

  public CommandLineRunnerImpl( EntityManagerFactory entityManagerFactory, TestEntityDao testEntityDao ) {
    this.entityManagerFactory = entityManagerFactory;
    this.testEntityDao = testEntityDao;
  }

  @Override
  public void run(String... args) throws Exception {
    for ( int i=0; i<5; i++ ) {
      createTestEntity( new TestEntity() );
    }

    TestEntity readTestEntity = testEntityDao.findOne( 3L ); //readTestEntityById( 3L );
    if ( readTestEntity != null ) {
      Logger.getLogger( CommandLineRunnerImpl.class.getSimpleName() ).info( readTestEntity.toString() );
    } else {
      throw new NullPointerException();
    }

    TestEntity updateTestEntity = updateTestEntityById( 5L );
    if ( updateTestEntity != null ) {
      Logger.getLogger( CommandLineRunnerImpl.class.getSimpleName() ).info( updateTestEntity.toString() );
    } else {
      throw new NullPointerException();
    }

    deletedTestEntityById( 4L );
  }

  private void createTestEntity( TestEntity entity ) {
    Session session = entityManagerFactory.createEntityManager().unwrap( Session.class );
    Transaction tx = null;

    try {
      tx = session.beginTransaction();
      entity.setData( entity.getClass().getSimpleName() + entity.hashCode() );
      session.persist( entity );
      tx.commit();
    } catch ( HibernateException hex ) {
      if ( tx != null ) {
        tx.rollback();
      } else {
        hex.printStackTrace();
      }
    } finally {
      session.close();
    }
  }

  private TestEntity readTestEntityById( Long id ) {
    Session session = entityManagerFactory.createEntityManager().unwrap( Session.class );
    Transaction tx = null;
    TestEntity result = null;

    try {
      tx = session.beginTransaction();
      result = session.find( TestEntity.class, id );
      tx.commit();
    } catch ( HibernateException hex ) {
      if ( tx != null ) {
        tx.rollback();
      } else {
        hex.printStackTrace();
      }
    } finally {
      session.close();
    }
    return result;
  }

  private TestEntity updateTestEntityById( Long id ) {
    Session session = entityManagerFactory.createEntityManager().unwrap( Session.class );
    Transaction tx = null;
    TestEntity result = null;

    try {
      tx = session.beginTransaction();
      TestEntity findEntity = readTestEntityById( id );
      findEntity.setData( "NEW DATA" );
      result = (TestEntity)session.merge( findEntity );
      tx.commit();
    } catch ( HibernateException hex ) {
      if ( tx != null ) {
        tx.rollback();
      } else {
        hex.printStackTrace();
      }
    } finally {
      session.close();
    }
    return result;
  }

  private void deletedTestEntityById( Long id ) {
    Session session = entityManagerFactory.createEntityManager().unwrap( Session.class );
    Transaction tx = null;

    try {
      tx = session.beginTransaction();
      TestEntity findEntity = readTestEntityById( id );
      TestEntity mergeTest = (TestEntity)session.merge( findEntity );
      session.remove( mergeTest );
      tx.commit();
    } catch ( HibernateException hex ) {
      if ( tx != null ) {
        tx.rollback();
      } else {
        hex.printStackTrace();
      }
    } finally {
      session.close();
    }
  }
}
