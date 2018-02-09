package com.sencha.gxt.explorer.client.app.mvp;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.place.shared.Place;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.sencha.gxt.explorer.client.app.activity.ShowExampleActivity;
import com.sencha.gxt.explorer.client.app.place.ExamplePlace;

public class ExplorerActivityMapper implements ActivityMapper {
  @Inject
  private Provider<ShowExampleActivity> exampleActivity;

  @Override
  public Activity getActivity(Place place) {
    if (place instanceof ExamplePlace) {
      ExamplePlace ep = (ExamplePlace) place;
      ShowExampleActivity ex = exampleActivity.get();
      ex.setExampleId(ep.getExampleId());
      return ex;
    }

    return null;
  }

}
