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
import com.google.gwt.core.client.GWT;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.core.client.XTemplates;
import com.sencha.gxt.explorer.client.app.ui.ExampleContainer;
import com.sencha.gxt.explorer.client.model.Example.Detail;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.AbstractHtmlLayoutContainer.HtmlData;
import com.sencha.gxt.widget.core.client.container.HtmlLayoutContainer;
import com.sencha.gxt.widget.core.client.container.MarginData;

@Detail(
    name = "Html Layout",
    category = "Layouts",
    icon = "htmllayoutcontainer",
    minHeight = HtmlLayoutContainerExample.MIN_HEIGHT,
    minWidth = HtmlLayoutContainerExample.MIN_WIDTH
)
public class HtmlLayoutContainerExample implements IsWidget, EntryPoint {

  public interface HtmlLayoutContainerTemplate extends XTemplates {
    @XTemplate("<table width=\"100%\" height=\"100%\"><tbody><tr><td height=\"100%\" class=\"cell1\" /><td>Some other cell</td><td class=\"cell2\" /></tr></tbody></table>")
    SafeHtml getTemplate();
  }

  protected static final int MIN_HEIGHT = 150;
  protected static final int MIN_WIDTH = 340;

  private ContentPanel widget;

  @Override
  public Widget asWidget() {
    if (widget == null) {
      TextButton button1 = new TextButton("Button Left Column");
      TextButton button2 = new TextButton("Button Right Column");

      HtmlLayoutContainerTemplate templates = GWT.create(HtmlLayoutContainerTemplate.class);

      HtmlLayoutContainer htmlLayoutContainer = new HtmlLayoutContainer(templates.getTemplate());
      htmlLayoutContainer.add(button1, new HtmlData(".cell1"));
      htmlLayoutContainer.add(button2, new HtmlData(".cell2"));
      
      widget = new ContentPanel();
      widget.setHeading("Html Layout");
      widget.add(htmlLayoutContainer, new MarginData(20));
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

}
