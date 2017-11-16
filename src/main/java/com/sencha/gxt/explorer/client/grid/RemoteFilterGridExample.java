package com.sencha.gxt.explorer.client.grid;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.cell.client.DateCell;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.data.client.loader.RpcProxy;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.loader.FilterConfig;
import com.sencha.gxt.data.shared.loader.FilterConfigBean;
import com.sencha.gxt.data.shared.loader.FilterPagingLoadConfig;
import com.sencha.gxt.data.shared.loader.FilterPagingLoadConfigBean;
import com.sencha.gxt.data.shared.loader.LoadResultListStoreBinding;
import com.sencha.gxt.data.shared.loader.PagingLoadResult;
import com.sencha.gxt.data.shared.loader.PagingLoader;
import com.sencha.gxt.examples.resources.client.ExampleService;
import com.sencha.gxt.examples.resources.client.ExampleServiceAsync;
import com.sencha.gxt.examples.resources.client.model.Stock;
import com.sencha.gxt.examples.resources.client.model.StockProperties;
import com.sencha.gxt.explorer.client.app.ui.ExampleContainer;
import com.sencha.gxt.explorer.client.model.Example.Detail;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;
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
import com.sencha.gxt.widget.core.client.toolbar.PagingToolBar;

@Detail(
    name = "Remote Filter Grid",
    category = "Grid",
    icon = "filtergrid",
    classes = {
        Stock.class,
        StockProperties.class,
        ExampleServiceAsync.class,
        ExampleService.class
    },
    maxHeight = RemoteFilterGridExample.MAX_HEIGHT,
    maxWidth = RemoteFilterGridExample.MAX_WIDTH,
    minHeight = RemoteFilterGridExample.MIN_HEIGHT,
    minWidth = RemoteFilterGridExample.MIN_WIDTH
)
public class RemoteFilterGridExample implements IsWidget, EntryPoint {

  protected static final int MAX_HEIGHT = 600;
  protected static final int MAX_WIDTH = 800;
  protected static final int MIN_HEIGHT = 320;
  protected static final int MIN_WIDTH = 600;

  private static final StockProperties properties = GWT.create(StockProperties.class);

  private ContentPanel panel;

  @Override
  public Widget asWidget() {
    if (panel == null) {
      final ExampleServiceAsync rpcService = GWT.create(ExampleService.class);

      RpcProxy<FilterPagingLoadConfig, PagingLoadResult<Stock>> rpcProxy = new RpcProxy<FilterPagingLoadConfig, PagingLoadResult<Stock>>() {
        @Override
        public void load(FilterPagingLoadConfig loadConfig, AsyncCallback<PagingLoadResult<Stock>> callback) {
          rpcService.getStocks(loadConfig, callback);
        }
      };

      ListStore<Stock> store = new ListStore<Stock>(properties.key());

      final PagingLoader<FilterPagingLoadConfig, PagingLoadResult<Stock>> remoteLoader = new PagingLoader<FilterPagingLoadConfig, PagingLoadResult<Stock>>(
          rpcProxy);
      remoteLoader.useLoadConfig(new FilterPagingLoadConfigBean());
      remoteLoader.setRemoteSort(true);
      remoteLoader.addLoadHandler(new LoadResultListStoreBinding<FilterPagingLoadConfig, Stock, PagingLoadResult<Stock>>(store));

      ColumnConfig<Stock, String> nameColumn = new ColumnConfig<Stock, String>(properties.name(), 200, "Company");
      ColumnConfig<Stock, String> symbolColumn = new ColumnConfig<Stock, String>(properties.symbol(), 75, "Symbol");
      ColumnConfig<Stock, Double> lastColumn = new ColumnConfig<Stock, Double>(properties.last(), 75, "Last");
      ColumnConfig<Stock, Double> changeColumn = new ColumnConfig<Stock, Double>(properties.change(), 75, "Change");
      ColumnConfig<Stock, Date> lastTransColumn = new ColumnConfig<Stock, Date>(properties.lastTrans(), 100, "Last Updated");
      ColumnConfig<Stock, Boolean> splitColumn = new ColumnConfig<Stock, Boolean>(properties.split(), 50, "Split");
      ColumnConfig<Stock, String> typeColumn = new ColumnConfig<Stock, String>(properties.industry(), 75, "Type");

      final NumberFormat number = NumberFormat.getFormat("0.00");
      changeColumn.setCell(new AbstractCell<Double>() {
        @Override
        public void render(Context context, Double value, SafeHtmlBuilder sb) {
          String style = "style='color: " + (value < 0 ? "red" : "green") + "'";
          String v = number.format(value);
          sb.appendHtmlConstant("<span " + style + ">" + v + "</span>");
        }
      });

      lastTransColumn.setCell(new DateCell(DateTimeFormat.getFormat("MM/dd/yyyy")));

      splitColumn.setCell(new AbstractCell<Boolean>() {
        @Override
        public void render(Context context, Boolean value, SafeHtmlBuilder sb) {
          sb.appendHtmlConstant(value ? "Yes" : "No");
        }
      });

      List<ColumnConfig<Stock, ?>> columns = new ArrayList<ColumnConfig<Stock, ?>>();
      columns.add(nameColumn);
      columns.add(symbolColumn);
      columns.add(lastColumn);
      columns.add(changeColumn);
      columns.add(lastTransColumn);
      columns.add(splitColumn);
      columns.add(typeColumn);

      ColumnModel<Stock> cm = new ColumnModel<Stock>(columns);

      final Grid<Stock> grid = new Grid<Stock>(store, cm) {
        @Override
        protected void onAfterFirstAttach() {
          super.onAfterFirstAttach();
          Scheduler.get().scheduleDeferred(new ScheduledCommand() {
            @Override
            public void execute() {
              remoteLoader.load();
            }
          });
        }
      };
      grid.setLoader(remoteLoader);
      grid.getView().setAutoExpandColumn(nameColumn);
      grid.getView().setStripeRows(true);
      grid.getView().setColumnLines(true);

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

      NumericFilter<Stock, Double> lastFilter = new NumericFilter<Stock, Double>(properties.last(), new DoublePropertyEditor());
      StringFilter<Stock> nameFilter = new StringFilter<Stock>(properties.name());
      DateFilter<Stock> dateFilter = new DateFilter<Stock>(properties.lastTrans());
      BooleanFilter<Stock> booleanFilter = new BooleanFilter<Stock>(properties.split());
      ListFilter<Stock, String> listFilter = new ListFilter<Stock, String>(properties.industry(), typeStore);

      GridFilters<Stock> filters = new GridFilters<Stock>(remoteLoader);
      filters.initPlugin(grid);
      filters.addFilter(lastFilter);
      filters.addFilter(nameFilter);
      filters.addFilter(dateFilter);
      filters.addFilter(booleanFilter);
      filters.addFilter(listFilter);

      FilterConfigBean filterConfigBean = new FilterConfigBean();
      filterConfigBean.setComparison("lt");
      filterConfigBean.setField("numeric");
      filterConfigBean.setValue("100");

      List<FilterConfig> configs = new ArrayList<FilterConfig>();
      configs.add(filterConfigBean);
      lastFilter.setValue(configs);
      lastFilter.setActive(true, false);

      final PagingToolBar toolBar = new PagingToolBar(25);
      toolBar.setBorders(false);
      toolBar.bind(remoteLoader);

      VerticalLayoutContainer verticalLayoutContainer = new VerticalLayoutContainer();
      verticalLayoutContainer.add(grid, new VerticalLayoutData(1, 1));
      verticalLayoutContainer.add(toolBar, new VerticalLayoutData(1, -1));

      panel = new ContentPanel();
      panel.setHeading("Remote Filter Grid");
      panel.add(verticalLayoutContainer);
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
