package com.sencha.gxt.explorer.client.app.ui;

import com.google.gwt.user.client.ui.IsWidget;
import com.sencha.gxt.explorer.client.model.Example;

public interface ExampleDetailView extends IsWidget {

  public interface Presenter {
    void selectExample(Example ex);
  }
  void setPresenter(Presenter listener);

  /**
   * Focuses on the given example
   * 
   * @param example
   */
  void showExample(Example example);
}
