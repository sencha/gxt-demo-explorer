package com.sencha.gxt.explorer.client.tabs;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.sencha.gxt.explorer.client.app.ui.ExampleContainer;
import com.sencha.gxt.explorer.client.model.Example.Detail;
import com.sencha.gxt.widget.core.client.PlainTabPanel;
import com.sencha.gxt.widget.core.client.PlainTabPanel.PlainTabPanelBottomAppearance;
import com.sencha.gxt.widget.core.client.TabPanel;
import com.sencha.gxt.widget.core.client.TabPanel.TabPanelBottomAppearance;

@Detail(
    name = "Bottom Advanced Tabs",
    icon = "advancedbottomtabs",
    category = "Tabs",
    minHeight = AdvancedBottomTabsExample.MIN_HEIGHT,
    minWidth = AdvancedBottomTabsExample.MIN_WIDTH
)
public class AdvancedBottomTabsExample extends AdvancedTabsExample implements EntryPoint {

  protected static final int MIN_HEIGHT = 435;
  protected static final int MIN_WIDTH = 435;

  @Override
  protected TabPanel getTabPanel() {
    return new TabPanel(GWT.<TabPanelBottomAppearance> create(TabPanelBottomAppearance.class));
  }

  @Override
  protected PlainTabPanel getPlainTabPanel() {
    return new PlainTabPanel(GWT.<PlainTabPanelBottomAppearance> create(PlainTabPanelBottomAppearance.class));
  }

  @Override
  public void onModuleLoad() {
    new ExampleContainer(this)
        .setMinHeight(MIN_HEIGHT)
        .setMinWidth(MIN_WIDTH)
        .doStandalone();
  }

}
