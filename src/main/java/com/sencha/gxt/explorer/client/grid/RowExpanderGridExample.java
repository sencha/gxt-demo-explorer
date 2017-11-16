package com.sencha.gxt.explorer.client.grid;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.cell.client.DateCell;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
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
import com.sencha.gxt.widget.core.client.grid.RowExpander;

@Detail(
    name = "Row Expander Grid",
    category = "Grid",
    icon = "rowexpandergrid",
    classes = {
        Stock.class,
        StockProperties.class,
        TestData.class
    },
    maxHeight = RowExpanderGridExample.MAX_HEIGHT,
    maxWidth = RowExpanderGridExample.MAX_WIDTH,
    minHeight = RowExpanderGridExample.MIN_HEIGHT,
    minWidth = RowExpanderGridExample.MIN_WIDTH
)
public class RowExpanderGridExample implements IsWidget, EntryPoint {

  protected static final int MAX_HEIGHT = 600;
  protected static final int MAX_WIDTH = 800;
  protected static final int MIN_HEIGHT = 320;
  protected static final int MIN_WIDTH = 480;

  private static final StockProperties properties = GWT.create(StockProperties.class);

  private ContentPanel panel;

  @Override
  public Widget asWidget() {
    if (panel == null) {
      final String text = "Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Sed metus nibh, sodales a, porta at, vulputate eget, dui. Pellentesque ut nisl. Maecenas tortor turpis, interdum non, sodales non, iaculis ac, lacus. Vestibulum auctor, tortor quis iaculis malesuada, libero lectus bibendum purus, sit amet tincidunt quam turpis vel lacus. In pellentesque nisl non sem. Suspendisse nunc sem, pretium eget, cursus a, fringilla vel, urna.<br/><br/>Aliquam commodo ullamcorper erat. Nullam vel justo in neque porttitor laoreet. Aenean lacus dui, consequat eu, adipiscing eget, nonummy non, nisi. Morbi nunc est, dignissim non, ornare sed, luctus eu, massa. Vivamus eget quam. Vivamus tincidunt diam nec urna. Curabitur velit.";

      RowExpander<Stock> rowExpander = new RowExpander<Stock>(new AbstractCell<Stock>() {
        @Override
        public void render(Context context, Stock value, SafeHtmlBuilder sb) {
          sb.appendHtmlConstant("<p style='margin: 5px 5px 10px'><b>Company:</b> " + value.getName() + "</p>");
          sb.appendHtmlConstant("<p style='margin: 5px 5px 10px'><b>Summary:</b> " + text);
        }
      });

      ColumnConfig<Stock, String> nameCol = new ColumnConfig<Stock, String>(properties.name(), 200, "Company");
      ColumnConfig<Stock, String> symbolCol = new ColumnConfig<Stock, String>(properties.symbol(), 75, "Symbol");
      ColumnConfig<Stock, Double> lastCol = new ColumnConfig<Stock, Double>(properties.last(), 75, "Last");
      ColumnConfig<Stock, Double> changeCol = new ColumnConfig<Stock, Double>(properties.change(), 75, "Change");
      ColumnConfig<Stock, Date> lastTransCol = new ColumnConfig<Stock, Date>(properties.lastTrans(), 100, "Last Updated");

      final NumberFormat number = NumberFormat.getFormat("0.00");
      changeCol.setCell(new AbstractCell<Double>() {
        @Override
        public void render(Context context, Double value, SafeHtmlBuilder sb) {
          String style = "style='color: " + (value < 0 ? "red" : "green") + "'";
          String v = number.format(value);
          sb.appendHtmlConstant("<span " + style + ">" + v + "</span>");
        }
      });

      lastTransCol.setCell(new DateCell(DateTimeFormat.getFormat("MM/dd/yyyy")));

      List<ColumnConfig<Stock, ?>> columns = new ArrayList<ColumnConfig<Stock, ?>>();
      columns.add(rowExpander);
      columns.add(nameCol);
      columns.add(symbolCol);
      columns.add(lastCol);
      columns.add(changeCol);
      columns.add(lastTransCol);

      ColumnModel<Stock> cm = new ColumnModel<Stock>(columns);

      ListStore<Stock> store = new ListStore<Stock>(properties.key());
      store.addAll(TestData.getStocks());

      final Grid<Stock> grid = new Grid<Stock>(store, cm);
      grid.getView().setAutoExpandColumn(nameCol);
      grid.setBorders(false);
      grid.getView().setStripeRows(true);
      grid.getView().setColumnLines(true);

      rowExpander.initPlugin(grid);

      panel = new ContentPanel();
      panel.setHeading("Row Expander Grid");
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
