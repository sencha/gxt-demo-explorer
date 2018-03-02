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
package com.sencha.gxt.explorer.client.tips;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.explorer.client.app.ui.ExampleContainer;
import com.sencha.gxt.explorer.client.model.Example.Detail;
import com.sencha.gxt.widget.core.client.container.FlowLayoutContainer;
import com.sencha.gxt.widget.core.client.tips.QuickTip;

@Detail(
  name = "Quick Tips",
  category = "Tips",
  icon = "tooltips",
  minWidth = QuickTipsExample.MIN_WIDTH,
  preferredHeight = QuickTipsExample.PREFERRED_HEIGHT)
public class QuickTipsExample implements IsWidget, EntryPoint {

  protected static final int MIN_WIDTH = 360;
  protected static final int PREFERRED_HEIGHT = -1;

  private FlowLayoutContainer container;

  @Override
  public Widget asWidget() {
    if (container == null) {
      HTML html = new HTML("<span qtitle='The tips title.' qtip='The quick tips message.' "
          + "qwidth='100px'>Hover over me to see the quick tip.</span>");

      container = new FlowLayoutContainer();
      container.add(html);

      QuickTip.of(container);
    }

    return container;
  }

  @Override
  public void onModuleLoad() {
    new ExampleContainer(this).setMinWidth(MIN_WIDTH).setPreferredHeight(PREFERRED_HEIGHT).doStandalone();
  }

}
