package com.sencha.gxt.examples.resources.server;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;


public final class EMF {
  private static final EntityManagerFactory emfInstance = Persistence.createEntityManagerFactory("openjpa");

  public static EntityManagerFactory get() {
    return emfInstance;
  }

  private EMF() {
    // nothing
  }
}