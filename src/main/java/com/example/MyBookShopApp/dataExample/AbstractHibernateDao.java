package com.example.MyBookShopApp.data;

import jakarta.persistence.EntityManagerFactory;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public abstract class AbstractHibernateDao<T> {
  @Autowired
  EntityManagerFactory entityManagerFactory;

  private Class<T> object;

  public void setObject( Class<T> object ) {
    this.object = object;
  }

  public T findOne( Long id ) {
    return getSession().find( object, id );
  }

  public Session getSession() {
    return entityManagerFactory.createEntityManager().unwrap( Session.class );
  }


}
