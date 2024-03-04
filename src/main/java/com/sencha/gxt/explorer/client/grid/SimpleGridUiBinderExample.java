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
package com.sencha.gxt.explorer.client.grid;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.examples.resources.client.TestData;
import com.sencha.gxt.examples.resources.client.model.Stock;
import com.sencha.gxt.examples.resources.client.model.StockProperties;
import com.sencha.gxt.explorer.client.app.ui.ExampleContainer;
import com.sencha.gxt.explorer.client.model.Example.Detail;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.grid.ColumnConfig;
import com.sencha.gxt.widget.core.client.grid.ColumnModel;
import com.sencha.gxt.widget.core.client.grid.Grid;
import com.sencha.gxt.widget.core.client.grid.GridView;

@Detail(
    name = "Basic Simple Grid (UiBinder)",
    icon = "basicsimplegriduibinder",
    category = "Grid",
    files = "SimpleGridUiBinderExample.ui.xml",
    classes = {
        Stock.class,
        StockProperties.class,
        TestData.class
    },
    maxHeight = SimpleGridUiBinderExample.MAX_HEIGHT,
    maxWidth = SimpleGridUiBinderExample.MAX_WIDTH,
    minHeight = SimpleGridUiBinderExample.MIN_HEIGHT,
    minWidth = SimpleGridUiBinderExample.MIN_WIDTH
)
public class SimpleGridUiBinderExample implements IsWidget, EntryPoint {

  protected static final int MAX_HEIGHT = 600;
  protected static final int MAX_WIDTH = 800;
  protected static final int MIN_HEIGHT = 320;
  protected static final int MIN_WIDTH = 480;

  private static final StockProperties properties = GWT.create(StockProperties.class);

  interface MyUiBinder extends UiBinder<ContentPanel, SimpleGridUiBinderExample> {
  }

  private static MyUiBinder uiBinder = GWT.create(MyUiBinder.class);

  private ContentPanel panel;

  @UiField(provided = true)
  ColumnModel<Stock> columnModel;
  @UiField(provided = true)
  ListStore<Stock> listStore;
  @UiField
  GridView<Stock> gridView;
  @UiField
  Grid<Stock> grid;

  @Override
  public Widget asWidget() {
    if (panel == null) {
      columnModel = initColumModel();
      listStore = initListStore();

      panel = uiBinder.createAndBindUi(this);

      // Auto expand the name column
      gridView.setAutoExpandColumn(columnModel.getColumn(0));
      gridView.setAutoExpandMax(1000);
    }

    return panel;
  }

  private ColumnModel<Stock> initColumModel() {
    ColumnConfig<Stock, String> nameCol = new ColumnConfig<Stock, String>(properties.name(), 50, "Company");
    ColumnConfig<Stock, String> symbolCol = new ColumnConfig<Stock, String>(properties.symbol(), 75, "Symbol");
    ColumnConfig<Stock, Double> lastCol = new ColumnConfig<Stock, Double>(properties.last(), 75, "Last");

    List<ColumnConfig<Stock, ?>> columns = new ArrayList<ColumnConfig<Stock, ?>>();
    columns.add(nameCol);
    columns.add(symbolCol);
    columns.add(lastCol);

    ColumnModel<Stock> columnModel = new ColumnModel<Stock>(columns);

    return columnModel;
  }

  private ListStore<Stock> initListStore() {
    ListStore<Stock> listStore = new ListStore<Stock>(properties.key());
    listStore.addAll(TestData.getStocks());
    return listStore;
  }

  @Override
  public void onModuleLoad() {
    new ExampleContainer(this)
        .setMaxHeight(MAX_HEIGHT)
        .setMaxWidth(MAX_WIDTH)
        .setMinHeight(MIN_HEIGHT)
        .setMinWidth(MIN_WIDTH)
        .doStandalone();
  }

}
