package com.sencha.gxt.explorer.client.toolbar;

import java.util.Date;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.DateTimeFormat.PredefinedFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.explorer.client.app.ui.ExampleContainer;
import com.sencha.gxt.explorer.client.model.Example.Detail;
import com.sencha.gxt.widget.core.client.info.Info;
import com.sencha.gxt.widget.core.client.menu.DateMenu;
import com.sencha.gxt.widget.core.client.menu.Item;
import com.sencha.gxt.widget.core.client.menu.MenuItem;

@Detail(
    name = "Menu Bar (UiBinder)",
    category = "Tool Bar & Menu",
    icon = "menubaruibinder",
    files = "MenuBarUiBinderExample.ui.xml",
    minHeight = MenuBarUiBinderExample.MIN_HEIGHT,
    minWidth = MenuBarUiBinderExample.MIN_WIDTH
)
public class MenuBarUiBinderExample implements IsWidget, EntryPoint {

  interface MyUiBinder extends UiBinder<Widget, MenuBarUiBinderExample> {
  }

  protected static final int MIN_HEIGHT = 150;
  protected static final int MIN_WIDTH = 240;

  private static MyUiBinder uiBinder = GWT.create(MyUiBinder.class);

  @UiField
  DateMenu dateMenu;

  private Widget widget;

  @Override
  public Widget asWidget() {
    if (widget == null) {
      widget = uiBinder.createAndBindUi(this);
    }

    return widget;
  }

  @UiHandler(value = {"menuNew", "menuOpen", "menuEdit", "menuSearch", "menuFoo", "menuTheme"})
  public void onMenuSelection(SelectionEvent<Item> event) {
    MenuItem item = (MenuItem) event.getSelectedItem();
    Info.display("Action", "You selected the " + item.getText());
  }

  @UiHandler("dateMenu")
  public void onDateSelect(ValueChangeEvent<Date> event) {
    Date date = event.getValue();
    DateTimeFormat f = DateTimeFormat.getFormat(PredefinedFormat.DATE_SHORT);
    Info.display("Value Changed", "You selected " + f.format(date));
    dateMenu.hide(true);
  }

  @Override
  public void onModuleLoad() {
    new ExampleContainer(this)
        .setMinHeight(MIN_HEIGHT)
        .setMinWidth(MIN_WIDTH)
        .doStandalone();
  }

}
