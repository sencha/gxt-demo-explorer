package com.sencha.gxt.examples.resources.server;

import javax.persistence.EntityManager;

public class EntityManagerUtil {

  public static final ThreadLocal<EntityManager> MANAGERS = new ThreadLocal<EntityManager>();

  public static EntityManager getEntityManager() {
    EntityManager mgr = MANAGERS.get();
    // if manager is null assume we are not running in a web context
    if (mgr == null) {
      mgr = EMF.get().createEntityManager();
      MANAGERS.set(mgr);
      return mgr;
    }
    return MANAGERS.get();
  }
}
