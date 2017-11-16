package com.sencha.gxt.explorer.client.grid;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.cell.client.DateCell;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.cell.core.client.form.ComboBoxCell.TriggerAction;
import com.sencha.gxt.core.client.IdentityValueProvider;
import com.sencha.gxt.core.client.Style.SelectionMode;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.data.shared.StringLabelProvider;
import com.sencha.gxt.examples.resources.client.TestData;
import com.sencha.gxt.examples.resources.client.model.Stock;
import com.sencha.gxt.examples.resources.client.model.StockProperties;
import com.sencha.gxt.explorer.client.app.ui.ExampleContainer;
import com.sencha.gxt.explorer.client.model.Example.Detail;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;
import com.sencha.gxt.widget.core.client.form.SimpleComboBox;
import com.sencha.gxt.widget.core.client.grid.CheckBoxSelectionModel;
import com.sencha.gxt.widget.core.client.grid.ColumnConfig;
import com.sencha.gxt.widget.core.client.grid.ColumnModel;
import com.sencha.gxt.widget.core.client.grid.Grid;
import com.sencha.gxt.widget.core.client.toolbar.LabelToolItem;
import com.sencha.gxt.widget.core.client.toolbar.ToolBar;

@Detail(
    name = "Check Box Grid",
    category = "Grid",
    icon = "checkboxgrid",
    classes = {
        Stock.class,
        StockProperties.class,
        TestData.class
    },
    maxHeight = CheckBoxGridExample.MAX_HEIGHT,
    maxWidth = CheckBoxGridExample.MAX_WIDTH,
    minHeight = CheckBoxGridExample.MIN_HEIGHT,
    minWidth = CheckBoxGridExample.MIN_WIDTH
)
public class CheckBoxGridExample implements IsWidget, EntryPoint {

  protected static final int MAX_HEIGHT = 600;
  protected static final int MAX_WIDTH = 800;
  protected static final int MIN_HEIGHT = 320;
  protected static final int MIN_WIDTH = 480;

  private static final StockProperties props = GWT.create(StockProperties.class);

  private ContentPanel panel;

  @Override
  public Widget asWidget() {
    if (panel == null) {
      IdentityValueProvider<Stock> identity = new IdentityValueProvider<Stock>();
      final CheckBoxSelectionModel<Stock> selectionModel = new CheckBoxSelectionModel<Stock>(identity);

      ColumnConfig<Stock, String> nameCol = new ColumnConfig<Stock, String>(props.name(), 200, "Company");
      ColumnConfig<Stock, String> symbolCol = new ColumnConfig<Stock, String>(props.symbol(), 75, "Symbol");
      ColumnConfig<Stock, Double> lastCol = new ColumnConfig<Stock, Double>(props.last(), 75, "Last");
      ColumnConfig<Stock, Double> changeCol = new ColumnConfig<Stock, Double>(props.change(), 75, "Change");
      ColumnConfig<Stock, Date> lastTransCol = new ColumnConfig<Stock, Date>(props.lastTrans(), 100, "Last Updated");

      final NumberFormat number = NumberFormat.getFormat("0.00");
      changeCol.setCell(new AbstractCell<Double>() {
        @Override
        public void render(Context context, Double value, SafeHtmlBuilder sb) {
          String style = "style='color: " + (value < 0 ? "red" : "green") + "'";
          String v = number.format(value);
          sb.appendHtmlConstant("<span " + style + "" + v + ">" + v + "</span>");
        }
      });

      lastTransCol.setCell(new DateCell(DateTimeFormat.getFormat("MM/dd/yyyy")));

      List<ColumnConfig<Stock, ?>> columns = new ArrayList<ColumnConfig<Stock, ?>>();
      // The first column is the SelectionModel Column
      columns.add(selectionModel.getColumn());
      columns.add(nameCol);
      columns.add(symbolCol);
      columns.add(lastCol);
      columns.add(changeCol);
      columns.add(lastTransCol);

      ColumnModel<Stock> cm = new ColumnModel<Stock>(columns);

      ListStore<Stock> store = new ListStore<Stock>(props.key());
      store.addAll(TestData.getStocks());

      final Grid<Stock> grid = new Grid<Stock>(store, cm);
      grid.setSelectionModel(selectionModel);
      grid.getView().setAutoExpandColumn(nameCol);
      grid.setBorders(false);
      grid.getView().setStripeRows(true);
      grid.getView().setColumnLines(true);

      SimpleComboBox<String> typeCombo = new SimpleComboBox<String>(new StringLabelProvider<String>());
      typeCombo.setTriggerAction(TriggerAction.ALL);
      typeCombo.setEditable(false);
      typeCombo.setWidth(100);
      typeCombo.add("Multi");
      typeCombo.add("Simple");
      typeCombo.setValue("Multi");
      typeCombo.addSelectionHandler(new SelectionHandler<String>() {
        @Override
        public void onSelection(SelectionEvent<String> event) {
          boolean simple = event.getSelectedItem().equals("Simple");
          selectionModel.deselectAll();
          selectionModel.setSelectionMode(simple ? SelectionMode.SIMPLE : SelectionMode.MULTI);
        }
      });

      ToolBar toolBar = new ToolBar();
      toolBar.add(new LabelToolItem("Selection Mode: "));
      toolBar.add(typeCombo);

      VerticalLayoutContainer verticalLayoutContainer = new VerticalLayoutContainer();
      verticalLayoutContainer.add(toolBar, new VerticalLayoutData(1, -1));
      verticalLayoutContainer.add(grid, new VerticalLayoutData(1, 1));

      panel = new ContentPanel();
      panel.setHeading("Check Box Grid");
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
