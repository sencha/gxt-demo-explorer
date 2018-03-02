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
package com.sencha.gxt.explorer.client.dnd;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor.Path;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;
import com.sencha.gxt.data.shared.SortDir;
import com.sencha.gxt.data.shared.Store.StoreSortInfo;
import com.sencha.gxt.dnd.core.client.DND.Feedback;
import com.sencha.gxt.dnd.core.client.GridDragSource;
import com.sencha.gxt.dnd.core.client.GridDropTarget;
import com.sencha.gxt.examples.resources.client.Utils;
import com.sencha.gxt.examples.resources.client.Utils.Theme;
import com.sencha.gxt.examples.resources.client.model.ExampleData;
import com.sencha.gxt.examples.resources.client.model.Stock;
import com.sencha.gxt.examples.resources.client.model.StockProxy;
import com.sencha.gxt.explorer.client.app.ui.ExampleContainer;
import com.sencha.gxt.explorer.client.model.Example.Detail;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer.BorderLayoutData;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;
import com.sencha.gxt.widget.core.client.grid.ColumnConfig;
import com.sencha.gxt.widget.core.client.grid.ColumnModel;
import com.sencha.gxt.widget.core.client.grid.Grid;

@Detail(
    name = "Grid to Grid",
    category = "Drag & Drop",
    icon = "gridtogrid",
    classes = {StockProxy.class, Utils.class },
    minHeight = GridDndExample.MIN_HEIGHT,
    minWidth = GridDndExample.MIN_WIDTH
)
public class GridDndExample implements IsWidget, EntryPoint {

  interface StockProperties extends PropertyAccess<Stock> {
    @Path("symbol")
    ModelKeyProvider<StockProxy> key();

    @Path("name")
    ValueProvider<StockProxy, String> nameProp();
  }

  protected static final int MIN_HEIGHT = 450;
  protected static final int MIN_WIDTH = 450;

  private StockProperties properties = GWT.create(StockProperties.class);

  private VerticalLayoutContainer panel;

  @Override
  public Widget asWidget() {
    if (panel == null) {
      panel = new VerticalLayoutContainer();
      panel.add(createGrids1And2(), new VerticalLayoutData(1, 0.5, new Margins(0, 0, 10, 0)));
      panel.add(createGrids3And4(), new VerticalLayoutData(1, 0.5, new Margins(10, 0, 0, 0)));
    }

    return panel;
  }

  private ContentPanel createGrids1And2() {
    ListStore<StockProxy> store1 = new ListStore<StockProxy>(properties.key());
    store1.addSortInfo(new StoreSortInfo<StockProxy>(properties.nameProp(), SortDir.ASC));
    store1.addAll(ExampleData.getStocks());

    ListStore<StockProxy> store2 = new ListStore<StockProxy>(properties.key());
    store2.addSortInfo(new StoreSortInfo<StockProxy>(properties.nameProp(), SortDir.ASC));

    Grid<StockProxy> grid1 = new Grid<StockProxy>(store1, createColumnList(properties, true));
    grid1.setBorders(Theme.BLUE.isActive() || Theme.GRAY.isActive() ? true : false);
    grid1.getView().setForceFit(true);

    Grid<StockProxy> grid2 = new Grid<StockProxy>(store2, createColumnList(properties, true));
    grid2.setBorders(Theme.BLUE.isActive() || Theme.GRAY.isActive() ? true : false);
    grid2.getView().setForceFit(true);

    new GridDragSource<StockProxy>(grid1).setGroup("top");
    new GridDragSource<StockProxy>(grid2).setGroup("top");

    new GridDropTarget<StockProxy>(grid1).setGroup("top");
    new GridDropTarget<StockProxy>(grid2).setGroup("top");

    BorderLayoutData westData = new BorderLayoutData(0.5);
    westData.setMargins(Theme.BLUE.isActive() || Theme.GRAY.isActive() ? new Margins(0) : new Margins(0, 2, 0, 0));

    BorderLayoutData centerData = new BorderLayoutData();
    centerData.setMargins(Theme.BLUE.isActive() || Theme.GRAY.isActive() ? new Margins(0) : new Margins(0, 0, 0, 2));

    BorderLayoutContainer borderLayoutContainer = new BorderLayoutContainer();
    borderLayoutContainer.setWestWidget(grid1, westData);
    borderLayoutContainer.setCenterWidget(grid2, centerData);

    ContentPanel panel = new ContentPanel();
    panel.setHeading("Grid to Grid — Insert at Sort Position");
    panel.add(borderLayoutContainer);

    return panel;
  }

  private ContentPanel createGrids3And4() {
    ListStore<StockProxy> store3 = new ListStore<StockProxy>(properties.key());
    store3.addAll(ExampleData.getStocks());

    ListStore<StockProxy> store4 = new ListStore<StockProxy>(properties.key());

    Grid<StockProxy> grid3 = new Grid<StockProxy>(store3, createColumnList(properties, false));
    grid3.setBorders(Theme.BLUE.isActive() || Theme.GRAY.isActive() ? true : false);
    grid3.getView().setForceFit(true);

    Grid<StockProxy> grid4 = new Grid<StockProxy>(store4, createColumnList(properties, false));
    grid4.setBorders(Theme.BLUE.isActive() || Theme.GRAY.isActive() ? true : false);
    grid4.getView().setForceFit(true);

    new GridDragSource<StockProxy>(grid3).setGroup("bottom");
    new GridDragSource<StockProxy>(grid4).setGroup("bottom");

    GridDropTarget<StockProxy> target1 = new GridDropTarget<StockProxy>(grid3);
    target1.setFeedback(Feedback.INSERT);
    target1.setGroup("bottom");

    GridDropTarget<StockProxy> target2 = new GridDropTarget<StockProxy>(grid4);
    target2.setFeedback(Feedback.INSERT);
    target2.setGroup("bottom");

    BorderLayoutData westData = new BorderLayoutData(0.5);
    westData.setMargins(Theme.BLUE.isActive() || Theme.GRAY.isActive() ? new Margins(0) : new Margins(0, 2, 0, 0));

    BorderLayoutData centerData = new BorderLayoutData();
    centerData.setMargins(Theme.BLUE.isActive() || Theme.GRAY.isActive() ? new Margins(0) : new Margins(0, 0, 0, 2));

    BorderLayoutContainer borderLayoutContainer = new BorderLayoutContainer();
    borderLayoutContainer.setWestWidget(grid3, westData);
    borderLayoutContainer.setCenterWidget(grid4, centerData);

    ContentPanel panel = new ContentPanel();
    panel.setHeading("Grid to Grid — Insert at Drop Position");
    panel.add(borderLayoutContainer);

    return panel;
  }

  private ColumnModel<StockProxy> createColumnList(StockProperties props, boolean sortable) {
    ColumnConfig<StockProxy, String> nameColumn = new ColumnConfig<StockProxy, String>(props.nameProp());
    nameColumn.setHeader(SafeHtmlUtils.fromSafeConstant("Stock Name"));
    nameColumn.setSortable(sortable);

    List<ColumnConfig<StockProxy, ?>> columns = new ArrayList<ColumnConfig<StockProxy, ?>>();
    columns.add(nameColumn);

    return new ColumnModel<StockProxy>(columns);
  }

  @Override
  public void onModuleLoad() {
    new ExampleContainer(this)
        .setMinHeight(MIN_HEIGHT)
        .setMinWidth(MIN_WIDTH)
        .doStandalone();
  }

}
