package com.sencha.gxt.explorer.client.toolbar;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.examples.resources.client.images.ExampleImages;
import com.sencha.gxt.explorer.client.app.ui.ExampleContainer;
import com.sencha.gxt.explorer.client.model.Example.Detail;
import com.sencha.gxt.widget.core.client.Window;
import com.sencha.gxt.widget.core.client.button.ButtonBar;
import com.sencha.gxt.widget.core.client.button.ButtonGroup;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.BoxLayoutContainer.BoxLayoutPack;
import com.sencha.gxt.widget.core.client.container.NorthSouthContainer;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.toolbar.FillToolItem;
import com.sencha.gxt.widget.core.client.toolbar.ToolBar;

@Detail(
    name = "Overflow Tool Bar",
    category = "Tool Bar & Menu",
    icon = "overflowtoolbar",
    minWidth = OverflowToolBarExample.MIN_WIDTH,
    preferredHeight = OverflowToolBarExample.PREFERRED_HEIGHT
)
public class OverflowToolBarExample implements IsWidget, EntryPoint {

  protected static final int MIN_WIDTH = 120;
  protected static final int PREFERRED_HEIGHT = -1;

  private ButtonBar buttonBar;
  private boolean showWindow;

  @Override
  public Widget asWidget() {
    if (buttonBar == null) {
      TextButton button1 = new TextButton("Cool");
      TextButton button2 = new TextButton("Copy");
      TextButton button3 = new TextButton("Add");
      TextButton button4 = new TextButton("Delete");
      TextButton button5 = new TextButton("Cool");
      TextButton button6 = new TextButton("Copy");
      TextButton button7 = new TextButton("Add");
      TextButton button8 = new TextButton("Delete");

      button1.setIcon(ExampleImages.INSTANCE.add16());
      button2.setIcon(ExampleImages.INSTANCE.add16());
      button3.setIcon(ExampleImages.INSTANCE.user_add());
      button4.setIcon(ExampleImages.INSTANCE.user_delete());
      button5.setIcon(ExampleImages.INSTANCE.add16());
      button6.setIcon(ExampleImages.INSTANCE.add16());
      button7.setIcon(ExampleImages.INSTANCE.user_add());
      button8.setIcon(ExampleImages.INSTANCE.user_delete());

      FlexTable table1 = new FlexTable();
      table1.setWidget(0, 0, button1);
      table1.setWidget(0, 1, button2);
      table1.setWidget(1, 0, button3);
      table1.setWidget(1, 1, button4);

      FlexTable table2 = new FlexTable();
      table2.setWidget(0, 0, button5);
      table2.setWidget(0, 1, button6);
      table2.setWidget(1, 0, button7);
      table2.setWidget(1, 1, button8);

      ButtonGroup group1 = new ButtonGroup();
      group1.setHeading("Clipboard");
      group1.add(table1);

      ButtonGroup group2 = new ButtonGroup();
      group2.setHeading("Other Bogus Actions");
      group2.add(table2);

      ToolBar toolBar = new ToolBar();
      toolBar.add(group1);
      toolBar.add(new FillToolItem());
      toolBar.add(group2);

      NorthSouthContainer northSouthContainer = new NorthSouthContainer();
      northSouthContainer.setNorthWidget(toolBar);

      final Window window = new Window();
      window.setHeading("Overflow Tool Bar");
      window.setWidth(250);
      window.setMinWidth(50);
      window.add(northSouthContainer);
      window.setButtonAlign(BoxLayoutPack.CENTER);
      window.addButton(new TextButton("Save"));
      window.addButton(new TextButton("Cancel"));
      window.addButton(new TextButton("Close"));
      window.addButton(new TextButton("Highlight"));
      window.addButton(new TextButton("Shutdown"));

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
