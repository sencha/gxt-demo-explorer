package com.sencha.gxt.explorer.client.button;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.explorer.client.app.ui.ExampleContainer;
import com.sencha.gxt.explorer.client.model.Example.Detail;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.info.Info;

@Detail(
    name = "Button Aligning (UiBinder)",
    category = "Button",
    icon = "buttonaligninguibinder",
    files = "ButtonAlignUiBinderExample.ui.xml",
    minHeight = ButtonAlignUiBinderExample.MIN_HEIGHT,
    minWidth = ButtonAlignUiBinderExample.MIN_WIDTH
)
public class ButtonAlignUiBinderExample implements IsWidget, EntryPoint {

  interface MyUiBinder extends UiBinder<Widget, ButtonAlignUiBinderExample> {
  }

  protected static final int MIN_HEIGHT = 330;
  protected static final int MIN_WIDTH = 330;

  private static MyUiBinder uiBinder = GWT.create(MyUiBinder.class);

  private Widget widget;

  public Widget asWidget() {
    if (widget == null) {
      widget = uiBinder.createAndBindUi(this);
      widget = uiBinder.createAndBindUi(this);
    }

    return widget;
  }

  @UiHandler({"button1s", "button2s", "button3s", "button1c", "button2c", "button3c", "button1e", "button2e", "button3e"})
  public void onButtonClick(SelectEvent event) {
    Info.display("Click", ((TextButton) event.getSource()).getText() + " clicked");
  }

  @Override
  public void onModuleLoad() {
    new ExampleContainer(this)
        .setMinHeight(MIN_HEIGHT)
        .setMinWidth(MIN_WIDTH)
        .doStandalone();
  }

}
