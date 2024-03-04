/**
 * Sencha GXT 1.1.0-SNAPSHOT - Sencha for GWT
 * Copyright (c) 2006-2021, Sencha Inc.
 *
 * licensing@sencha.com
 * http://www.sencha.com/products/gxt/license/
 *
 * ================================================================================
 * Commercial License
 * ================================================================================
 * This version of Sencha GXT is licensed commercially and is the appropriate
 * option for the vast majority of use cases.
 *
 * Please see the Sencha GXT Licensing page at:
 * http://www.sencha.com/products/gxt/license/
 *
 * For clarification or additional options, please contact:
 * licensing@sencha.com
 * ================================================================================
 *
 *
 *
 *
 *
 *
 *
 *
 * ================================================================================
 * Disclaimer
 * ================================================================================
 * THIS SOFTWARE IS DISTRIBUTED "AS-IS" WITHOUT ANY WARRANTIES, CONDITIONS AND
 * REPRESENTATIONS WHETHER EXPRESS OR IMPLIED, INCLUDING WITHOUT LIMITATION THE
 * IMPLIED WARRANTIES AND CONDITIONS OF MERCHANTABILITY, MERCHANTABLE QUALITY,
 * FITNESS FOR A PARTICULAR PURPOSE, DURABILITY, NON-INFRINGEMENT, PERFORMANCE AND
 * THOSE ARISING BY STATUTE OR FROM CUSTOM OR USAGE OF TRADE OR COURSE OF DEALING.
 * ================================================================================
 */
package com.sencha.gxt.explorer.client.window;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.explorer.client.model.Example.Detail;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.Window;
import com.sencha.gxt.widget.core.client.WindowManager;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.SimpleContainer;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.toolbar.LabelToolItem;

@Detail(name = "Window Manager", category = "Windows", icon = "accordionwindow")
public class WindowManagerExample implements IsWidget, EntryPoint {

  private SimpleContainer container;
  private Window window1;
  private Window window2;
  private Window window3;

  @Override
  public Widget asWidget() {
    if (container == null) {
      // Create 3 windows to manage
      window1 = createWindow("1");
      window2 = createWindow("2");
      window3 = createWindow("3");

      container = new SimpleContainer() {
        @Override
        public void setVisible(boolean visible) {
          super.setVisible(visible);
          // when the container is shown, show all visible windows
          if (!visible) {
            window1.hide();
            window2.hide();
            window3.hide();
          } else {
            window1.show();
            window2.show();
            window3.show();
          }
        }
      };
    }

    window1.show();
    window2.show();
    window3.show();

    return container;
  }

  public Window createWindow(String name) {
    TextButton sendToBack = new TextButton("Send Window to Back");

    ContentPanel cp = new ContentPanel();
    cp.setHeaderVisible(false);
    cp.add(new LabelToolItem("Window " + name + ". Control the window manager with the buttons."));
    cp.addButton(sendToBack);

    final Window window = new Window();
    window.setClosable(false);
    window.setHeading("Window " + name);
    window.setPixelSize(400, 400);
    window.add(cp);

    sendToBack.addSelectHandler(new SelectHandler() {
      @Override
      public void onSelect(SelectEvent event) {
        WindowManager.get().sendToBack(window);
      }
    });

    return window;
  }

  @Override
  public void onModuleLoad() {
  }

}
