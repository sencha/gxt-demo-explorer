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
package com.sencha.gxt.explorer.client.dnd;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor.Path;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.core.client.Style.SelectionMode;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;
import com.sencha.gxt.data.shared.SortDir;
import com.sencha.gxt.data.shared.Store.StoreSortInfo;
import com.sencha.gxt.dnd.core.client.DND.Feedback;
import com.sencha.gxt.dnd.core.client.ListViewDragSource;
import com.sencha.gxt.dnd.core.client.ListViewDropTarget;
import com.sencha.gxt.examples.resources.client.Utils;
import com.sencha.gxt.examples.resources.client.Utils.Theme;
import com.sencha.gxt.examples.resources.client.model.ExampleData;
import com.sencha.gxt.examples.resources.client.model.Stock;
import com.sencha.gxt.examples.resources.client.model.StockProxy;
import com.sencha.gxt.explorer.client.app.ui.ExampleContainer;
import com.sencha.gxt.explorer.client.model.Example.Detail;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.ListView;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer.BorderLayoutData;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;

@Detail(
    name = "List to List",
    icon = "listtolist",
    category = "Drag & Drop",
    classes = {StockProxy.class, Utils.class},
    minHeight = ListViewDndExample.MIN_HEIGHT,
    minWidth = ListViewDndExample.MIN_WIDTH
)
public class ListViewDndExample implements IsWidget, EntryPoint {

  interface StockProperties extends PropertyAccess<Stock> {
    @Path("symbol")
    ModelKeyProvider<StockProxy> key();

    @Path("name")
    ValueProvider<StockProxy, String> nameProp();
  }

  protected static final int MIN_HEIGHT = 450;
  protected static final int MIN_WIDTH = 450;

  private VerticalLayoutContainer panel;

  @Override
  public Widget asWidget() {
    if (panel == null) {
      panel = new VerticalLayoutContainer();
      panel.add(createListView1And2(), new VerticalLayoutData(1, 0.5, new Margins(0, 0, 10, 0)));
      panel.add(createListView3And4(), new VerticalLayoutData(1, 0.5, new Margins(10, 0, 0, 0)));
    }

    return panel;
  }

  private ContentPanel createListView1And2() {
    StockProperties properties = GWT.create(StockProperties.class);

    ListStore<StockProxy> store = new ListStore<StockProxy>(properties.key());
    store.addSortInfo(new StoreSortInfo<StockProxy>(properties.nameProp(), SortDir.ASC));
    store.addAll(ExampleData.getStocks());

    ListStore<StockProxy> store2 = new ListStore<StockProxy>(properties.key());
    store2.addSortInfo(new StoreSortInfo<StockProxy>(properties.nameProp(), SortDir.ASC));

    ListView<StockProxy, String> listView1 = new ListView<StockProxy, String>(store, properties.nameProp());
    listView1.setBorders(Theme.BLUE.isActive() || Theme.GRAY.isActive() ? true : false);

    ListView<StockProxy, String> listView2 = new ListView<StockProxy, String>(store2, properties.nameProp());
    listView2.setBorders(Theme.BLUE.isActive() || Theme.GRAY.isActive() ? true : false);
    listView2.getSelectionModel().setSelectionMode(SelectionMode.MULTI);

    new ListViewDragSource<StockProxy>(listView1).setGroup("top");
    new ListViewDragSource<StockProxy>(listView2).setGroup("top");

    new ListViewDropTarget<StockProxy>(listView1).setGroup("top");
    new ListViewDropTarget<StockProxy>(listView2).setGroup("top");

    BorderLayoutData westData = new BorderLayoutData(0.5);
    westData.setMargins(Theme.BLUE.isActive() || Theme.GRAY.isActive() ? new Margins(0) : new Margins(0, 2, 0, 0));

    BorderLayoutData centerData = new BorderLayoutData();
    centerData.setMargins(Theme.BLUE.isActive() || Theme.GRAY.isActive() ? new Margins(0) : new Margins(0, 0, 0, 2));

    BorderLayoutContainer borderLayoutContainer = new BorderLayoutContainer();
    borderLayoutContainer.setWestWidget(listView1, westData);
    borderLayoutContainer.setCenterWidget(listView2, centerData);

    ContentPanel panel = new ContentPanel();
    panel.setHeading("List to List — Insert at Sort Position");
    panel.add(borderLayoutContainer);

    return panel;
  }

  private ContentPanel createListView3And4() {
    StockProperties properties = GWT.create(StockProperties.class);

    ListStore<StockProxy> store3 = new ListStore<StockProxy>(properties.key());
    store3.addAll(ExampleData.getStocks());

    ListView<StockProxy, String> listView3 = new ListView<StockProxy, String>(store3, properties.nameProp());
    listView3.setBorders(Theme.BLUE.isActive() || Theme.GRAY.isActive() ? true : false);

    ListStore<StockProxy> store4 = new ListStore<StockProxy>(properties.key());

    ListView<StockProxy, String> listView4 = new ListView<StockProxy, String>(store4, properties.nameProp());
    listView4.setBorders(Theme.BLUE.isActive() || Theme.GRAY.isActive() ? true : false);

    new ListViewDragSource<StockProxy>(listView3).setGroup("bottom");
    new ListViewDragSource<StockProxy>(listView4).setGroup("bottom");

    ListViewDropTarget<StockProxy> target1 = new ListViewDropTarget<StockProxy>(listView3);
    target1.setFeedback(Feedback.INSERT);
    target1.setGroup("bottom");

    ListViewDropTarget<StockProxy> target2 = new ListViewDropTarget<StockProxy>(listView4);
    target2.setFeedback(Feedback.INSERT);
    target2.setGroup("bottom");

    BorderLayoutData westData = new BorderLayoutData(0.5);
    westData.setMargins(Theme.BLUE.isActive() || Theme.GRAY.isActive() ? new Margins(0) : new Margins(0, 2, 0, 0));

    BorderLayoutData centerData = new BorderLayoutData();
    centerData.setMargins(Theme.BLUE.isActive() || Theme.GRAY.isActive() ? new Margins(0) : new Margins(0, 0, 0, 2));

    BorderLayoutContainer borderLayoutContainer = new BorderLayoutContainer();
    borderLayoutContainer.setWestWidget(listView3, westData);
    borderLayoutContainer.setCenterWidget(listView4, centerData);

    ContentPanel panel = new ContentPanel();
    panel.setHeading("List to List — Insert at Drop Position");
    panel.add(borderLayoutContainer);

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
