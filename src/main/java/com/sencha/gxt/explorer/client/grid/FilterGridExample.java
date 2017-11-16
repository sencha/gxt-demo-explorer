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
import com.sencha.gxt.core.client.GXT;
import com.sencha.gxt.core.client.util.DateWrapper;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.examples.resources.client.TestData;
import com.sencha.gxt.examples.resources.client.model.Stock;
import com.sencha.gxt.examples.resources.client.model.StockProperties;
import com.sencha.gxt.explorer.client.app.ui.ExampleContainer;
import com.sencha.gxt.explorer.client.model.Example.Detail;
import com.sencha.gxt.state.client.CookieProvider;
import com.sencha.gxt.state.client.GridFilterStateHandler;
import com.sencha.gxt.state.client.StateManager;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.form.NumberPropertyEditor.DoublePropertyEditor;
import com.sencha.gxt.widget.core.client.grid.ColumnConfig;
import com.sencha.gxt.widget.core.client.grid.ColumnModel;
import com.sencha.gxt.widget.core.client.grid.Grid;
import com.sencha.gxt.widget.core.client.grid.filters.BooleanFilter;
import com.sencha.gxt.widget.core.client.grid.filters.DateFilter;
import com.sencha.gxt.widget.core.client.grid.filters.GridFilters;
import com.sencha.gxt.widget.core.client.grid.filters.ListFilter;
import com.sencha.gxt.widget.core.client.grid.filters.NumericFilter;
import com.sencha.gxt.widget.core.client.grid.filters.StringFilter;

@Detail(
    name = "Filter Grid",
    category = "Grid",
    icon = "filtergrid",
    classes = {
        Stock.class,
        StockProperties.class,
        TestData.class
    },
    maxHeight = FilterGridExample.MAX_HEIGHT,
    maxWidth = FilterGridExample.MAX_WIDTH,
    minHeight = FilterGridExample.MIN_HEIGHT,
    minWidth = FilterGridExample.MIN_WIDTH
)
public class FilterGridExample implements IsWidget, EntryPoint {

  protected static final int MAX_HEIGHT = 600;
  protected static final int MAX_WIDTH = 800;
  protected static final int MIN_HEIGHT = 320;
  protected static final int MIN_WIDTH = 600;

  private static final StockProperties props = GWT.create(StockProperties.class);

  private ContentPanel panel;

  @Override
  public Widget asWidget() {
    if (panel == null) {
      ColumnConfig<Stock, String> nameCol = new ColumnConfig<Stock, String>(props.name(), 200, "Company");
      ColumnConfig<Stock, String> symbolCol = new ColumnConfig<Stock, String>(props.symbol(), 75, "Symbol");
      ColumnConfig<Stock, Double> lastCol = new ColumnConfig<Stock, Double>(props.last(), 75, "Last");
      ColumnConfig<Stock, Double> changeCol = new ColumnConfig<Stock, Double>(props.change(), 75, "Change");
      ColumnConfig<Stock, Date> lastTransCol = new ColumnConfig<Stock, Date>(props.lastTrans(), 100, "Last Updated");
      ColumnConfig<Stock, Boolean> splitCol = new ColumnConfig<Stock, Boolean>(props.split(), 50, "Split");
      ColumnConfig<Stock, String> typeCol = new ColumnConfig<Stock, String>(props.industry(), 75, "Type");

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

      splitCol.setCell(new AbstractCell<Boolean>() {
        @Override
        public void render(Context context, Boolean value, SafeHtmlBuilder sb) {
          sb.appendHtmlConstant(value ? "Yes" : "No");
        }
      });

      List<ColumnConfig<Stock, ?>> columns = new ArrayList<ColumnConfig<Stock, ?>>();
      columns.add(nameCol);
      columns.add(symbolCol);
      columns.add(lastCol);
      columns.add(changeCol);
      columns.add(lastTransCol);
      columns.add(splitCol);
      columns.add(typeCol);

      ColumnModel<Stock> cm = new ColumnModel<Stock>(columns);

      ListStore<Stock> store = new ListStore<Stock>(props.key());
      store.addAll(TestData.getStocks());

      final Grid<Stock> grid = new Grid<Stock>(store, cm);
      grid.setColumnReordering(true);
      grid.getView().setAutoExpandColumn(nameCol);
      grid.setBorders(false);
      grid.getView().setStripeRows(true);
      grid.getView().setColumnLines(true);

      // State manager, make this grid stateful
      grid.setStateful(true);
      grid.setStateId("filterGridExample");

      ListStore<String> typeStore = new ListStore<String>(new ModelKeyProvider<String>() {
        @Override
        public String getKey(String item) {
          return item;
        }
      });
      typeStore.add("Auto");
      typeStore.add("Media");
      typeStore.add("Medical");
      typeStore.add("Tech");

      NumericFilter<Stock, Double> lastFilter = new NumericFilter<Stock, Double>(props.last(), new DoublePropertyEditor());
      lastFilter.setLessThanValue(100D);
      lastFilter.setActive(true, false);

      StringFilter<Stock> nameFilter = new StringFilter<Stock>(props.name());

      DateFilter<Stock> dateFilter = new DateFilter<Stock>(props.lastTrans());
      dateFilter.setMinDate(new DateWrapper().addDays(-5).asDate());
      dateFilter.setMaxDate(new DateWrapper().addMonths(2).asDate());

      BooleanFilter<Stock> booleanFilter = new BooleanFilter<Stock>(props.split());
      ListFilter<Stock, String> listFilter = new ListFilter<Stock, String>(props.industry(), typeStore);
      listFilter.setUseStoreKeys(true);

      GridFilters<Stock> filters = new GridFilters<Stock>();
      filters.initPlugin(grid);
      filters.setLocal(true);
      filters.addFilter(lastFilter);
      filters.addFilter(nameFilter);
      filters.addFilter(dateFilter);
      filters.addFilter(booleanFilter);
      filters.addFilter(listFilter);

      // Stage manager, load the previous state
      GridFilterStateHandler<Stock> handler = new GridFilterStateHandler<Stock>(grid, filters);
      handler.loadState();

      panel = new ContentPanel();
      panel.setHeading("Filter Grid");
      panel.add(grid);
    }

    return panel;
  }

  @Override
  public void onModuleLoad() {
    // State manager, initialize the state options
    StateManager.get().setProvider(new CookieProvider("/", null, null, GXT.isSecure()));

    new ExampleContainer(this)
        .setMaxHeight(MAX_HEIGHT)
        .setMaxWidth(MAX_WIDTH)
        .setMinHeight(MIN_HEIGHT)
        .setMinWidth(MIN_WIDTH)
        .doStandalone();
  }

}
