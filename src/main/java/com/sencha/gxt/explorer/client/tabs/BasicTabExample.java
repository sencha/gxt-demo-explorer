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
package com.sencha.gxt.explorer.client.tabs;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.examples.resources.client.Resources;
import com.sencha.gxt.examples.resources.client.TestData;
import com.sencha.gxt.explorer.client.app.ui.ExampleContainer;
import com.sencha.gxt.explorer.client.model.Example.Detail;
import com.sencha.gxt.widget.core.client.PlainTabPanel;
import com.sencha.gxt.widget.core.client.TabItemConfig;
import com.sencha.gxt.widget.core.client.TabPanel;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;

@Detail(
    name = "Basic Tabs",
    icon = "basictabs",
    category = "Tabs",
    minHeight = BasicTabExample.MIN_HEIGHT,
    minWidth = BasicTabExample.MIN_WIDTH,
    classes = { TestData.class }
)
public class BasicTabExample implements IsWidget, EntryPoint {

  protected static final int MIN_HEIGHT = 320;
  protected static final int MIN_WIDTH = 320;

  private VerticalLayoutContainer widget;

  @Override
  public Widget asWidget() {
    if (widget == null) {
      String text = TestData.DUMMY_TEXT_SHORT;

      HTML shortText = new HTML(text);
      shortText.addStyleName("pad-text");

      HTML longText = new HTML(text + "<br><br>" + text);
      longText.addStyleName("pad-text");

      Label normalLabel = new Label("Just a plain old tab");
      normalLabel.addStyleName("pad-text");

      Label iconLabel = new Label("Just a plain old tab with an icon");
      iconLabel.addStyleName("pad-text");
      
      Label disabledLabel = new Label("This tab should be disabled");
      disabledLabel.addStyleName("pad-text");

      TabItemConfig iconTab = new TabItemConfig("Icon Tab");
      iconTab.setIcon(Resources.IMAGES.table());

      TabItemConfig disabledTab = new TabItemConfig("Disabled");
      disabledTab.setEnabled(false);

      TabPanel folderTabPanel = new TabPanel();
      folderTabPanel.setWidth(450);
      folderTabPanel.add(shortText, "Short Text");
      folderTabPanel.add(longText, "Long Text");

      final PlainTabPanel tabPanel = new PlainTabPanel();
      tabPanel.setPixelSize(450, 250);
      tabPanel.add(normalLabel, "Normal");
      tabPanel.add(iconLabel, iconTab);
      tabPanel.add(disabledLabel, disabledTab);

      widget = new VerticalLayoutContainer();
      widget.add(folderTabPanel, new VerticalLayoutData(1, 0.5, new Margins(0, 0, 10, 0)));
      widget.add(tabPanel, new VerticalLayoutData(1, 0.5, new Margins(10, 0, 0, 0)));
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
