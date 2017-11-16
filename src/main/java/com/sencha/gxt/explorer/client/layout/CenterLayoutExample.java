package com.sencha.gxt.explorer.client.layout;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.explorer.client.app.ui.ExampleContainer;
import com.sencha.gxt.explorer.client.model.Example.Detail;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.container.CenterLayoutContainer;
import com.sencha.gxt.widget.core.client.container.MarginData;

@Detail(
    name = "Center Layout",
    category = "Layouts",
    icon = "centerlayout",
    minHeight = CenterLayoutExample.MIN_HEIGHT,
    minWidth = CenterLayoutExample.MIN_WIDTH
)
public class CenterLayoutExample implements IsWidget, EntryPoint {

  protected static final int MIN_HEIGHT = 100;
  protected static final int MIN_WIDTH = 200;

  private ContentPanel panel;

  @Override
  public Widget asWidget() {
    if (panel == null) {
      CenterLayoutContainer centerLayoutContainer = new CenterLayoutContainer();
      centerLayoutContainer.add(new Label("I should be centered."));

      panel = new ContentPanel();
      panel.setHeading("Center Layout");
      panel.add(centerLayoutContainer, new MarginData(20));

    }

    return panel;
  }

  @Override
  public void onModuleLoad() {
    new ExampleContainer(this)
        .setMinHeight(MIN_HEIGHT)
        .setMinWidth(MIN_WIDTH)
        .doStandalone();
  }

}
