package com.sencha.gxt.explorer.client.forms;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.explorer.client.app.ui.ExampleContainer;
import com.sencha.gxt.explorer.client.model.Example.Detail;
import com.sencha.gxt.widget.core.client.Slider;
import com.sencha.gxt.widget.core.client.event.SelectEvent;

@Detail(
    name = "Slider (UiBinder)",
    category = "Forms",
    icon = "slideruibinder",
    files = "SliderUiBinderExample.ui.xml",
    preferredHeight = SliderUiBinderExample.PREFERRED_HEIGHT,
    preferredWidth = SliderUiBinderExample.PREFERRED_WIDTH
)
public class SliderUiBinderExample implements IsWidget, EntryPoint {

  interface MyUiBinder extends UiBinder<Widget, SliderUiBinderExample> {
  }

  protected static final int PREFERRED_HEIGHT = -1;
  protected static final int PREFERRED_WIDTH = 220;

  private static MyUiBinder uiBinder = GWT.create(MyUiBinder.class);

  @UiField
  Slider slider1;
  @UiField(provided = true)
  Slider slider2 = new Slider(true);

  private Widget widget;

  @Override
  public Widget asWidget() {
    if (widget == null) {
      widget = uiBinder.createAndBindUi(this);
    }

    return widget;
  }

  @UiHandler("slider1Button")
  public void slider1ButtonClicked(SelectEvent event) {
    slider1.setValue(40);
  }

  @UiHandler("slider2Button")
  public void slider2ButtonClicked(SelectEvent event) {
    slider2.setValue(20);
  }

  @Override
  public void onModuleLoad() {
    new ExampleContainer(this)
        .setPreferredHeight(PREFERRED_HEIGHT)
        .setPreferredWidth(PREFERRED_WIDTH)
        .doStandalone();
  }

}
