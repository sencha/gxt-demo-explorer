package com.sencha.gxt.explorer.client.tabs;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.explorer.client.app.ui.ExampleContainer;
import com.sencha.gxt.explorer.client.model.Example.Detail;
import com.sencha.gxt.widget.core.client.PlainTabPanel;
import com.sencha.gxt.widget.core.client.TabItemConfig;
import com.sencha.gxt.widget.core.client.TabPanel;
import com.sencha.gxt.widget.core.client.button.ButtonBar;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.button.ToggleButton;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;

@Detail(
    name = "Advanced Tabs",
    icon = "advancedtabs",
    category = "Tabs",
    minHeight = AdvancedTabsExample.MIN_HEIGHT,
    minWidth = AdvancedTabsExample.MIN_WIDTH
)
public class AdvancedTabsExample implements IsWidget, EntryPoint {

  private static final int DEFAULT_TAB_COUNT = 7;

  protected static final int MIN_HEIGHT = 435;
  protected static final int MIN_WIDTH = 435;

  private VerticalLayoutContainer container;

  @Override
  public Widget asWidget() {
    if (container == null) {
      container = new VerticalLayoutContainer();

      final Map<TabPanel, Integer> tabPanels = new HashMap<TabPanel, Integer>(2); // map tab panels to index count
      tabPanels.put(getTabPanel(), 0);
      tabPanels.put(getPlainTabPanel(), 0);

      for (Iterator<Entry<TabPanel, Integer>> it = tabPanels.entrySet().iterator(); it.hasNext();) {
        final TabPanel tabPanel = it.next().getKey();

        TextButton add = new TextButton("Add Tab");
        add.addSelectHandler(new SelectHandler() {
          @Override
          public void onSelect(SelectEvent event) {
            int index = tabPanels.get(tabPanel) + 1;
            tabPanels.put(tabPanel, index);
            addTab(tabPanel, index);
            tabPanel.setActiveWidget(tabPanel.getWidget(tabPanel.getWidgetCount() - 1));
          }
        });

        ToggleButton toggleContextMenu = new ToggleButton("Enable Tab Context Menu");
        toggleContextMenu.addValueChangeHandler(new ValueChangeHandler<Boolean>() {
          @Override
          public void onValueChange(ValueChangeEvent<Boolean> event) {
            tabPanel.setCloseContextMenu(event.getValue());
          }
        });
        toggleContextMenu.setValue(true);

        final ToggleButton toggleCloseBtn = new ToggleButton("Enable Selected Tab Close Button");
        toggleCloseBtn.addValueChangeHandler(new ValueChangeHandler<Boolean>() {
          @Override
          public void onValueChange(ValueChangeEvent<Boolean> event) {
            Widget widget = tabPanel.getActiveWidget();
            if (widget != null) {
              TabItemConfig tabConfig = tabPanel.getConfig(widget);
              tabConfig.setClosable(event.getValue());
              tabPanel.update(widget, tabConfig);
            }
          }
        });

        ButtonBar bb = new ButtonBar();
        bb.add(add);
        bb.add(toggleContextMenu);
        bb.add(toggleCloseBtn);

        tabPanel.setAnimScroll(true);
        tabPanel.setTabScroll(true);
        tabPanel.setCloseContextMenu(true);

        for (int j = 0; j < DEFAULT_TAB_COUNT; j++) {
          int index = tabPanels.get(tabPanel) + 1;
          tabPanels.put(tabPanel, index);
          addTab(tabPanel, index);
        }

        tabPanel.setActiveWidget(tabPanel.getWidget(DEFAULT_TAB_COUNT - 1));

        tabPanel.addSelectionHandler(new SelectionHandler<Widget>() {
          @Override
          public void onSelection(SelectionEvent<Widget> event) {
            toggleCloseBtn.setValue(tabPanel.getConfig(event.getSelectedItem()).isClosable());
          }
        });

        container.add(bb, new VerticalLayoutData(1, -1, new Margins(0, 0, 20, 0)));
        container.add(tabPanel, new VerticalLayoutData(1, 0.5));
        if (it.hasNext()) {
          container.add(new HTML(), new VerticalLayoutData(1, 20));
        }
      }
    }
    return container;
  }

  protected TabPanel getTabPanel() {
    return new TabPanel();
  }

  protected PlainTabPanel getPlainTabPanel() {
    return new PlainTabPanel();
  }

  private void addTab(TabPanel tabPanel, int index) {
    Label item = new Label("Tab Body " + index);
    item.addStyleName("pad-text");
    tabPanel.add(item, new TabItemConfig("New Tab " + index, index != 1));
  }

  @Override
  public void onModuleLoad() {
    new ExampleContainer(this)
        .setMinHeight(MIN_HEIGHT)
        .setMinWidth(MIN_WIDTH)
        .doStandalone();
  }

}
