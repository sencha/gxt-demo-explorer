package com.sencha.gxt.explorer.client.app.ioc;

import com.google.gwt.inject.client.GinModules;
import com.google.gwt.inject.client.Ginjector;
import com.sencha.gxt.explorer.client.ExplorerApp;

@GinModules(ExplorerModule.class)
public interface ExplorerGinjector extends Ginjector {

  ExplorerApp getApp();
}
