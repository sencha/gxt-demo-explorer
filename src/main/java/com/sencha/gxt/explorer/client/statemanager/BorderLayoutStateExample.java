package com.sencha.gxt.explorer.client.statemanager;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.core.client.GXT;
import com.sencha.gxt.core.client.Style.LayoutRegion;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.examples.resources.client.Utils;
import com.sencha.gxt.examples.resources.client.Utils.Theme;
import com.sencha.gxt.explorer.client.app.ui.ExampleContainer;
import com.sencha.gxt.explorer.client.model.Example.Detail;
import com.sencha.gxt.state.client.BorderLayoutStateHandler;
import com.sencha.gxt.state.client.CookieProvider;
import com.sencha.gxt.state.client.StateManager;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer.BorderLayoutData;
import com.sencha.gxt.widget.core.client.container.MarginData;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;

@Detail(
    name = "Border Layout State",
    category = "State Manager",
    icon = "borderlayout",
    minHeight = BorderLayoutStateExample.MIN_HEIGHT,
    minWidth = BorderLayoutStateExample.MIN_WIDTH,
    classes = { Utils.class }
)
public class BorderLayoutStateExample implements IsWidget, EntryPoint {

  protected static final int MIN_HEIGHT = 480;
  protected static final int MIN_WIDTH = 720;

  private BorderLayoutContainer blc;
  
  /**
   * Persist the state of the border layout
   */
  private BorderLayoutStateHandler blcStateHandler;

  @Override
  public Widget asWidget() {
    final boolean borders = Theme.TRITON.isActive() ? false : true;
    final int margins = Theme.TRITON.isActive() ? 10 : Theme.NEPTUNE.isActive() ? 8 : 5;
    
    if (blc == null) {
      FlexTable table = new FlexTable();
      table.getElement().getStyle().setProperty("margin", "10px");
      table.setCellSpacing(8);
      table.setCellPadding(4);

      for (int i = 0; i < LayoutRegion.values().length; i++) {
        final LayoutRegion r = LayoutRegion.values()[i];
        if (r == LayoutRegion.CENTER) {
          continue;
        }

        SelectHandler handler = new SelectHandler() {
          @Override
          public void onSelect(SelectEvent event) {
            TextButton btn = (TextButton) event.getSource();
            String txt = btn.getText();
            if (txt.equals("Expand")) {
              blc.expand(r);
            } else if (txt.equals("Collapse")) {
              blc.collapse(r);
            } else if (txt.equals("Show")) {
              blc.show(r);
            } else {
              blc.hide(r);
            }
          }
        };

        table.setHTML(i, 0, "<div style='font-size: 12px; width: 100px'>" + r.name() + ":</span>");
        table.setWidget(i, 1, new TextButton("Expand", handler));
        table.setWidget(i, 2, new TextButton("Collapse", handler));
        table.setWidget(i, 3, new TextButton("Show", handler));
        table.setWidget(i, 4, new TextButton("Hide", handler));
      }

      ContentPanel center = new ContentPanel();
      center.setHeading("Center");
      center.setResize(false);
      center.add(table);

      ContentPanel north = new ContentPanel();
      north.setHeading("North");
      
      ContentPanel west = new ContentPanel();
      west.setHeading("West");
      
      ContentPanel east = new ContentPanel();
      east.setHeading("East");
      
      ContentPanel south = new ContentPanel();
      south.setHeading("South");

      BorderLayoutData northData = new BorderLayoutData(100);
      northData.setMargins(new Margins(margins));
      northData.setCollapsible(true);
      northData.setCollapseHeaderVisible(true);
      northData.setSplit(true);
      northData.setCollapseMini(true);

      BorderLayoutData westData = new BorderLayoutData(150);
      westData.setMargins(new Margins(0, margins, 0, margins));
      westData.setCollapsible(true);
      westData.setCollapseHeaderVisible(true);
      westData.setSplit(true);
      westData.setCollapseMini(true);

      MarginData centerData = new MarginData();

      BorderLayoutData eastData = new BorderLayoutData(150);
      eastData.setMargins(new Margins(0, margins, 0, margins));
      eastData.setCollapsible(true);
      eastData.setCollapseHeaderVisible(true);
      eastData.setSplit(true);
      eastData.setCollapseMini(true);

      BorderLayoutData southData = new BorderLayoutData(100);
      southData.setMargins(new Margins(margins));
      southData.setCollapsible(true);
      southData.setCollapseHeaderVisible(true);
      southData.setSplit(true);
      southData.setCollapseMini(true);

      blc = new BorderLayoutContainer();
      blc.setBorders(borders);
      blc.setNorthWidget(north, northData);
      blc.setWestWidget(west, westData);
      blc.setCenterWidget(center, centerData);
      blc.setEastWidget(east, eastData);
      blc.setSouthWidget(south, southData);
      
      // 2. Persist the state of the border layout container
      blcStateHandler = new BorderLayoutStateHandler(blc, "blcId1");
      blc.setStateful(true);
      blc.setStateId("blcStateExample");
    }
    
    // 3. load the previous
    blcStateHandler.loadState();

    return blc;
  }

  @Override
  public void onModuleLoad() {
    // 1. State manager, initialize the state options
    StateManager.get().setProvider(new CookieProvider("/", null, null, GXT.isSecure()));
    
    new ExampleContainer(this)
        .setMinHeight(MIN_HEIGHT)
        .setMinWidth(MIN_WIDTH)
        .doStandalone();
  }

}
