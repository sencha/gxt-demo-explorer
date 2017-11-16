package com.sencha.gxt.explorer.client.tabs;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.examples.resources.client.TestData;
import com.sencha.gxt.explorer.client.app.ui.ExampleContainer;
import com.sencha.gxt.explorer.client.model.Example.Detail;

@Detail(
    name = "Basic Tabs (UiBinder)",
    category = "Tabs",
    icon = "basictabsuibinder",
    files = "BasicTabUiBinderExample.ui.xml",
    minHeight = BasicTabUiBinderExample.MIN_HEIGHT,
    minWidth = BasicTabUiBinderExample.MIN_WIDTH,
    classes = { TestData.class }
)
public class BasicTabUiBinderExample implements IsWidget, EntryPoint {

  interface MyUiBinder extends UiBinder<Widget, BasicTabUiBinderExample> {
  }

  protected static final int MIN_HEIGHT = 320;
  protected static final int MIN_WIDTH = 320;

  private static MyUiBinder uiBinder = GWT.create(MyUiBinder.class);

  @UiField(provided = true)
  String txt = TestData.DUMMY_TEXT_SHORT;

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
