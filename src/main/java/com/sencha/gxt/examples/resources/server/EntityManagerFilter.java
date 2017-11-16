package com.sencha.gxt.examples.resources.server;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class EntityManagerFilter implements Filter {

  private static EntityManagerFactory factory = null;

  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
      ServletException {
    EntityManager em = null;
    try {
      em = factory.createEntityManager();
      EntityManagerUtil.MANAGERS.set(em);
      chain.doFilter(request, response);
      EntityManagerUtil.MANAGERS.remove();
    } finally {
      try {
        if (em != null) em.close();
      } catch (Throwable t) {
        t.printStackTrace();
      }
    }
  }

  public void init(FilterConfig config) {
    destroy();
    factory = EMF.get();
  }

  public void destroy() {
    if (factory != null) factory.close();
  }
}
