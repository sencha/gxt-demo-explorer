package com.sencha.gxt.explorer.client.layout;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiFactory;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.core.client.GXT;
import com.sencha.gxt.core.client.Style.LayoutRegion;
import com.sencha.gxt.examples.resources.client.Utils;
import com.sencha.gxt.examples.resources.client.Utils.Theme;
import com.sencha.gxt.explorer.client.app.ui.ExampleContainer;
import com.sencha.gxt.explorer.client.model.Example.Detail;
import com.sencha.gxt.state.client.BorderLayoutStateHandler;
import com.sencha.gxt.state.client.CookieProvider;
import com.sencha.gxt.state.client.StateManager;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;

@Detail(
    name = "Border Layout (UiBinder)",
    category = "Layouts",
    icon = "borderlayoutuibinder",
    files = "BorderLayoutUiBinderExample.ui.xml",
    minHeight = BorderLayoutUiBinderExample.MIN_HEIGHT,
    minWidth = BorderLayoutUiBinderExample.MIN_WIDTH,
    classes = { Utils.class }
)
public class BorderLayoutUiBinderExample implements IsWidget, EntryPoint {

  protected static final int MIN_HEIGHT = 480;
  protected static final int MIN_WIDTH = 720;

  private static MyUiBinder uiBinder = GWT.create(MyUiBinder.class);

  interface MyUiBinder extends UiBinder<BorderLayoutContainer, BorderLayoutUiBinderExample> {
  }

  @UiField(provided = true)
  final Boolean borders = Theme.TRITON.isActive() ? false : true;
  @UiField(provided = true)
  final Integer margins = Theme.TRITON.isActive() ? 10 : Theme.NEPTUNE.isActive() ? 8 : 5;


  private BorderLayoutContainer widget;
  
  /**
   * Persist the state of the border layout
   */
  private BorderLayoutStateHandler stateHandler;

  @Override
  public Widget asWidget() {
    if (widget == null) {
      widget = uiBinder.createAndBindUi(this);
      
      // 2. Persist the state of the border layout container
      stateHandler = new BorderLayoutStateHandler(widget, "blcId2");
      // Tell the widget to persist the state
      widget.setStateful(true);
    }
    
    // 3. load the previous
    stateHandler.loadState();

    return widget;
  }

  @UiFactory
  public FlexTable createFlexTable() {
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
            widget.expand(r);
          } else if (txt.equals("Collapse")) {
            widget.collapse(r);
          } else if (txt.equals("Show")) {
            widget.show(r);
          } else {
            widget.hide(r);
          }
        }
      };

      table.setHTML(i, 0, "<div style='font-size: 12px; width: 100px'>" + r.name() + ":</span>");
      table.setWidget(i, 1, new TextButton("Expand", handler));
      table.setWidget(i, 2, new TextButton("Collapse", handler));
      table.setWidget(i, 3, new TextButton("Show", handler));
      table.setWidget(i, 4, new TextButton("Hide", handler));
    }

    return table;
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
