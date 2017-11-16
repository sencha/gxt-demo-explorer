package com.sencha.gxt.explorer.client.layout;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.explorer.client.app.ui.ExampleContainer;
import com.sencha.gxt.explorer.client.model.Example.Detail;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.CardLayoutContainer;
import com.sencha.gxt.widget.core.client.event.SelectEvent;

@Detail(
    name = "Card Layout (UiBinder)",
    category = "Layouts",
    icon = "cardlayoutuibinder",
    files = "CardLayoutUiBinderExample.ui.xml",
    minHeight = CardLayoutUiBinderExample.MIN_HEIGHT,
    minWidth = CardLayoutUiBinderExample.MIN_WIDTH
)
public class CardLayoutUiBinderExample implements IsWidget, EntryPoint {

  interface MyUiBinder extends UiBinder<Widget, CardLayoutUiBinderExample> {
  }

  protected static final int MIN_HEIGHT = 150;
  protected static final int MIN_WIDTH = 425;

  private static MyUiBinder uiBinder = GWT.create(MyUiBinder.class);

  @UiField
  CardLayoutContainer layout;
  @UiField
  ContentPanel panel;

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

  @UiHandler({"card1Button", "card2Button", "card3Button", "card4Button"})
  public void onButton1Click(SelectEvent event) {
    TextButton button = (TextButton) event.getSource();
    int index = panel.getButtonBar().getWidgetIndex(button);
    layout.setActiveWidget(layout.getWidget(index - 1));
  }

}
