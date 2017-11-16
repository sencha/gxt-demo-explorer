package com.sencha.gxt.examples.resources.server.data;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.sencha.gxt.examples.resources.server.EntityManagerUtil;

public class FolderTest {
  private static EntityManager em;
  private EntityTransaction tx;

  @BeforeClass
  public static void setupEm() {
    em = EntityManagerUtil.getEntityManager();
  }

  @Before
  public void setupTx() {
    tx = em.getTransaction();
    tx.begin();
  }

  @After
  public void teardownTx() {
    tx.rollback();
  }

  @Test
  public void testCreatePersist() {
    Folder f1 = new Folder();
    f1.setName("f1");
    Folder f2 = new Folder();
    f2.setName("f2");

    f1 = f1.persist();
    f2 = f2.persist();

    Assert.assertTrue(f1.getId() > 0);
    Assert.assertNotSame(f1.getId(), f2.getId());
  }
}
