package com.sencha.gxt.explorer.client.chart;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.client.Random;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.cell.core.client.NumberCell;
import com.sencha.gxt.chart.client.chart.Chart;
import com.sencha.gxt.chart.client.chart.Chart.Position;
import com.sencha.gxt.chart.client.chart.axis.CategoryAxis;
import com.sencha.gxt.chart.client.chart.event.SeriesSelectionEvent;
import com.sencha.gxt.chart.client.chart.event.SeriesSelectionEvent.SeriesSelectionHandler;
import com.sencha.gxt.chart.client.chart.series.BarSeries;
import com.sencha.gxt.chart.client.chart.series.SeriesHighlighter;
import com.sencha.gxt.chart.client.chart.series.SeriesLabelConfig;
import com.sencha.gxt.chart.client.draw.Color;
import com.sencha.gxt.chart.client.draw.RGB;
import com.sencha.gxt.chart.client.draw.sprite.RectangleSprite;
import com.sencha.gxt.chart.client.draw.sprite.Sprite;
import com.sencha.gxt.chart.client.draw.sprite.TextSprite;
import com.sencha.gxt.chart.client.draw.sprite.TextSprite.TextAnchor;
import com.sencha.gxt.chart.client.draw.sprite.TextSprite.TextBaseline;
import com.sencha.gxt.core.client.Style.SelectionMode;
import com.sencha.gxt.core.client.dom.XElement;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.data.shared.LabelProvider;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.data.shared.event.StoreFilterEvent;
import com.sencha.gxt.data.shared.event.StoreFilterEvent.StoreFilterHandler;
import com.sencha.gxt.examples.resources.client.FormattedNumericFilter;
import com.sencha.gxt.examples.resources.client.model.DashboardDataProperties;
import com.sencha.gxt.examples.resources.client.model.Data;
import com.sencha.gxt.examples.resources.client.model.RadarData;
import com.sencha.gxt.examples.resources.client.model.RadarDataProperties;
import com.sencha.gxt.explorer.client.app.ui.ExampleContainer;
import com.sencha.gxt.explorer.client.model.Example.Detail;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer.BorderLayoutData;
import com.sencha.gxt.widget.core.client.container.SimpleContainer;
import com.sencha.gxt.widget.core.client.event.SortChangeEvent;
import com.sencha.gxt.widget.core.client.event.SortChangeEvent.SortChangeHandler;
import com.sencha.gxt.widget.core.client.form.NumberPropertyEditor.DoublePropertyEditor;
import com.sencha.gxt.widget.core.client.grid.ColumnConfig;
import com.sencha.gxt.widget.core.client.grid.ColumnModel;
import com.sencha.gxt.widget.core.client.grid.Grid;
import com.sencha.gxt.widget.core.client.grid.filters.GridFilters;
import com.sencha.gxt.widget.core.client.grid.filters.StringFilter;
import com.sencha.gxt.widget.core.client.selection.SelectionChangedEvent;
import com.sencha.gxt.widget.core.client.selection.SelectionChangedEvent.SelectionChangedHandler;

@Detail(
    name = "Company Dashboard",
    icon = "dashboard",
    category = "Charts",
    classes = {
        Data.class,
        RadarData.class,
        CompanyDetailsEditor.class,
        FormattedNumericFilter.class,
        DashboardDataProperties.class,
        RadarDataProperties.class
    },
    minHeight = DashboardExample.MIN_HEIGHT,
    minWidth = DashboardExample.MIN_WIDTH
)
public class DashboardExample implements IsWidget, EntryPoint {

  private final static String[] companies = {
      "3m Co", "Alcoa Inc", "Altria Group Inc", "American Express Company", "American International Group, Inc.",
      "AT&T Inc", "Boeing Co.", "Caterpillar Inc.", "Citigroup, Inc.", "E.I. du Pont de Nemours and Company",
      "Exxon Mobil Corp", "General Electric Company", "General Motors Corporation", "Hewlett-Packard Co",
      "Honeywell Intl Inc", "Intel Corporation", "International Business Machines", "Johnson & Johnson",
      "JP Morgan & Chase & Co", "McDonald\"s Corporation", "Merck & Co., Inc.", "Microsoft Corporation", "Pfizer Inc",
      "The Coca-Cola Company", "The Home Depot, Inc.", "The Procter & Gamble Company",
      "United Technologies Corporation", "Verizon Communications", "Wal-Mart Stores, Inc."};

  protected static final int MIN_HEIGHT = 885;
  protected static final int MIN_WIDTH = 860;

  private static final DashboardDataProperties dataProperties = GWT.<DashboardDataProperties> create(DashboardDataProperties.class);

  private final ListStore<Data> store = new ListStore<Data>(dataProperties.nameKey());

  private Chart<Data> barChart;
  private Grid<Data> grid;
  private ContentPanel panel;
  private CompanyDetailsEditor companyDetailsEditor;
  private Data lastSelected;

  @Override
  public Widget asWidget() {
    if (panel == null) {
      for (int i = 0; i < companies.length; i++) {
        store.add(new Data(companies[i], Random.nextDouble() * 100, Random.nextDouble() * 100,
            Random.nextDouble() * 100, Random.nextDouble() * 100, Random.nextDouble() * 100, 0, 0, 0, 0));
      }
      store.addStoreFilterHandler(new StoreFilterHandler<Data>() {
        @Override
        public void onFilter(StoreFilterEvent<Data> event) {
          barChart.redrawChart();
          grid.getSelectionModel().select(0, false);
        }
      });

      initializeGrid();
      initializeBarChart();

      companyDetailsEditor = new CompanyDetailsEditor();
      companyDetailsEditor.addValueChangeHandler(new ValueChangeHandler<Data>() {
        @Override
        public void onValueChange(ValueChangeEvent<Data> event) {
          store.update(event.getValue());
          barChart.redrawChart();
          doHighlightSelected();
        }
      });

      grid.addSortChangeHandler(new SortChangeHandler() {
        @Override
        public void onSortChange(SortChangeEvent event) {
          barChart.redrawChart();
          doHighlightSelected();
          grid.getView().ensureVisible(grid.getSelectionModel().getSelectedItem());
        }
      });

      grid.getSelectionModel().addSelectionChangedHandler(new SelectionChangedHandler<Data>() {
        @Override
        public void onSelectionChanged(SelectionChangedEvent<Data> event) {
          if (event.getSelection().isEmpty()) {
            // disable ability to deselect
            grid.getSelectionModel().select(lastSelected, false);
            return;
          }

          if (lastSelected != null) {
            int index = store.indexOf(lastSelected);
            if (index >= 0) {
              barChart.getSeries(0).unHighlight(index);
            }
          }

          lastSelected = event.getSelection().get(0);
          doHighlightSelected();
          companyDetailsEditor.setValue(lastSelected);
          grid.getView().ensureVisible(grid.getSelectionModel().getSelectedItem());
        }
      });

      StringFilter<Data> nameFilter = new StringFilter<Data>(dataProperties.name());
      FormattedNumericFilter priceFilter = new FormattedNumericFilter(dataProperties.price(), new DoublePropertyEditor(), "0.00");
      FormattedNumericFilter revenueFilter = new FormattedNumericFilter(dataProperties.revenue(), new DoublePropertyEditor(), "0.00");
      FormattedNumericFilter growthFilter = new FormattedNumericFilter(dataProperties.growth(), new DoublePropertyEditor(), "0.00");
      FormattedNumericFilter productFilter = new FormattedNumericFilter(dataProperties.product(), new DoublePropertyEditor(), "0.00");
      FormattedNumericFilter marketFilter = new FormattedNumericFilter(dataProperties.market(), new DoublePropertyEditor(), "0.00");

      GridFilters<Data> filters = new GridFilters<Data>();
      filters.initPlugin(grid);
      filters.setLocal(true);
      filters.addFilter(nameFilter);
      filters.addFilter(priceFilter);
      filters.addFilter(revenueFilter);
      filters.addFilter(growthFilter);
      filters.addFilter(productFilter);
      filters.addFilter(marketFilter);

      SimpleContainer barChartContainer = new SimpleContainer();
      barChartContainer.add(barChart);

      ContentPanel centerPanel = new ContentPanel();
      centerPanel.setHeading("Company Data");
      centerPanel.setWidget(grid);

      BorderLayoutData centerLayoutData = new BorderLayoutData();
      centerLayoutData.setMargins(new Margins(5, 5, 0, 0));

      BorderLayoutData eastLayoutData = new BorderLayoutData(330);
      eastLayoutData.setMargins(new Margins(5, 0, 0, 0));

      BorderLayoutContainer container = new BorderLayoutContainer();
      container.setNorthWidget(barChartContainer, new BorderLayoutData(200));
      container.setCenterWidget(centerPanel, centerLayoutData);
      container.setEastWidget(companyDetailsEditor, eastLayoutData);

      panel = new ContentPanel();
      panel.setHeading("Company Dashboard");
      panel.add(container);

      grid.getSelectionModel().select(0, false);
    }

    return panel;
  }

  @Override
  public void onModuleLoad() {
    new ExampleContainer(this)
        .setMinHeight(MIN_HEIGHT)
        .setMinWidth(MIN_WIDTH)
        .doStandalone();
  }

  private void doHighlightSelected() {
    Scheduler.get().scheduleDeferred(new ScheduledCommand() {
      @Override
      public void execute() {
        barChart.getSeries(0).highlight(store.indexOf(grid.getSelectionModel().getSelectedItem()));
      }
    });
  }

  private void initializeBarChart() {
    TextSprite rotation = new TextSprite();
    rotation.setRotation(270);

    CategoryAxis<Data, String> catAxis = new CategoryAxis<Data, String>();
    catAxis.setPosition(Position.BOTTOM);
    catAxis.setField(dataProperties.name());
    catAxis.setLabelConfig(rotation);
    catAxis.setLabelProvider(new LabelProvider<String>() {
      @Override
      public String getLabel(String item) {
        if (item.length() > 8) {
          return item.substring(0, 8) + "...";
        } else {
          return item;
        }
      }
    });

    rotation = rotation.copy();
    rotation.setTextAnchor(TextAnchor.END);
    rotation.setTextBaseline(TextBaseline.MIDDLE);

    SeriesLabelConfig<Data> barLabelConfig = new SeriesLabelConfig<Data>();
    barLabelConfig.setSpriteConfig(rotation);

    final BarSeries<Data> bar = new BarSeries<Data>();
    bar.setYAxisPosition(Position.LEFT);
    bar.addYField(dataProperties.price());
    bar.setLabelConfig(barLabelConfig);
    bar.addColor(new RGB(148, 174, 10));
    bar.setColumn(true);
    bar.setHighlighter(new SeriesHighlighter() {
      @Override
      public void highlight(Sprite sprite) {
        if (sprite instanceof RectangleSprite) {
          RectangleSprite bar = (RectangleSprite) sprite;
          bar.setStroke(new RGB(85, 85, 204));
          bar.setStrokeWidth(3);
          bar.setFill(new RGB("#a2b5ca"));
          bar.redraw();
        }
      }

      @Override
      public void unHighlight(Sprite sprite) {
        if (sprite instanceof RectangleSprite) {
          RectangleSprite bar = (RectangleSprite) sprite;
          bar.setStroke(Color.NONE);
          bar.setStrokeWidth(0);
          bar.setFill(new RGB(148, 174, 10));
          bar.redraw();
        }
      }
    });

    bar.addSeriesSelectionHandler(new SeriesSelectionHandler<Data>() {
      @Override
      public void onSeriesSelection(SeriesSelectionEvent<Data> event) {
        grid.getSelectionModel().select(event.getIndex(), false);
      }
    });

    barChart = new Chart<Data>();
    barChart.setStore(store);
    barChart.setShadowChart(false);
    barChart.setAnimated(true);
    barChart.addAxis(catAxis);
    barChart.addSeries(bar);
  }

  private void initializeGrid() {
    ColumnConfig<Data, String> nameColumnConfig = new ColumnConfig<Data, String>(dataProperties.name(), 120, "Name");
    ColumnConfig<Data, Double> priceColumnConfig = new ColumnConfig<Data, Double>(dataProperties.price(), 75, "Price $");
    ColumnConfig<Data, Double> revenueColumnConfig = new ColumnConfig<Data, Double>(dataProperties.revenue(), 75, "Revenue %");
    ColumnConfig<Data, Double> growthColumnConfig = new ColumnConfig<Data, Double>(dataProperties.growth(), 75, "Growth %");
    ColumnConfig<Data, Double> productColumnConfig = new ColumnConfig<Data, Double>(dataProperties.product(), 75, "Product %");
    ColumnConfig<Data, Double> marketColumnConfig = new ColumnConfig<Data, Double>(dataProperties.market(), 75, "Market %");
    
    priceColumnConfig.setCell(new NumberCell<Double>(NumberFormat.getFormat("0.00")));
    revenueColumnConfig.setCell(new NumberCell<Double>(NumberFormat.getFormat("0.00")));
    growthColumnConfig.setCell(new NumberCell<Double>(NumberFormat.getFormat("0.00")));
    productColumnConfig.setCell(new NumberCell<Double>(NumberFormat.getFormat("0.00")));
    marketColumnConfig.setCell(new NumberCell<Double>(NumberFormat.getFormat("0.00")));

    List<ColumnConfig<Data, ?>> columns = new ArrayList<ColumnConfig<Data, ?>>();
    columns.add(nameColumnConfig);
    columns.add(priceColumnConfig);
    columns.add(revenueColumnConfig);
    columns.add(growthColumnConfig);
    columns.add(productColumnConfig);
    columns.add(marketColumnConfig);

    grid = new Grid<Data>(store, new ColumnModel<Data>(columns));
    grid.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    grid.getView().setAutoExpandColumn(nameColumnConfig);
  }

}
