package com.sencha.gxt.explorer.client.app.activity;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.inject.Inject;
import com.sencha.gxt.explorer.client.app.place.ExamplePlace;
import com.sencha.gxt.explorer.client.app.ui.ExampleDetailView;
import com.sencha.gxt.explorer.client.model.Example;
import com.sencha.gxt.explorer.client.model.ExampleModel;

public class ShowExampleActivity extends AbstractActivity {
  @Inject
  private ExampleDetailView detailView;

  @Inject
  private ExampleModel model;
  
  @Inject
  private PlaceController placeController;

  private String exampleId;

  public String getExampleId() {
    return exampleId;
  }

  public void setExampleId(String exampleId) {
    this.exampleId = exampleId;
  }

  @Override
  public void start(AcceptsOneWidget panel, EventBus eventBus) {
    Example example = model.findExample(exampleId);
    if (example != null) {
      detailView.showExample(example);
    } else {
      //TODO this should be checked somewhere else instead?
      Scheduler.get().scheduleDeferred(new ScheduledCommand() {
        @Override
        public void execute() {
          placeController.goTo(new ExamplePlace("overview"));
        }
      });
    }
  }

}
