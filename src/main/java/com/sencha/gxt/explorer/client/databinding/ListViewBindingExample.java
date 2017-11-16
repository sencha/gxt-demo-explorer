package com.sencha.gxt.explorer.client.databinding;

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
import com.sencha.gxt.explorer.client.databinding.ListViewBindingExample.StockExchange;
import com.sencha.gxt.explorer.client.model.Example.Detail;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.ListView;
import com.sencha.gxt.widget.core.client.container.MarginData;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.selection.SelectionChangedEvent;
import com.sencha.gxt.widget.core.client.selection.SelectionChangedEvent.SelectionChangedHandler;

/**
 * Demonstrates using the ListStoreEditor, and some concept of building multiple
 * editors. Note that as currently written, when a Stock object is saved, it
 * will modify the StockExchange's instances, instead of cloning models before
 * editing them.
 */
@Detail(
    name = "List View Binding",
    category = "Data Binding",
    icon = "listviewbinding",
    classes = {
        Stock.class,
        StockProperties.class,
        StockEditor.class,
        TestData.class
    },
    minHeight = ListViewBindingExample.MIN_HEIGHT,
    minWidth = ListViewBindingExample.MIN_WIDTH
)
public class ListViewBindingExample implements EntryPoint, IsWidget, Editor<StockExchange> {

  public static class StockExchange {
    private List<Stock> stocks = TestData.getStocks();

    public List<Stock> getStocks() {
      return stocks;
    }

    public void setStocks(List<Stock> stocks) {
      this.stocks = stocks;
    }
  }

  interface ListDriver extends SimpleBeanEditorDriver<StockExchange, ListViewBindingExample> {
  }

  interface StockDriver extends SimpleBeanEditorDriver<Stock, StockEditor> {
  }

  protected static final int MIN_HEIGHT = 325;
  protected static final int MIN_WIDTH = 300;

  private ListDriver driver = GWT.create(ListDriver.class);
  private StockDriver itemDriver = GWT.create(StockDriver.class);
  private ContentPanel panel;

  ListView<Stock, String> stockListView;
  ListStoreEditor<Stock> stocks;
  @Ignore
  StockEditor stockEditor;

  @Override
  public Widget asWidget() {
    if (panel == null) {
      final StockProperties properties = GWT.create(StockProperties.class);

      stockListView = new ListView<Stock, String>(new ListStore<Stock>(properties.key()), properties.name());
      stockListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
      stockListView.getSelectionModel().addSelectionChangedHandler(new SelectionChangedHandler<Stock>() {
        @Override
        public void onSelectionChanged(SelectionChangedEvent<Stock> event) {
          if (event.getSelection().size() > 0) {
            edit(event.getSelection().get(0));
          } else {
            stockEditor.setSaveEnabled(false);
          }
        }
      });

      stocks = new ListStoreEditor<Stock>(stockListView.getStore());

      stockEditor = new StockEditor();
      stockEditor.getSaveButton().addSelectHandler(new SelectHandler() {
        @Override
        public void onSelect(SelectEvent event) {
          saveCurrentStock();

        }
      });

      VerticalLayoutContainer verticalLayoutContainer = new VerticalLayoutContainer();
      verticalLayoutContainer.add(stockListView, new VerticalLayoutData(1, 1));
      verticalLayoutContainer.add(stockEditor, new VerticalLayoutData(1, -1, new Margins(10, 0, 0, 0)));

      panel = new ContentPanel();
      panel.setHeading("List View Binding");
      panel.add(verticalLayoutContainer, new MarginData(10));

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

      stockListView.getStore().update(edited);
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
