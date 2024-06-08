/**
 * Sencha GXT 1.0.0-SNAPSHOT - Sencha for GWT
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
package com.sencha.gxt.explorer.client;

import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;

import com.google.gwt.core.ext.GeneratorContext;
import com.google.gwt.core.ext.StubGeneratorContext;
import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.UnableToCompleteException;
import com.google.gwt.dev.javac.StandardGeneratorContext;
import com.google.gwt.dev.util.log.CompositeTreeLogger;
import com.sencha.gxt.core.client.GXT;

import com.sencha.gxt.explorer.rebind.SampleGenerator;
import com.sencha.gxt.state.client.CookieProvider;
import com.sencha.gxt.state.client.StateManager;




public class Explorer  implements EntryPoint {

 // private final ExplorerGinjector injector = GWT.create(ExplorerGinjector.class);


  @Override
  public  void onModuleLoad() {
    Scheduler.get().scheduleDeferred(new ScheduledCommand() {
      @Override
      public void execute() {
       /* SampleGenerator generator= new SampleGenerator();
        GeneratorContext context= new StubGeneratorContext();
        TreeLogger logger =new CompositeTreeLogger();
        String typeName="com.sencha.gxt.explorer.client.model.ExampleModel";
          try {
              generator.generate(logger,context,typeName);
          } catch (UnableToCompleteException e) {
              throw new RuntimeException(e);
          }*/
      //  ExplorerModule explorerModule= new ExplorerModule();
       // explorerModule.configure();
        StateManager.get().setProvider(new CookieProvider("/", null, null, GXT.isSecure()));
        ExplorerApp app = new ExplorerApp();
        app.run();
        onReady();
      }
    });

    /*TextButton button= new TextButton("Enter");
    RootPanel.get().add(button);*/
  }

  private native void onReady() /*-{
		if (typeof $wnd.GxtReady != 'undefined') {
			$wnd.GxtReady();
		}
  }-*/;




}
