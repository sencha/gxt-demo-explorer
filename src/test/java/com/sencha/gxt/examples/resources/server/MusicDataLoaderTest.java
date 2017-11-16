package com.sencha.gxt.examples.resources.server;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.sencha.gxt.examples.resources.server.data.Folder;

public class MusicDataLoaderTest {
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
  public void testMakeFolder() {
    Folder f = MusicDataLoader.makeFolder();
    em.persist(f);

    Assert.assertNotNull(Folder.getRootFolder());
  }
}
