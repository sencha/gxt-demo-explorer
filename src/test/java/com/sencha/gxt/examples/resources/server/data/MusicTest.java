/**
 * Sencha GXT 1.1.0-SNAPSHOT - Sencha for GWT
 * Copyright (c) 2006-2021, Sencha Inc.
 *
 * licensing@sencha.com
 * http://www.sencha.com/products/gxt/license/
 *
 * ================================================================================
 * Commercial License
 * ================================================================================
 * This version of Sencha GXT is licensed commercially and is the appropriate
 * option for the vast majority of use cases.
 *
 * Please see the Sencha GXT Licensing page at:
 * http://www.sencha.com/products/gxt/license/
 *
 * For clarification or additional options, please contact:
 * licensing@sencha.com
 * ================================================================================
 *
 *
 *
 *
 *
 *
 *
 *
 * ================================================================================
 * Disclaimer
 * ================================================================================
 * THIS SOFTWARE IS DISTRIBUTED "AS-IS" WITHOUT ANY WARRANTIES, CONDITIONS AND
 * REPRESENTATIONS WHETHER EXPRESS OR IMPLIED, INCLUDING WITHOUT LIMITATION THE
 * IMPLIED WARRANTIES AND CONDITIONS OF MERCHANTABILITY, MERCHANTABLE QUALITY,
 * FITNESS FOR A PARTICULAR PURPOSE, DURABILITY, NON-INFRINGEMENT, PERFORMANCE AND
 * THOSE ARISING BY STATUTE OR FROM CUSTOM OR USAGE OF TRADE OR COURSE OF DEALING.
 * ================================================================================
 */
package com.sencha.gxt.examples.resources.server.data;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.sencha.gxt.examples.resources.server.EntityManagerUtil;

import java.util.List;


public class MusicTest {
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
    // EXTGWT-4713 - we're no longer persisting to the database
    /*
     * can't figure out how to instantiate new Music() { ... } to override getHttpSession method
     * wind up with this error: No metadata was found for type "class com.sencha.gxt.examples.resources.server.data.MusicTest$1".
     * The class does not appear in the list of persistent types: [com.sencha.gxt.examples.resources.server.data.Music,
     * com.sencha.gxt.examples.resources.server.data.Folder].
     *
     * not spending any more time on this for now
     */
    // final MockHttpSession mockSession = new MockHttpSession();

    // this isn't testing music in isolation, but need to test music ID is generated when persisting folder
    Music m1 = new Music();
    m1.setAuthor("asdf");
    m1.setGenre("fdsa");
    Music m2 = new Music();
    m2.setAuthor("1234");
    m2.setGenre("4321");
    Folder f1 = new Folder();
    f1.setName("f1");
    f1.addMusic(m1);
    f1.addMusic(m2);
    f1 = f1.persist();

    List<Music> children = f1.getChildren();
    Assert.assertTrue(children.size() == 2);

    m1 = children.get(0);
    m2 = children.get(1);

    Assert.assertTrue(m1.getId() > 0);
    Assert.assertTrue(m2.getId() > 0);
    Assert.assertNotSame(m1.getId(), m2.getId());

    /*
     * if the above issue is fixed, uncomment the following lines
     */
    // Assert.assertTrue(mockSession.attributes.size() == 0);
    // m1.persist();
    // m2.persist();
    // Assert.assertTrue(mockSession.attributes.size() == 2);
  }
}
