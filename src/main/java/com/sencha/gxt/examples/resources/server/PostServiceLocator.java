package com.sencha.gxt.examples.resources.server;

import com.google.web.bindery.requestfactory.shared.ServiceLocator;

public class PostServiceLocator implements ServiceLocator {

  @Override
  public Object getInstance(Class<?> clazz) {
    return new PostService();
  }

}
