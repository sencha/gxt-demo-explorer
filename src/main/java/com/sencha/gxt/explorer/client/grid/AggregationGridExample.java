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
import java.util.Date;
import java.util.List;

import com.google.gwt.cell.client.DateCell;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.DateTimeFormat.PredefinedFormat;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.cell.core.client.PropertyDisplayCell;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.examples.resources.client.TestData;
import com.sencha.gxt.examples.resources.client.model.Stock;
import com.sencha.gxt.examples.resources.client.model.StockProperties;
import com.sencha.gxt.explorer.client.app.ui.ExampleContainer;
import com.sencha.gxt.explorer.client.model.Example.Detail;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.form.NumberPropertyEditor.DoublePropertyEditor;
import com.sencha.gxt.widget.core.client.grid.AggregationNumberSummaryRenderer;
import com.sencha.gxt.widget.core.client.grid.AggregationRowConfig;
import com.sencha.gxt.widget.core.client.grid.AggregationSafeHtmlRenderer;
import com.sencha.gxt.widget.core.client.grid.ColumnConfig;
import com.sencha.gxt.widget.core.client.grid.ColumnModel;
import com.sencha.gxt.widget.core.client.grid.Grid;
import com.sencha.gxt.widget.core.client.grid.HeaderGroupConfig;
import com.sencha.gxt.widget.core.client.grid.SummaryType.AvgSummaryType;
import com.sencha.gxt.widget.core.client.grid.SummaryType.MaxSummaryType;
import com.sencha.gxt.widget.core.client.grid.SummaryType.MinSummaryType;

@Detail(
    name = "Aggregation Grid",
    category = "Grid",
    icon = "aggregationgrid",
    classes = {
        Stock.class,
        StockProperties.class,
        TestData.class
    },
    maxHeight = AggregationGridExample.MAX_HEIGHT,
    maxWidth = AggregationGridExample.MAX_WIDTH,
    minHeight = AggregationGridExample.MIN_HEIGHT,
    minWidth = AggregationGridExample.MIN_WIDTH
)
public class AggregationGridExample implements IsWidget, EntryPoint {

  protected static final int MAX_HEIGHT = 600;
  protected static final int MAX_WIDTH = 800;
  protected static final int MIN_HEIGHT = 320;
  protected static final int MIN_WIDTH = 480;

  private ContentPanel panel;

  @Override
  public Widget asWidget() {
    if (panel == null) {
      StockProperties properties = GWT.create(StockProperties.class);

      ColumnConfig<Stock, String> nameColumn = new ColumnConfig<Stock, String>(properties.name(), 200, "Company");
      ColumnConfig<Stock, String> symbolColumn = new ColumnConfig<Stock, String>(properties.symbol(), 75, "Symbol");
      ColumnConfig<Stock, Double> lastColumn = new ColumnConfig<Stock, Double>(properties.last(), 100, "Last");
      ColumnConfig<Stock, Double> changeColumn = new ColumnConfig<Stock, Double>(properties.change(), 75, "Change");
      ColumnConfig<Stock, Date> dateColumn = new ColumnConfig<Stock, Date>(properties.lastTrans(), 100, "Date");

      // Horizontal align the column header only
      nameColumn.setHorizontalHeaderAlignment(HasHorizontalAlignment.ALIGN_CENTER);
      symbolColumn.setHorizontalHeaderAlignment(HasHorizontalAlignment.ALIGN_CENTER);
      lastColumn.setHorizontalHeaderAlignment(HasHorizontalAlignment.ALIGN_CENTER);
      changeColumn.setHorizontalHeaderAlignment(HasHorizontalAlignment.ALIGN_CENTER);
      dateColumn.setHorizontalHeaderAlignment(HasHorizontalAlignment.ALIGN_CENTER);

      final NumberFormat currency = NumberFormat.getCurrencyFormat();
      lastColumn.setCell(new PropertyDisplayCell<Double>(new DoublePropertyEditor(currency)));

      final NumberFormat numberFormat = NumberFormat.getFormat("0.00");
      changeColumn.setCell(new PropertyDisplayCell<Double>(new DoublePropertyEditor(numberFormat)) {
        @Override
        public void render(com.google.gwt.cell.client.Cell.Context context, Double value, SafeHtmlBuilder sb) {
          String style = value < 0 ? "red" : "green";
          sb.appendHtmlConstant("<span style='color:" + style + "'>");
          super.render(context, value, sb);
          sb.appendHtmlConstant("</span>");
        }
      });

      dateColumn.setCell(new DateCell(DateTimeFormat.getFormat(PredefinedFormat.DATE_SHORT)));

      final ListStore<Stock> store = new ListStore<Stock>(properties.key());
      store.addAll(TestData.getStocks());

      List<ColumnConfig<Stock, ?>> columns = new ArrayList<ColumnConfig<Stock, ?>>();
      columns.add(nameColumn);
      columns.add(symbolColumn);
      columns.add(lastColumn);
      columns.add(changeColumn);
      columns.add(dateColumn);

      AggregationRowConfig<Stock> averages = new AggregationRowConfig<Stock>();
      averages.setRenderer(nameColumn, new AggregationSafeHtmlRenderer<Stock>("Average"));
      averages.setRenderer(lastColumn, new AggregationNumberSummaryRenderer<Stock, Number>(currency,
          new AvgSummaryType<Number>()));
      averages.setRenderer(changeColumn, new AggregationNumberSummaryRenderer<Stock, Number>(numberFormat,
          new AvgSummaryType<Number>()));

      AggregationRowConfig<Stock> max = new AggregationRowConfig<Stock>();
      max.setRenderer(nameColumn, new AggregationSafeHtmlRenderer<Stock>("Maximum"));
      max.setRenderer(lastColumn, new AggregationNumberSummaryRenderer<Stock, Number>(currency,
          new MaxSummaryType<Number>()));
      max.setRenderer(changeColumn, new AggregationNumberSummaryRenderer<Stock, Number>(numberFormat,
          new MaxSummaryType<Number>()));

      AggregationRowConfig<Stock> min = new AggregationRowConfig<Stock>();
      min.setRenderer(nameColumn, new AggregationSafeHtmlRenderer<Stock>("Minimum"));
      min.setRenderer(lastColumn, new AggregationNumberSummaryRenderer<Stock, Number>(currency,
          new MinSummaryType<Number>()));
      min.setRenderer(changeColumn, new AggregationNumberSummaryRenderer<Stock, Number>(numberFormat,
          new MinSummaryType<Number>()));

      ColumnModel<Stock> cm = new ColumnModel<Stock>(columns);
      cm.addHeaderGroup(0, 0, new HeaderGroupConfig("Stock Information", 1, 2));
      cm.addHeaderGroup(0, 2, new HeaderGroupConfig("Stock Performance", 1, 2));
      cm.addAggregationRow(averages);
      cm.addAggregationRow(max);
      cm.addAggregationRow(min);

      Grid<Stock> grid = new Grid<Stock>(store, cm);
      grid.getView().setAutoExpandColumn(nameColumn);

      panel = new ContentPanel();
      panel.setHeading("Aggregation Grid");
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
