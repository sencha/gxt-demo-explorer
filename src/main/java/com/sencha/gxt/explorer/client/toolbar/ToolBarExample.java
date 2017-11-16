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
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.cell.core.client.form.ComboBoxCell.TriggerAction;
import com.sencha.gxt.data.shared.LabelProvider;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;
import com.sencha.gxt.examples.resources.client.Resources;
import com.sencha.gxt.examples.resources.client.TestData;
import com.sencha.gxt.examples.resources.client.model.Stock;
import com.sencha.gxt.explorer.client.app.ui.ExampleContainer;
import com.sencha.gxt.explorer.client.model.Example.Detail;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.button.SplitButton;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.button.ToggleButton;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;
import com.sencha.gxt.widget.core.client.form.ComboBox;
import com.sencha.gxt.widget.core.client.info.Info;
import com.sencha.gxt.widget.core.client.menu.CheckMenuItem;
import com.sencha.gxt.widget.core.client.menu.ColorMenu;
import com.sencha.gxt.widget.core.client.menu.DateMenu;
import com.sencha.gxt.widget.core.client.menu.Item;
import com.sencha.gxt.widget.core.client.menu.Menu;
import com.sencha.gxt.widget.core.client.menu.MenuItem;
import com.sencha.gxt.widget.core.client.menu.SeparatorMenuItem;
import com.sencha.gxt.widget.core.client.toolbar.FillToolItem;
import com.sencha.gxt.widget.core.client.toolbar.ToolBar;

@Detail(
    name = "Basic Tool Bar",
    category = "Tool Bar & Menu",
    icon = "basictoolbar",
    classes = { Stock.class, TestData.class },
    minHeight = ToolBarExample.MIN_HEIGHT,
    minWidth = ToolBarExample.MIN_WIDTH
)
public class ToolBarExample implements IsWidget, EntryPoint {

  interface StockProperties extends PropertyAccess<Stock> {
    ModelKeyProvider<Stock> id();

    LabelProvider<Stock> name();
  }

  protected static final int MIN_HEIGHT = 150;
  protected static final int MIN_WIDTH = 480;

  private ContentPanel panel;

  @Override
  public Widget asWidget() {
    if (panel == null) {
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

      StockProperties properties = GWT.create(StockProperties.class);

      ListStore<Stock> store = new ListStore<Stock>(properties.id());
      store.addAll(TestData.getStocks());

      final ComboBox<Stock> combo = new ComboBox<Stock>(store, properties.name());
      combo.setName("name");
      combo.setForceSelection(true);
      combo.setStore(store);
      combo.setTriggerAction(TriggerAction.ALL);

      CheckMenuItem menuItem1 = new CheckMenuItem("I Like Cats");
      menuItem1.setChecked(true);

      CheckMenuItem menuItem2 = new CheckMenuItem("I Like Dogs");

      CheckMenuItem menuItemRadios = new CheckMenuItem("Blue Theme");
      menuItemRadios.setGroup("radios");
      menuItemRadios.setChecked(true);

      CheckMenuItem menuItemGray = new CheckMenuItem("Gray Theme");
      menuItemGray.setGroup("radios");

      Menu radioMenu = new Menu();
      radioMenu.addSelectionHandler(handler);
      radioMenu.add(menuItemRadios);
      radioMenu.add(menuItemGray);

      MenuItem radios = new MenuItem("Radio Options");
      radios.setSubMenu(radioMenu);

      final DateMenu dateMenu = new DateMenu();
      dateMenu.getDatePicker().addValueChangeHandler(new ValueChangeHandler<Date>() {
        @Override
        public void onValueChange(ValueChangeEvent<Date> event) {
          Date d = event.getValue();
          DateTimeFormat f = DateTimeFormat.getFormat(PredefinedFormat.DATE_SHORT);
          Info.display("Value Changed", "You selected " + f.format(d));
          dateMenu.hide(true);
        }
      });

      MenuItem date = new MenuItem("Choose a Date");
      date.setIcon(Resources.IMAGES.calendar());
      date.setSubMenu(dateMenu);

      final ColorMenu colorMenu = new ColorMenu();
      colorMenu.getPalette().addValueChangeHandler(new ValueChangeHandler<String>() {
        @Override
        public void onValueChange(ValueChangeEvent<String> event) {
          String color = event.getValue();
          Info.display("Color Changed", "You selected " + color);
          colorMenu.hide(true);
        }
      });

      MenuItem color = new MenuItem("Choose a Color");
      color.setSubMenu(colorMenu);

      Menu menu1 = new Menu();
      menu1.addSelectionHandler(handler);
      menu1.add(combo);
      menu1.add(menuItem1);
      menu1.add(menuItem2);
      menu1.add(new SeparatorMenuItem());
      menu1.add(radios);
      menu1.add(date);
      menu1.add(color);

      TextButton button1 = new TextButton("Button w/ Menu");
      button1.setIcon(Resources.IMAGES.menu_show());
      button1.setMenu(menu1);

      MenuItem item1 = new MenuItem();
      item1.setHTML(SafeHtmlUtils.fromSafeConstant("<b>Bold</b>"));

      MenuItem item2 = new MenuItem();
      item2.setHTML(SafeHtmlUtils.fromSafeConstant("<i>Italic</i>"));

      MenuItem item3 = new MenuItem();
      item3.setHTML(SafeHtmlUtils.fromSafeConstant("<u>Underline</u>"));

      Menu menu2 = new Menu();
      menu2.addSelectionHandler(handler);
      menu2.add(item1);
      menu2.add(item2);
      menu2.add(item3);

      SplitButton splitItem = new SplitButton("Split Button");
      splitItem.setIcon(Resources.IMAGES.list_items());
      splitItem.setMenu(menu2);

      ToggleButton toggle = new ToggleButton("Toggle");
      toggle.setValue(true);

      Menu scrollMenu = new Menu();
      scrollMenu.addSelectionHandler(handler);
      scrollMenu.setMaxHeight(200);
      for (int i = 0; i < 40; i++) {
        scrollMenu.add(new MenuItem("Item " + i));
      }

      TextButton scrollerButton = new TextButton("Scrolling Menu");
      scrollerButton.setMenu(scrollMenu);

      ToolBar toolBar = new ToolBar();
      toolBar.add(button1);
      toolBar.add(splitItem);
      toolBar.add(toggle);
      toolBar.add(scrollerButton);
      toolBar.add(new FillToolItem());

      VerticalLayoutContainer vlc = new VerticalLayoutContainer();
      vlc.add(toolBar, new VerticalLayoutData(1, -1));

      panel = new ContentPanel();
      panel.setHeading("Basic Tool Bar");
      panel.add(vlc);
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
