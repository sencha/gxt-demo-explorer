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
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.examples.resources.client.ExampleStyles;
import com.sencha.gxt.examples.resources.client.TestData;
import com.sencha.gxt.examples.resources.client.model.Stock;
import com.sencha.gxt.examples.resources.client.model.StockProperties;
import com.sencha.gxt.explorer.client.app.ui.ExampleContainer;
import com.sencha.gxt.explorer.client.model.Example.Detail;
import com.sencha.gxt.widget.core.client.Portlet;
import com.sencha.gxt.widget.core.client.button.ToolButton;
import com.sencha.gxt.widget.core.client.container.PortalLayoutContainer;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.grid.ColumnConfig;
import com.sencha.gxt.widget.core.client.grid.ColumnModel;
import com.sencha.gxt.widget.core.client.grid.Grid;
import com.sencha.gxt.widget.core.client.tips.QuickTip;

@Detail(
    name = "Portal Layout",
    category = "Layouts",
    icon = "portallayout",
    classes = {
        Stock.class,
        StockProperties.class, 
        TestData.class
    },
    minHeight = PortalLayoutContainerExample.MIN_HEIGHT,
    minWidth = PortalLayoutContainerExample.MIN_WIDTH,
    preferredHeight = PortalLayoutContainerExample.PREFERRED_HEIGHT,
    preferredWidth = PortalLayoutContainerExample.PREFERRED_WIDTH
)
public class PortalLayoutContainerExample implements IsWidget, EntryPoint {

  protected static final int MIN_HEIGHT = 1;
  protected static final int MIN_WIDTH = 1280;
  protected static final int PREFERRED_HEIGHT = 1;
  protected static final int PREFERRED_WIDTH = 1;

  private static final StockProperties properties = GWT.create(StockProperties.class);

  private PortalLayoutContainer portal;

  @Override
  public Widget asWidget() {
    if (portal == null) {
      Portlet portlet1 = new Portlet();
      portlet1.setHeading("Portal Layout — Panel 1 with Grid");
      portlet1.add(createGrid());
      portlet1.setHeight(250);
      configPanel(portlet1);

      Portlet portlet2 = new Portlet();
      portlet2.setHeading("Portal Layout — Panel 2");
      portlet2.add(getBogusText());
      configPanel(portlet2);

      Portlet portlet3 = new Portlet();
      portlet3.setHeading("Portal Layout — Panel 3");
      portlet3.add(getBogusText());
      configPanel(portlet3);

      Portlet portlet4 = new Portlet();
      portlet4.setHeading("Portal Layout — Panel 4");
      portlet4.add(getBogusText());
      configPanel(portlet4);

      portal = new PortalLayoutContainer(3);
      portal.setSpacing(20);
      portal.setColumnWidth(0, .40);
      portal.setColumnWidth(1, .30);
      portal.setColumnWidth(2, .30);
      portal.add(portlet1, 0);
      portal.add(portlet2, 0);
      portal.add(portlet3, 1);
      portal.add(portlet4, 1);
    }

    return portal;
  }

  public Widget createGrid() {
    final NumberFormat number = NumberFormat.getFormat("0.00");
    
    ColumnConfig<Stock, String> nameCol = new ColumnConfig<Stock, String>(properties.name(), 200, "Company");
    ColumnConfig<Stock, String> symbolCol = new ColumnConfig<Stock, String>(properties.symbol(), 75, "Symbol");
    ColumnConfig<Stock, Double> lastCol = new ColumnConfig<Stock, Double>(properties.last(), 75, "Last");
    ColumnConfig<Stock, Double> changeCol = new ColumnConfig<Stock, Double>(properties.change(), 75, "Change");
    ColumnConfig<Stock, Date> lastTransCol = new ColumnConfig<Stock, Date>(properties.lastTrans(), 100, "Last Updated");
    
    changeCol.setCell(new AbstractCell<Double>() {
      @Override
      public void render(Context context, Double value, SafeHtmlBuilder sb) {
        String style = "style='color: " + (value < 0 ? "red" : "green") + "'";
        String v = number.format(value);
        // The quicktip has to initialized to use the quick tips in this element. 
        sb.appendHtmlConstant("<span " + style + " qtitle='Change' qtip='" + v + "'>" + v + "</span>");
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
    grid.getView().setStripeRows(true);
    grid.getView().setColumnLines(true);

    // This is needed to enable quicktips to be displayed in the grid.  
    // See the cell renderer HTML above with qtip attribute.
    QuickTip.of(grid);

    return grid;
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

  private void configPanel(final Portlet portlet) {
    portlet.setCollapsible(true);
    portlet.setAnimCollapse(false);
    portlet.getHeader().addTool(new ToolButton(ToolButton.GEAR));
    portlet.getHeader().addTool(new ToolButton(ToolButton.CLOSE, new SelectHandler() {
      @Override
      public void onSelect(SelectEvent event) {
        portlet.removeFromParent();
      }
    }));
  }

  private HTML getBogusText() {
    HTML html = new HTML(TestData.DUMMY_TEXT_SHORT);
    html.addStyleName(ExampleStyles.get().paddedText());

    return html;
  }

}
