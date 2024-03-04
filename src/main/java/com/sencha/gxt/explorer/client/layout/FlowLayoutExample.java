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
package com.sencha.gxt.explorer.client.layout;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.InlineHTML;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.examples.resources.client.Utils;
import com.sencha.gxt.explorer.client.app.ui.ExampleContainer;
import com.sencha.gxt.explorer.client.model.Example.Detail;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.container.FlowLayoutContainer;
import com.sencha.gxt.widget.core.client.container.MarginData;

@Detail(
  name = "Flow Layout",
  category = "Layouts",
  icon = "verticallayout",
  minHeight = FlowLayoutExample.MIN_HEIGHT,
  minWidth = FlowLayoutExample.MIN_WIDTH,
  classes = { Utils.class })
public class FlowLayoutExample implements IsWidget, EntryPoint {

  protected static final int MIN_HEIGHT = 320;
  protected static final int MIN_WIDTH = 150;

  private ContentPanel panel;

  @Override
  public Widget asWidget() {
    if (panel == null) {
      FlowLayoutContainer flc = new FlowLayoutContainer();
      flc.add(createRow("Stack 1"), new MarginData(10, 10, 10, 10));
      flc.add(createRow("Stack 2"), new MarginData(10, 10, 10, 10));
      flc.add(createRow("Stack 3"), new MarginData(10, 10, 10, 10));

      flc.add(createColumn("Inline 1"), new MarginData(10, 10, 10, 10));
      flc.add(createColumn("Inline 2"), new MarginData(10, 10, 10, 10));
      flc.add(createColumn("Inline 3"), new MarginData(10, 10, 10, 10));
      
      panel = new ContentPanel();
      panel.setHeading("Flow Layout");
      panel.add(flc);
    }

    return panel;
  }

  private Label createRow(String text) {
    HTML label = new HTML(text);
    label.getElement().getStyle().setProperty("whiteSpace", "nowrap");
    label.addStyleName("pad-text gray-bg");
    return label;
  }
  
  private Label createColumn(String text) {
    InlineHTML label = new InlineHTML(text);
    label.getElement().getStyle().setProperty("whiteSpace", "nowrap");
    label.addStyleName("pad-text gray-bg");
    return label;
  }

  @Override
  public void onModuleLoad() {
    new ExampleContainer(this).setMinHeight(MIN_HEIGHT).setMinWidth(MIN_WIDTH).doStandalone();
  }

}
