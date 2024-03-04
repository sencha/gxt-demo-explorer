/**
 * Sencha GXT 1.0.0-SNAPSHOT - Sencha for GWT
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
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.core.client.Style.AnchorAlignment;
import com.sencha.gxt.explorer.client.app.ui.ExampleContainer;
import com.sencha.gxt.explorer.client.model.Example.Detail;
import com.sencha.gxt.widget.core.client.Window;
import com.sencha.gxt.widget.core.client.container.SimpleContainer;

import static com.sencha.gxt.core.client.Style.Anchor.CENTER;

@Detail(
    name = "Hello World (UiBinder)",
    category = "Windows",
    icon = "helloworlduibinder",
    files = "HelloWindowUiBinderExample.ui.xml",
    preferredWidth = HelloWindowUiBinderExample.PREFERRED_WIDTH,
    preferredHeight = HelloWindowUiBinderExample.PREFERRED_HEIGHT,
    preferredMargin = HelloWindowUiBinderExample.PREFERRED_MARGIN
)
public class HelloWindowUiBinderExample implements IsWidget, EntryPoint {

  interface MyUiBinder extends UiBinder<Widget, HelloWindowUiBinderExample> {
  }

  protected static final int PREFERRED_WIDTH = 1;
  protected static final int PREFERRED_HEIGHT = 1;
  // keep margin for shadow
  protected static final int PREFERRED_MARGIN = 2;

  private static MyUiBinder uiBinder = GWT.create(MyUiBinder.class);

  private SimpleContainer container;

  @UiField
  Window window;

  @Override
  public Widget asWidget() {
    if (container == null) {
      uiBinder.createAndBindUi(this);

      container = new SimpleContainer() {

        @Override
        public void setVisible(boolean visible) {
          super.setVisible(visible);
          if (visible) {
            window.show();
            Scheduler.get().scheduleDeferred(new Scheduler.ScheduledCommand() {
              @Override
              public void execute() {
                // since this is deferred, check that window is actually visible
                if (window.isVisible()) {
                  window.alignTo(container.getElement(), new AnchorAlignment(CENTER, CENTER), 0, 0);
                }
              }
            });
          } else {
            window.hide();
          }
        }
      };
      window.getDraggable().setContainer(container);
    }
    return container;
  }

  @Override
  public void onModuleLoad() {
    new ExampleContainer(this)
        .setPreferredWidth(PREFERRED_WIDTH)
        .setPreferredHeight(PREFERRED_HEIGHT)
        .setPreferredMargin(PREFERRED_MARGIN)
        .doStandalone();
  }

}