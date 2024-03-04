/**
 * Sencha GXT 1.0.0-SNAPSHOT - Sencha for GWT
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
package com.sencha.gxt.explorer.client.layout;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.cell.client.DateCell;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.safecss.shared.SafeStyles;
import com.google.gwt.safecss.shared.SafeStylesBuilder;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiFactory;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.core.client.XTemplates;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.examples.resources.client.TestData;
import com.sencha.gxt.examples.resources.client.model.Stock;
import com.sencha.gxt.examples.resources.client.model.StockProperties;
import com.sencha.gxt.explorer.client.app.ui.ExampleContainer;
import com.sencha.gxt.explorer.client.model.Example.Detail;
import com.sencha.gxt.widget.core.client.Portlet;
import com.sencha.gxt.widget.core.client.button.IconButton.IconConfig;
import com.sencha.gxt.widget.core.client.button.ToolButton;
import com.sencha.gxt.widget.core.client.container.PortalLayoutContainer;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.grid.ColumnConfig;
import com.sencha.gxt.widget.core.client.grid.ColumnModel;
import com.sencha.gxt.widget.core.client.grid.Grid;
import com.sencha.gxt.widget.core.client.tips.QuickTip;

@Detail(
    name = "Portal Layout (UiBinder)",
    category = "Layouts",
    icon = "portallayoutuibinder",
    classes = {
        Stock.class,
        StockProperties.class,
        TestData.class
    },
    files = "PortalLayoutContainerUiBinderExample.ui.xml",
    minHeight = PortalLayoutContainerUiBinderExample.MIN_HEIGHT,
    minWidth = PortalLayoutContainerUiBinderExample.MIN_WIDTH,
    preferredHeight = PortalLayoutContainerUiBinderExample.PREFERRED_HEIGHT,
    preferredWidth = PortalLayoutContainerUiBinderExample.PREFERRED_WIDTH
)
public class PortalLayoutContainerUiBinderExample implements IsWidget, EntryPoint {

  protected static final int MIN_HEIGHT = 1;
  protected static final int MIN_WIDTH = 1280;
  protected static final int PREFERRED_HEIGHT = 1;
  protected static final int PREFERRED_WIDTH = 1;

  private static final StockProperties properties = GWT.create(StockProperties.class);

  private static MyUiBinder uiBinder = GWT.create(MyUiBinder.class);

  enum ToolConfig {
    GEAR(ToolButton.GEAR), CLOSE(ToolButton.CLOSE);

    private IconConfig config;

    private ToolConfig(IconConfig config) {
      this.config = config;
    }
  }

  interface CellTemplates extends XTemplates {
    @XTemplate("<span style='{styles}' qtitle='Change' qtip='{qtip}'>{value}</span>")
    SafeHtml template(SafeStyles styles, String qtip, String value);
  }

  interface MyUiBinder extends UiBinder<Widget, PortalLayoutContainerUiBinderExample> {
  }

  @UiField
  PortalLayoutContainer portal;
  @UiField(provided = true)
  String txt = TestData.DUMMY_TEXT_SHORT;

  @Override
  public Widget asWidget() {
    if (portal == null) {
      uiBinder.createAndBindUi(this);

      portal.setColumnWidth(0, .40);
      portal.setColumnWidth(1, .30);
      portal.setColumnWidth(2, .30);
    }

    return portal;
  }

  @UiFactory()
  public Grid<Stock> createGrid() {
    final NumberFormat number = NumberFormat.getFormat("0.00");

    ColumnConfig<Stock, String> nameCol = new ColumnConfig<Stock, String>(properties.name(), 200, "Company");
    ColumnConfig<Stock, String> symbolCol = new ColumnConfig<Stock, String>(properties.symbol(), 75, "Symbol");
    ColumnConfig<Stock, Double> lastCol = new ColumnConfig<Stock, Double>(properties.last(), 75, "Last");
    ColumnConfig<Stock, Double> changeCol = new ColumnConfig<Stock, Double>(properties.change(), 75, "Change");
    ColumnConfig<Stock, Date> lastTransCol = new ColumnConfig<Stock, Date>(properties.lastTrans(), 100, "Last Updated");

    changeCol.setCell(new AbstractCell<Double>() {
      @Override
      public void render(Context context, Double value, SafeHtmlBuilder sb) {
        SafeStylesBuilder stylesBuilder = new SafeStylesBuilder();
        stylesBuilder.appendTrustedString("color:" + (value < 0 ? "red" : "green") + ";");
        String v = number.format(value);
        CellTemplates cellTemplates = GWT.create(CellTemplates.class);
        SafeHtml template = cellTemplates.template(stylesBuilder.toSafeStyles(), v, v);
        sb.append(template);
      }
    });

    lastTransCol.setCell(new DateCell(DateTimeFormat.getFormat("MM/dd/yyyy")));

    List<ColumnConfig<Stock, ?>> columns = new ArrayList<ColumnConfig<Stock, ?>>();
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
    grid.getView().setForceFit(true);
    grid.setBorders(false);
    grid.getView().setStripeRows(true);
    grid.getView().setColumnLines(true);

    // needed to enable quicktips (qtitle for the heading and qtip for the
    // content) that are setup in the change GridCellRenderer
    new QuickTip(grid);

    return grid;
  }

  @UiFactory
  protected ToolButton createToolButton(ToolConfig icon, Portlet portlet) {
    ToolButton toolButton = new ToolButton(icon.config);
    toolButton.setData("portlet", portlet);

    return toolButton;
  }

  @UiHandler({"portlet1Close", "portlet2Close", "portlet3Close", "portlet4Close"})
  protected void onClosePortlet(SelectEvent event) {
    ToolButton tool = (ToolButton) event.getSource();
    Portlet portlet = tool.getData("portlet");
    portlet.removeFromParent();
  }

  @Override
  public void onModuleLoad() {
    new ExampleContainer(this)
        .setMinHeight(MIN_HEIGHT)
        .setMinWidth(MIN_WIDTH)
        .setPreferredHeight(PREFERRED_HEIGHT)
        .setPreferredWidth(PREFERRED_WIDTH)
        .doStandalone();
  }

}
