package com.sencha.gxt.explorer.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.sencha.gxt.core.client.GXT;
import com.sencha.gxt.explorer.client.app.ioc.ExplorerGinjector;
import com.sencha.gxt.state.client.CookieProvider;
import com.sencha.gxt.state.client.StateManager;

public class Explorer implements EntryPoint {

  private final ExplorerGinjector injector = GWT.create(ExplorerGinjector.class);

  @Override
  public void onModuleLoad() {
    Scheduler.get().scheduleDeferred(new ScheduledCommand() {

      @Override
      public void execute() {
        StateManager.get().setProvider(new CookieProvider("/", null, null, GXT.isSecure()));

        ExplorerApp app = injector.getApp();
        app.run();

        onReady();
      }

    });
  }

  private native void onReady() /*-{
		if (typeof $wnd.GxtReady != 'undefined') {
			$wnd.GxtReady();
		}
  }-*/;

}
