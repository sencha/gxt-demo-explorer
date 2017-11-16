package com.sencha.gxt.explorer.client.layout;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.core.client.util.ToggleGroup;
import com.sencha.gxt.examples.resources.client.Utils;
import com.sencha.gxt.examples.resources.client.Utils.Theme;
import com.sencha.gxt.explorer.client.app.ui.ExampleContainer;
import com.sencha.gxt.explorer.client.model.Example.Detail;
import com.sencha.gxt.widget.core.client.button.ToggleButton;
import com.sencha.gxt.widget.core.client.container.CardLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VBoxLayoutContainer;
import com.sencha.gxt.widget.core.client.event.SelectEvent;

@Detail(
  name = "Horizontal Box Layout (UiBinder)",
  category = "Layouts",
  icon = "hboxlayoutuibinder",
  files = "HBoxLayoutUiBinderExample.ui.xml",
  minHeight = HBoxLayoutUiBinderExample.MIN_HEIGHT,
  minWidth = HBoxLayoutUiBinderExample.MIN_WIDTH,
  classes = { Utils.class })
public class HBoxLayoutUiBinderExample implements IsWidget, EntryPoint {

  protected static final int MIN_HEIGHT = 480;
  protected static final int MIN_WIDTH = 480;

  private static MyUiBinder uiBinder = GWT.create(MyUiBinder.class);

  interface MyUiBinder extends UiBinder<Widget, HBoxLayoutUiBinderExample> {
  }

  @UiField(provided = true)
  String toolbarStyle = !Theme.BLUE.isActive() && !Theme.GRAY.isActive() ? "x-toolbar-mark" : "noop";

  @UiField
  VBoxLayoutContainer buttonBox;
  @UiField
  CardLayoutContainer layout;
  @UiField
  ToggleButton spaced, multiSpaced, alignTop, alignMiddle, alignBottom, alignStretch, alignStretchMax, flexAllEven,
      flexRatio, flexStretch, packStart, packCenter, packEnd;

  private Widget widget;

  @Override
  public Widget asWidget() {
    if (widget == null) {
      widget = uiBinder.createAndBindUi(this);

      ToggleGroup toggleGroup = new ToggleGroup();
      toggleGroup.add(spaced);
      toggleGroup.add(multiSpaced);
      toggleGroup.add(alignTop);
      toggleGroup.add(alignMiddle);
      toggleGroup.add(alignBottom);
      toggleGroup.add(alignStretch);
      toggleGroup.add(alignStretchMax);
      toggleGroup.add(flexAllEven);
      toggleGroup.add(flexRatio);
      toggleGroup.add(flexStretch);
      toggleGroup.add(packStart);
      toggleGroup.add(packCenter);
      toggleGroup.add(packEnd);
    }

    return widget;
  }

  @Override
  public void onModuleLoad() {
    new ExampleContainer(this)
    .setMinHeight(MIN_HEIGHT)
    .setMinWidth(MIN_WIDTH)
    .doStandalone();
  }

  @UiHandler({ "spaced", "multiSpaced", "alignTop", "alignMiddle", "alignBottom", "alignStretch", "alignStretchMax",
      "flexAllEven", "flexRatio", "flexStretch", "packStart", "packCenter", "packEnd" })
  public void buttonClicked(SelectEvent event) {
    ToggleButton button = (ToggleButton) event.getSource();

    int index = buttonBox.getWidgetIndex(button);
    layout.setActiveWidget(layout.getWidget(index + 1));
  }

}
