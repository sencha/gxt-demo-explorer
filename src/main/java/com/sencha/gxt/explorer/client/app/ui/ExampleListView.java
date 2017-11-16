package com.sencha.gxt.explorer.client.app.ui;

import com.google.gwt.place.shared.PlaceChangeEvent;
import com.google.gwt.user.client.ui.IsWidget;
import com.sencha.gxt.explorer.client.model.Example;

public interface ExampleListView extends IsWidget, PlaceChangeEvent.Handler {

  public interface Presenter {
    void selectExample(Example ex);
  }

  void setPresenter(Presenter listener);

}
