package com.sencha.gxt.examples.resources.server;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class DataLoader implements ServletContextListener {

  @Override
  public void contextDestroyed(ServletContextEvent event) {

  }

  @Override
  public void contextInitialized(ServletContextEvent event) {
    MusicDataLoader.initMusic(event);
  }

}
