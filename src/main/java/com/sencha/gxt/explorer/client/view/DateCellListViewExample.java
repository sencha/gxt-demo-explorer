package com.sencha.gxt.explorer.client.view;

import java.util.Date;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor.Path;
import com.google.gwt.i18n.shared.DateTimeFormat;
import com.google.gwt.i18n.shared.DateTimeFormat.PredefinedFormat;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.cell.core.client.form.DateCell;
import com.sencha.gxt.core.client.Style.SelectionMode;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;
import com.sencha.gxt.examples.resources.client.TestData;
import com.sencha.gxt.examples.resources.client.model.Stock;
import com.sencha.gxt.explorer.client.app.ui.ExampleContainer;
import com.sencha.gxt.explorer.client.model.Example.Detail;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.ListView;
import com.sencha.gxt.widget.core.client.form.DateTimePropertyEditor;

@Detail(
    name = "Date Cell List View",
    category = "Templates & Lists",
    icon = "datecelllistview",
    classes = { Stock.class, TestData.class },
    minHeight = DateCellListViewExample.MIN_HEIGHT,
    minWidth = DateCellListViewExample.MIN_WIDTH
)
public class DateCellListViewExample implements EntryPoint, IsWidget {

  interface StockProperties extends PropertyAccess<Stock> {
    @Path("id")
    ModelKeyProvider<Stock> key();

    @Path("lastTrans")
    ValueProvider<Stock, Date> date();
  }

  protected static final int MIN_HEIGHT = 320;
  protected static final int MIN_WIDTH = 320;

  private ContentPanel panel;

  @Override
  public Widget asWidget() {
    if (panel == null) {
      final StockProperties properties = GWT.create(StockProperties.class);

      DateCell cell = new DateCell();
      cell.setPropertyEditor(new DateTimePropertyEditor(DateTimeFormat.getFormat(PredefinedFormat.DATE_SHORT)));

      ListView<Stock, Date> stockList = new ListView<Stock, Date>(new ListStore<Stock>(properties.key()), properties.date());
      stockList.setCell(cell);
      stockList.getSelectionModel().setSelectionMode(SelectionMode.SIMPLE);
      stockList.getSelectionModel().setLocked(true);
      stockList.getStore().addAll(TestData.getStocks());

      panel = new ContentPanel();
      panel.setHeading("Date Cell List View");
      panel.setBodyBorder(false);
      panel.add(stockList);
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

}
