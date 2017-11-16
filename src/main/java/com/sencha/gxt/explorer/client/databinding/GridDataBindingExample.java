package com.sencha.gxt.explorer.client.databinding;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor;
import com.google.gwt.editor.client.SimpleBeanEditorDriver;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.core.client.Style.SelectionMode;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.data.client.editor.ListStoreEditor;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.examples.resources.client.TestData;
import com.sencha.gxt.examples.resources.client.model.Stock;
import com.sencha.gxt.examples.resources.client.model.StockProperties;
import com.sencha.gxt.explorer.client.app.ui.ExampleContainer;
import com.sencha.gxt.explorer.client.databinding.GridDataBindingExample.StockExchange;
import com.sencha.gxt.explorer.client.model.Example.Detail;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.grid.ColumnConfig;
import com.sencha.gxt.widget.core.client.grid.ColumnModel;
import com.sencha.gxt.widget.core.client.grid.Grid;
import com.sencha.gxt.widget.core.client.selection.SelectionChangedEvent;
import com.sencha.gxt.widget.core.client.selection.SelectionChangedEvent.SelectionChangedHandler;

/**
 * Demonstrates using the ListStoreEditor, and some concept of building multiple
 * editors. Note that as currently written, when a Stock object is saved, it
 * will modify the StockExchange's instances, instead of cloning models before
 * editing them.
 */
@Detail(
    name = "Grid Data Binding",
    category = "Data Binding",
    icon = "gridbinding",
    classes = {
        Stock.class,
        StockProperties.class,
        StockEditor.class,
        TestData.class
    },
    minHeight = GridDataBindingExample.MIN_HEIGHT,
    minWidth = GridDataBindingExample.MIN_WIDTH
)
public class GridDataBindingExample implements EntryPoint, IsWidget, Editor<StockExchange> {

  public static class StockExchange {
    private List<Stock> stocks = TestData.getStocks();

    public List<Stock> getStocks() {
      return stocks;
    }

    public void setStocks(List<Stock> stocks) {
      this.stocks = stocks;
    }
  }

  interface ListDriver extends SimpleBeanEditorDriver<StockExchange, GridDataBindingExample> {
  }

  interface StockDriver extends SimpleBeanEditorDriver<Stock, StockEditor> {
  }

  protected static final int MIN_HEIGHT = 325;
  protected static final int MIN_WIDTH = 300;

  private ListDriver driver = GWT.create(ListDriver.class);
  private StockDriver itemDriver = GWT.create(StockDriver.class);
  private ContentPanel panel;

  Grid<Stock> stockListGrid;
  ListStoreEditor<Stock> stocks;

  @Ignore
  StockEditor stockEditor;

  @Override
  public Widget asWidget() {
    if (panel == null) {
      final StockProperties properties = GWT.create(StockProperties.class);

      ColumnConfig<Stock, String> nameColumn = new ColumnConfig<Stock, String>(properties.name(), 200, "Name");
      ColumnConfig<Stock, String> symbolColumn = new ColumnConfig<Stock, String>(properties.symbol(), 70, "Symbol");

      List<ColumnConfig<Stock, ?>> columns = new ArrayList<ColumnConfig<Stock, ?>>();
      columns.add(nameColumn);
      columns.add(symbolColumn);

      stockListGrid = new Grid<Stock>(new ListStore<Stock>(properties.key()), new ColumnModel<Stock>(columns));
      stockListGrid.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
      stockListGrid.getView().setAutoExpandColumn(nameColumn);
      stockListGrid.getSelectionModel().addSelectionChangedHandler(new SelectionChangedHandler<Stock>() {
        @Override
        public void onSelectionChanged(SelectionChangedEvent<Stock> event) {
          if (event.getSelection().size() > 0) {
            edit(event.getSelection().get(0));
          } else {
            stockEditor.setSaveEnabled(false);
          }
        }
      });

      stocks = new ListStoreEditor<Stock>(stockListGrid.getStore());

      stockEditor = new StockEditor();
      stockEditor.getSaveButton().addSelectHandler(new SelectHandler() {
        @Override
        public void onSelect(SelectEvent event) {
          saveCurrentStock();
        }
      });

      VerticalLayoutContainer verticalLayoutContainer = new VerticalLayoutContainer();
      verticalLayoutContainer.add(stockListGrid, new VerticalLayoutData(1, 1));
      verticalLayoutContainer.add(stockEditor, new VerticalLayoutData(1, -1, new Margins(10)));

      panel = new ContentPanel();
      panel.setHeading("Grid Data Binding");
      panel.add(verticalLayoutContainer);

      itemDriver.initialize(stockEditor);
      driver.initialize(this);
    }

    driver.edit(new StockExchange());

    return panel;
  }

  protected void edit(Stock stock) {
    itemDriver.edit(stock);
    stockEditor.setSaveEnabled(true);
  }

  protected void saveCurrentStock() {
    Stock edited = itemDriver.flush();
    if (!itemDriver.hasErrors()) {
      stockEditor.setSaveEnabled(false);

      stockListGrid.getStore().update(edited);
    }
  }

  @Override
  public void onModuleLoad() {
    new ExampleContainer(this)
        .setMinHeight(MIN_HEIGHT)
        .setMinWidth(MIN_WIDTH)
        .doStandalone();
  }

}
