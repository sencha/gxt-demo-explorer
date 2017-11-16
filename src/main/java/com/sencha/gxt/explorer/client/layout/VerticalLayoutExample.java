package com.sencha.gxt.explorer.client.layout;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.core.client.resources.ThemeStyles;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.examples.resources.client.Utils;
import com.sencha.gxt.examples.resources.client.Utils.Theme;
import com.sencha.gxt.explorer.client.app.ui.ExampleContainer;
import com.sencha.gxt.explorer.client.model.Example.Detail;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;

@Detail(
    name = "Vertical Layout",
    category = "Layouts",
    icon = "verticallayout",
    minHeight = VerticalLayoutExample.MIN_HEIGHT,
    minWidth = VerticalLayoutExample.MIN_WIDTH,
    classes = { Utils.class }
)
public class VerticalLayoutExample implements IsWidget, EntryPoint {

  protected static final int MIN_HEIGHT = 320;
  protected static final int MIN_WIDTH = 150;

  private ContentPanel panel;

  @Override
  public Widget asWidget() {
    if (panel == null) {
      VerticalLayoutContainer vlc = new VerticalLayoutContainer();
      vlc.add(createLabel("Test Label 1"), new VerticalLayoutData(1, 0.25, new Margins(10)));
      vlc.add(createLabel("Test Label 2"), new VerticalLayoutData(1, 0.5, new Margins(0, 10, 0, 10)));
      vlc.add(createLabel("Test Label 3"), new VerticalLayoutData(1, 0.25, new Margins(10)));

      panel = new ContentPanel();
      panel.setHeading("Vertical Layout");
      panel.add(vlc);
    }

    return panel;
  }

  private Label createLabel(String text) {
    Label label = new Label(text);
    label.getElement().getStyle().setProperty("whiteSpace", "nowrap");
    if (Theme.BLUE.isActive() || Theme.GRAY.isActive()) {
      label.addStyleName(ThemeStyles.get().style().border());
    }
    label.addStyleName("pad-text gray-bg");

    return label;
  }

  @Override
  public void onModuleLoad() {
    new ExampleContainer(this)
        .setMinHeight(MIN_HEIGHT)
        .setMinWidth(MIN_WIDTH)
        .doStandalone();
  }

}
