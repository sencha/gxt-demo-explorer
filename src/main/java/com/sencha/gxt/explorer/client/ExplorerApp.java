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

import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gwt.activity.shared.ActivityManager;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.place.shared.PlaceChangeEvent;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.place.shared.PlaceHistoryHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.RootPanel;

import com.sencha.gxt.core.client.GXT;
import com.sencha.gxt.explorer.client.app.mvp.ExplorerActivityMapper;
import com.sencha.gxt.explorer.client.app.mvp.ExplorerPlaceHistoryMapper;
import com.sencha.gxt.explorer.client.app.place.ExamplePlace;
import com.sencha.gxt.explorer.client.app.ui.*;
import com.sencha.gxt.explorer.client.model.Example;
import com.sencha.gxt.explorer.client.model.ExampleModel;
import com.sencha.gxt.widget.core.client.container.Viewport;
import com.sencha.gxt.widget.core.client.info.Info;

public class ExplorerApp implements ExampleListView.Presenter, ExampleDetailView.Presenter{

  private static final Logger log = Logger.getLogger(ExplorerApp.class.getName());
  public static final String OVERVIEW = "Overview";
  public static final String OVERVIEW_CATEGORY = "overview category";

 // @Inject
     public static EventBus eventBus = new SimpleEventBus();

    //@Inject
    public static ExampleModel exampleModel = GWT.create(ExampleModel.class);
    public static PlaceController placeController = new PlaceController(eventBus);

//  @Inject
     public static ActivityMapper activityMapper = new ExplorerActivityMapper();

 // @Inject
 public static ExampleListView listView = new ExampleListViewImpl(exampleModel);

//  @Inject
public static ExampleDetailView detailView = new ExampleDetailViewImpl(exampleModel) ;
 /* @Inject*/
 public static ExplorerShell shell = new ExplorerShell(listView,detailView);





  public void run() {
      init();
  }

  @SuppressWarnings("deprecation")
  private void init() {

    GWT.setUncaughtExceptionHandler(new GWT.UncaughtExceptionHandler() {
      @Override
      public void onUncaughtException(Throwable e) {
        Window.alert("Error: " + e.getMessage());
        log.log(Level.SEVERE, e.getMessage(), e);
        e.printStackTrace();
      }
    });

    // Start ActivityManager for the main widget with our ActivityMapper
    ActivityManager activityManager = new ActivityManager(activityMapper, eventBus);
    activityManager.setDisplay(shell.getDisplay());

    // Start PlaceHistoryHandler with our PlaceHistoryMapper
    ExplorerPlaceHistoryMapper historyMapper = GWT.create(ExplorerPlaceHistoryMapper.class);

    final PlaceHistoryHandler historyHandler = new PlaceHistoryHandler(historyMapper);
    historyHandler.register(placeController, eventBus, new ExamplePlace(OVERVIEW.toLowerCase()));

    listView.setPresenter(this);
    detailView.setPresenter(this);

    eventBus.addHandler(PlaceChangeEvent.TYPE, listView);

    Example example = exampleModel.findExample(OVERVIEW.toLowerCase());

    OverviewExample oExample = (OverviewExample) example.getExample();
    oExample.setPlaceController(placeController);
    oExample.loadData(exampleModel);

    Viewport vp = new Viewport();
    vp.setWidget(shell);
    vp.setTouchKeyboardAdjustPan(true);
    RootPanel.get().add(vp);

    // Goes to place represented on URL or default place
    historyHandler.handleCurrentHistory();

  }

  @Override
  public void selectExample(Example example) {
    //Info.display("selectExample", "ex=" + example.getId());
    placeController.goTo(new ExamplePlace(example.getId()));
  }

}
