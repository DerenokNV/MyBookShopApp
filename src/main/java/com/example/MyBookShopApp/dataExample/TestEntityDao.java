package com.example.MyBookShopApp.dataExample;

import org.springframework.stereotype.Repository;

@Repository
public class TestEntityDao extends AbstractHibernateDao<TestEntity>{

  public TestEntityDao() {
    super();
    setObject( TestEntity.class );
  }

}
