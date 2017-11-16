package com.sencha.gxt.explorer.client.grid;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.gwt.cell.client.DateCell;
import com.google.gwt.cell.client.TextCell;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.DateTimeFormat;
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
import com.sencha.gxt.widget.core.client.grid.GroupingView;

@Detail(
    name = "Grouping Grid",
    category = "Grid",
    icon = "groupinggrid",
    classes = {
        Stock.class,
        StockProperties.class,
        TestData.class
    },
    maxHeight = GroupingGridExample.MAX_HEIGHT,
    maxWidth = GroupingGridExample.MAX_WIDTH,
    minHeight = GroupingGridExample.MIN_HEIGHT,
    minWidth = GroupingGridExample.MIN_WIDTH
)
public class GroupingGridExample implements EntryPoint, IsWidget {

  public static final int MAX_HEIGHT = 600;
  public static final int MAX_WIDTH = 800;
  public static final int MIN_HEIGHT = 320;
  public static final int MIN_WIDTH = 480;

  private ContentPanel panel;

  @Override
  public Widget asWidget() {
    if (panel == null) {
      StockProperties properties = GWT.create(StockProperties.class);

      ListStore<Stock> store = new ListStore<Stock>(properties.key());
      store.addAll(TestData.getCompanies());

      ColumnConfig<Stock, String> name = new ColumnConfig<Stock, String>(properties.name(), 60);
      ColumnConfig<Stock, Double> price = new ColumnConfig<Stock, Double>(properties.open(), 20);
      ColumnConfig<Stock, Double> change = new ColumnConfig<Stock, Double>(properties.change(), 20);
      ColumnConfig<Stock, String> industry = new ColumnConfig<Stock, String>(properties.industry(), 20);
      ColumnConfig<Stock, Date> lastUpdated = new ColumnConfig<Stock, Date>(properties.lastTrans(), 20);

      name.setHeader("Name");
      price.setHeader("Price");
      change.setHeader("Change");
      industry.setHeader("Industry");
      lastUpdated.setHeader("Last Updated");

      name.setCell(new TextCell());
      lastUpdated.setCell(new DateCell(DateTimeFormat.getFormat("MM/dd/yyyy")));

      List<ColumnConfig<Stock, ?>> columns = new ArrayList<ColumnConfig<Stock, ?>>();
      columns.add(name);
      columns.add(price);
      columns.add(change);
      columns.add(industry);
      columns.add(lastUpdated);

      ColumnModel<Stock> cm = new ColumnModel<Stock>(columns);

      final GroupingView<Stock> groupingView = new GroupingView<Stock>();
      groupingView.setShowGroupedColumn(false);
      groupingView.groupBy(industry);
      groupingView.setForceFit(true);
      groupingView.setAutoExpandColumn(name);

      Grid<Stock> grid = new Grid<Stock>(store, cm, groupingView);

      panel = new ContentPanel();
      panel.setHeading("Grouping Grid");
      panel.add(grid);
    }

    return panel;
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
