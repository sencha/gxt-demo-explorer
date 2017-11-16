package com.sencha.gxt.explorer.server;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

import com.google.web.bindery.requestfactory.server.RequestFactoryServlet;
import com.sencha.gxt.examples.resources.server.MusicDataLoader;

public class DbSetupRfServlet extends RequestFactoryServlet {
  @Override
  public void init(ServletConfig config) throws ServletException {
    super.init(config);
    MusicDataLoader.initMusic(null);
  }
}
