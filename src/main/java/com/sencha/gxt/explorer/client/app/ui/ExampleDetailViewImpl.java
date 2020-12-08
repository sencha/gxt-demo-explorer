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
package com.sencha.gxt.explorer.client.app.ui;

import java.util.List;

import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.sencha.gxt.core.client.XTemplates;
import com.sencha.gxt.explorer.client.ExplorerApp;
import com.sencha.gxt.explorer.client.model.Example;
import com.sencha.gxt.explorer.client.model.ExampleModel;
import com.sencha.gxt.widget.core.client.TabItemConfig;
import com.sencha.gxt.widget.core.client.TabPanel;

public class ExampleDetailViewImpl implements ExampleDetailView {

  private TabPanel tabPanel;
  private Presenter presenter;

  public interface Renderer extends XTemplates {
    @XTemplate(source = "template.html")
    public SafeHtml renderItems(List<Example> items);
  }

  private ExampleModel exampleModel;

  @Inject
  public ExampleDetailViewImpl(ExampleModel model) {
    this.exampleModel = model;
    tabPanel = new TabPanel();
    tabPanel.setTabScroll(true);
    tabPanel.setCloseContextMenu(true);
    tabPanel.addSelectionHandler(new SelectionHandler<Widget>() {
      @Override
      public void onSelection(SelectionEvent<Widget> event) {
        Widget item = event.getSelectedItem();
        TabItemConfig config = tabPanel.getConfig(item);
        String name = config.getText();
        Example example = exampleModel.findExampleByName(name);

        // This is a little gross, but it ensures that adding the first tab
        // won't kick off a place change
        if (presenter != null) {
          presenter.selectExample(example);
        }
      }
    });
    Example overview = exampleModel.findExample(ExplorerApp.OVERVIEW.toLowerCase());
    tabPanel.add(new Page(overview), overview.getName());
  }

  @Override
  public Widget asWidget() {
    return tabPanel;
  }

  @Override
  public void setPresenter(Presenter presenter) {
    this.presenter = presenter;
  }

  @Override
  public void showExample(final Example example) {
    if (example != null) {
      Page page = findTabItem(example.getName());
      if (page == null) {

        // TODO consider assisted inject for this, as it gets no dependencies this way
        page = new Page(example);

        TabItemConfig config = new TabItemConfig(example.getName(), true);
        config.setClosable(!ExplorerApp.OVERVIEW.equals(example.getName()));
        tabPanel.add(page, config);
      }
      tabPanel.setActiveWidget(page);
    }

    track(example);
  }

  private Page findTabItem(String name) {
    for (int i = 0; i < tabPanel.getWidgetCount(); i++) {
      Page item = (Page) tabPanel.getWidget(i);
      TabItemConfig config = tabPanel.getConfig(item);
      if (config.getContent().asString().equals(name)) {
        return item;
      }
    }
    return null;
  }

  private void track(Example example) {
    String id = example.getId();
    String hash = "#" + id;
    String path = Window.Location.getPath() + hash;
    trackJsni(path);
  }

  /**
   * Fire a Google Analytics pageview call.
   *
   * Firing both the newer universal analytics and older analytics calls.
   * https://developers.google.com/analytics/devguides/collection/analyticsjs/pages
   */
  private native void trackJsni(String path) /*-{
    // Universal Analytics
    try {
      $wnd.ga('send', 'pageview', path);
    } catch (e) {
    }
  }-*/;

}
