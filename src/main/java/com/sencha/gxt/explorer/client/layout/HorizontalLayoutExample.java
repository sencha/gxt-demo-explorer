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
import com.sencha.gxt.widget.core.client.container.HorizontalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.HorizontalLayoutContainer.HorizontalLayoutData;

@Detail(
    name = "Horizontal Layout",
    category = "Layouts",
    icon = "horizontallayout",
    minHeight = HorizontalLayoutExample.MIN_HEIGHT,
    minWidth = HorizontalLayoutExample.MIN_WIDTH,
    classes = { Utils.class }
)
public class HorizontalLayoutExample implements IsWidget, EntryPoint {

  protected static final int MIN_HEIGHT = 150;
  protected static final int MIN_WIDTH = 480;

  private ContentPanel panel;

  @Override
  public Widget asWidget() {
    if (panel == null) {
      HorizontalLayoutContainer hlc = new HorizontalLayoutContainer();
      hlc.add(createLabel("Test Label 1"), new HorizontalLayoutData(0.25, 1, new Margins(10)));
      hlc.add(createLabel("Test Label 2"), new HorizontalLayoutData(0.5, 1, new Margins(10, 0, 10, 0)));
      hlc.add(createLabel("Test Label 3"), new HorizontalLayoutData(0.25, 1, new Margins(10)));

      panel = new ContentPanel();
      panel.setHeading("Horizontal Layout");
      panel.setPixelSize(420, 320);
      panel.add(hlc);
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
