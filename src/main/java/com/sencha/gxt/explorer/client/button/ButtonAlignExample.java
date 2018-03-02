/**
 * Sencha GXT 1.0.0-SNAPSHOT - Sencha for GWT
 * Copyright (c) 2006-2018, Sencha Inc.
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
package com.sencha.gxt.explorer.client.button;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.explorer.client.app.ui.ExampleContainer;
import com.sencha.gxt.explorer.client.model.Example.Detail;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.BoxLayoutContainer.BoxLayoutData;
import com.sencha.gxt.widget.core.client.container.BoxLayoutContainer.BoxLayoutPack;
import com.sencha.gxt.widget.core.client.container.VBoxLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VBoxLayoutContainer.VBoxLayoutAlign;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.info.Info;

@Detail(
    name = "Button Aligning",
    category = "Button",
    icon = "buttonaligning",
    minHeight = ButtonAlignExample.MIN_HEIGHT,
    minWidth = ButtonAlignExample.MIN_WIDTH
)
public class ButtonAlignExample implements IsWidget, EntryPoint {

  protected static final int MIN_HEIGHT = 330;
  protected static final int MIN_WIDTH = 330;

  private VBoxLayoutContainer panel;

  public Widget asWidget() {
    if (panel == null) {
      SelectHandler selectHandler = new SelectHandler() {
        @Override
        public void onSelect(SelectEvent event) {
          Info.display("Click", ((TextButton) event.getSource()).getText() + " clicked");
        }
      };

      ContentPanel panelStart = new ContentPanel();
      // Align buttons to the start or left in ltr
      panelStart.setButtonAlign(BoxLayoutPack.START); // Left
      panelStart.setHeading("Button Aligning — " + BoxLayoutPack.START);
      panelStart.addButton(new TextButton("Button 1", selectHandler));
      panelStart.addButton(new TextButton("Button 2", selectHandler));
      panelStart.addButton(new TextButton("Button 3", selectHandler));

      ContentPanel panelCenter = new ContentPanel();
      // Align buttons to the center
      panelCenter.setButtonAlign(BoxLayoutPack.CENTER); // Center
      panelCenter.setHeading("Button Aligning — " + BoxLayoutPack.CENTER);
      panelCenter.addButton(new TextButton("Button 1", selectHandler));
      panelCenter.addButton(new TextButton("Button 2", selectHandler));
      panelCenter.addButton(new TextButton("Button 3", selectHandler));

      ContentPanel panelEnd = new ContentPanel();
      // Align buttons to the end or right in ltr
      panelEnd.setButtonAlign(BoxLayoutPack.END); // Right
      panelEnd.setHeading("Button Aligning — " + BoxLayoutPack.END);
      panelEnd.addButton(new TextButton("Button 1", selectHandler));
      panelEnd.addButton(new TextButton("Button 2", selectHandler));
      panelEnd.addButton(new TextButton("Button 3", selectHandler));

      BoxLayoutData flex = new BoxLayoutData(new Margins(10, 0, 10, 0));
      flex.setFlex(1);

      panel = new VBoxLayoutContainer(VBoxLayoutAlign.STRETCH);
      panel.add(panelStart, flex);
      panel.add(panelCenter, flex);
      panel.add(panelEnd, flex);
    }

    return panel;
  }

  @Override
  public void onModuleLoad() {
    new ExampleContainer(this)
        .setMinHeight(MIN_HEIGHT)
        .setMinWidth(MIN_WIDTH)
        .doStandalone();
  }

}
