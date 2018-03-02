/**
 * Sencha GXT 1.0.0-SNAPSHOT - Sencha for GWT
 * Copyright (c) 2006-2018, Sencha Inc.
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
package com.sencha.gxt.explorer.client.toolbar;

import java.util.Date;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.DateTimeFormat.PredefinedFormat;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiFactory;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.cell.core.client.form.ComboBoxCell.TriggerAction;
import com.sencha.gxt.data.shared.LabelProvider;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;
import com.sencha.gxt.examples.resources.client.TestData;
import com.sencha.gxt.examples.resources.client.model.Stock;
import com.sencha.gxt.explorer.client.app.ui.ExampleContainer;
import com.sencha.gxt.explorer.client.model.Example.Detail;
import com.sencha.gxt.widget.core.client.form.ComboBox;
import com.sencha.gxt.widget.core.client.info.Info;
import com.sencha.gxt.widget.core.client.menu.ColorMenu;
import com.sencha.gxt.widget.core.client.menu.DateMenu;
import com.sencha.gxt.widget.core.client.menu.Item;
import com.sencha.gxt.widget.core.client.menu.Menu;
import com.sencha.gxt.widget.core.client.menu.MenuItem;

@Detail(
    name = "Basic Tool Bar (UiBinder)",
    category = "Tool Bar & Menu",
    icon = "basictoolbaruibinder",
    classes = { Stock.class, TestData.class },
    files = "ToolBarUiBinderExample.ui.xml",
    minHeight = ToolBarUiBinderExample.MIN_HEIGHT,
    minWidth = ToolBarUiBinderExample.MIN_WIDTH
)
public class ToolBarUiBinderExample implements IsWidget, EntryPoint {

  interface StockProperties extends PropertyAccess<Stock> {
    ModelKeyProvider<Stock> id();

    LabelProvider<Stock> name();
  }

  interface MyUiBinder extends UiBinder<Widget, ToolBarUiBinderExample> {
  }

  protected static final int MIN_HEIGHT = 150;
  protected static final int MIN_WIDTH = 480;

  private static MyUiBinder uiBinder = GWT.create(MyUiBinder.class);

  @UiField
  Menu scrollMenu;
  @UiField
  DateMenu dateMenu;
  @UiField
  ColorMenu colorMenu;
  @UiField
  Menu radioMenu;

  StockProperties properties = GWT.create(StockProperties.class);

  private Widget component;

  @Override
  public Widget asWidget() {
    if (component == null) {
      SelectionHandler<Item> handler = new SelectionHandler<Item>() {
        @Override
        public void onSelection(SelectionEvent<Item> event) {
          MenuItem item = (MenuItem) event.getSelectedItem();

          SafeHtmlBuilder message = new SafeHtmlBuilder();
          message.appendHtmlConstant("You selected the ");
          message.append(item.getSafeHtml());
          Info.display(SafeHtmlUtils.fromSafeConstant("Action"), message.toSafeHtml());
        }
      };
      
      component = uiBinder.createAndBindUi(this);

      for (int i = 0; i < 40; i++) {
        scrollMenu.add(new MenuItem("Item " + i));
      }

      dateMenu.getDatePicker().addValueChangeHandler(new ValueChangeHandler<Date>() {
        @Override
        public void onValueChange(ValueChangeEvent<Date> event) {
          Date d = event.getValue();
          DateTimeFormat f = DateTimeFormat.getFormat(PredefinedFormat.DATE_SHORT);
          Info.display("Value Changed", "You selected " + f.format(d));
          dateMenu.hide(true);
        }
      });

      colorMenu.getPalette().addValueChangeHandler(new ValueChangeHandler<String>() {
        @Override
        public void onValueChange(ValueChangeEvent<String> event) {
          String color = event.getValue();
          Info.display("Color Changed", "You selected " + color);
          colorMenu.hide(true);
        }
      });
      
      radioMenu.addSelectionHandler(handler);
    }

    return component;
  }

  @UiFactory
  public ComboBox<Stock> createComboBox() {
    ListStore<Stock> store = new ListStore<Stock>(properties.id());
    store.addAll(TestData.getStocks());

    ComboBox<Stock> combo = new ComboBox<Stock>(store, properties.name());
    combo.setName("name");
    combo.setForceSelection(true);
    combo.setTriggerAction(TriggerAction.ALL);

    return combo;
  }

  @UiHandler({"menu1", "splitButtonMenu", "scrollMenu"})
  public void onMenuSelection(SelectionEvent<Item> event) {
    MenuItem item = (MenuItem) event.getSelectedItem();

    SafeHtmlBuilder message = new SafeHtmlBuilder();
    message.appendHtmlConstant("You selected the ");
    message.append(item.getSafeHtml());
    Info.display(SafeHtmlUtils.fromSafeConstant("Action"), message.toSafeHtml());
  }

  @Override
  public void onModuleLoad() {
    new ExampleContainer(this)
        .setMinHeight(MIN_HEIGHT)
        .setMinWidth(MIN_WIDTH)
        .doStandalone();
  }

}
