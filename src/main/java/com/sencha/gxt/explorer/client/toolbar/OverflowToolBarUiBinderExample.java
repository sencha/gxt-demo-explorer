package com.sencha.gxt.explorer.client.toolbar;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.explorer.client.app.ui.ExampleContainer;
import com.sencha.gxt.explorer.client.model.Example.Detail;
import com.sencha.gxt.widget.core.client.Window;
import com.sencha.gxt.widget.core.client.button.ButtonBar;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;

@Detail(
    name = "Overflow Tool Bar (UiBinder)",
    category = "Tool Bar & Menu",
    icon = "overflowtoolbaruibinder",
    files = "OverflowToolBarUiBinderExample.ui.xml",
    minWidth = OverflowToolBarUiBinderExample.MIN_WIDTH,
    preferredHeight = OverflowToolBarUiBinderExample.PREFERRED_HEIGHT
)
public class OverflowToolBarUiBinderExample implements IsWidget, EntryPoint {

  interface MyUiBinder extends UiBinder<Widget, OverflowToolBarUiBinderExample> {
  }

  protected static final int MIN_WIDTH = 120;
  protected static final int PREFERRED_HEIGHT = -1;

  private static MyUiBinder uiBinder = GWT.create(MyUiBinder.class);

  @UiField
  Window window;

  private ButtonBar buttonBar;
  private boolean showWindow;

  @Override
  public Widget asWidget() {
    if (buttonBar == null) {
      uiBinder.createAndBindUi(this);

      TextButton buttonOverflow = new TextButton("Overflow Tool Bar");
      buttonOverflow.addSelectHandler(new SelectHandler() {
        @Override
        public void onSelect(SelectEvent event) {
          window.show();
        }
      });

      buttonBar = new ButtonBar() {
        @Override
        public void setVisible(boolean visible) {
          super.setVisible(visible);
          if (visible == false) {
            showWindow = window.isVisible();
            window.hide();
          } else {
            if (showWindow) {
              window.show();
            }
          }
        }
      };
      buttonBar.add(buttonOverflow);
    }

    return buttonBar;
  }

  @Override
  public void onModuleLoad() {
    new ExampleContainer(this)
        .setMinWidth(MIN_WIDTH)
        .setPreferredHeight(PREFERRED_HEIGHT)
        .doStandalone();
  }

}
