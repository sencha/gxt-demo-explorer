package com.sencha.gxt.explorer.client.layout;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.core.client.resources.ThemeStyles;
import com.sencha.gxt.examples.resources.client.Utils;
import com.sencha.gxt.examples.resources.client.Utils.Theme;
import com.sencha.gxt.explorer.client.app.ui.ExampleContainer;
import com.sencha.gxt.explorer.client.model.Example.Detail;

@Detail(
    name = "Vertical Layout (UiBinder)",
    category = "Layouts",
    icon = "verticallayoutuibinder",
    files = "VerticalLayoutUiBinderExample.ui.xml",
    minHeight = VerticalLayoutUiBinderExample.MIN_HEIGHT,
    minWidth = VerticalLayoutUiBinderExample.MIN_WIDTH,
    classes = { Utils.class }
)
public class VerticalLayoutUiBinderExample implements IsWidget, EntryPoint {

  protected static final int MIN_HEIGHT = 320;
  protected static final int MIN_WIDTH = 150;

  private static MyUiBinder uiBinder = GWT.create(MyUiBinder.class);

  interface MyUiBinder extends UiBinder<Widget, VerticalLayoutUiBinderExample> {
  }

  @UiField(provided = true)
  String borderStyle =  Theme.BLUE.isActive() || Theme.GRAY.isActive() ? ThemeStyles.get().style().border() : "noop";

  private Widget widget;

  @Override
  public Widget asWidget() {
    if (widget == null) {
      widget = uiBinder.createAndBindUi(this);
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

}
