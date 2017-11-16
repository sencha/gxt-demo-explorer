package com.sencha.gxt.explorer.client;

import java.util.Iterator;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.GWT.UncaughtExceptionHandler;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.junit.client.GWTTestCase;
import com.google.gwt.junit.client.WithProperties;
import com.google.gwt.junit.client.WithProperties.Property;
import com.google.gwt.user.client.ui.RootPanel;
import com.sencha.gxt.core.client.GXT;
import com.sencha.gxt.explorer.client.model.Example;
import com.sencha.gxt.explorer.client.model.ExampleModel;
import com.sencha.gxt.state.client.CookieProvider;
import com.sencha.gxt.state.client.StateManager;

public class GwtTestSamplesLoad extends GWTTestCase {

  @Override
  public String getModuleName() {
    return "com.sencha.gxt.explorer.ExplorerTest";
  }
  private String currentExample;

  @WithProperties(@Property(name = "gxt.theme", value = "blue"))
  public void testSamplesLoad() {
    GWT.setUncaughtExceptionHandler(new UncaughtExceptionHandler() {
      @Override
      public void onUncaughtException(Throwable e) {
        GWT.log("Error loading example " + currentExample, e);
        fail("Example " + currentExample + " failed to load, check log for stack trace " + e.getMessage());
      }
    });

    //Needed for any example with state to not crash
    StateManager.get().setProvider(new CookieProvider("/", null, null, GXT.isSecure()));

    ExampleModel exampleModel = GWT.create(ExampleModel.class);

    final Iterator<Example> iter = exampleModel.getExamplesAsList().iterator();

    final int timeoutMillis = 15000;

    //start each example, waiting some period of time to let it load
    delayTestFinish(timeoutMillis);
    Scheduler.get().scheduleDeferred(new ScheduledCommand() {
      @Override
      public void execute() {
        Example ex = iter.next();
        currentExample = ex.getName();
        RootPanel.get().add(ex.getExample());
        //after starting the example, defer before scheduling the next one
        //this leaves the example an event loop or two to get set up, load from the server

        final ScheduledCommand cmd = this;
        Scheduler.get().scheduleDeferred(new ScheduledCommand() {
          @Override
          public void execute() {
            if (iter.hasNext()) {
              delayTestFinish(timeoutMillis);
              Scheduler.get().scheduleDeferred(cmd);
            } else {
              finishTest();
            }
          }
        });
      }
    });
  }
}
