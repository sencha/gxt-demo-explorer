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
package com.sencha.gxt.explorer.client.toolbar;

import java.util.Date;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.DateTimeFormat.PredefinedFormat;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.examples.resources.client.Resources;
import com.sencha.gxt.examples.resources.client.Utils;
import com.sencha.gxt.examples.resources.client.Utils.Theme;
import com.sencha.gxt.explorer.client.app.ui.ExampleContainer;
import com.sencha.gxt.explorer.client.model.Example.Detail;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.container.NorthSouthContainer;
import com.sencha.gxt.widget.core.client.info.Info;
import com.sencha.gxt.widget.core.client.menu.CheckMenuItem;
import com.sencha.gxt.widget.core.client.menu.DateMenu;
import com.sencha.gxt.widget.core.client.menu.HeaderMenuItem;
import com.sencha.gxt.widget.core.client.menu.Item;
import com.sencha.gxt.widget.core.client.menu.Menu;
import com.sencha.gxt.widget.core.client.menu.MenuBar;
import com.sencha.gxt.widget.core.client.menu.MenuBarItem;
import com.sencha.gxt.widget.core.client.menu.MenuItem;
import com.sencha.gxt.widget.core.client.menu.SeparatorMenuItem;

@Detail(
    name = "Menu Bar",
    category = "Tool Bar & Menu",
    icon = "menubar",
    minHeight = MenuBarExample.MIN_HEIGHT,
    minWidth = MenuBarExample.MIN_WIDTH,
    classes = { Utils.class }
)
public class MenuBarExample implements IsWidget, EntryPoint {

  protected static final int MIN_HEIGHT = 150;
  protected static final int MIN_WIDTH = 240;

  private ContentPanel panel;

  @Override
  public Widget asWidget() {
    if (panel == null) {
      SelectionHandler<Item> handler = new SelectionHandler<Item>() {
        @Override
        public void onSelection(SelectionEvent<Item> event) {
          if (event.getSelectedItem() instanceof MenuItem) {
            MenuItem item = (MenuItem) event.getSelectedItem();
            Info.display("Action", "You selected the " + item.getText());
          }
        }
      };

      Menu subMenuItem2 = new Menu();
      subMenuItem2.addSelectionHandler(handler);
      subMenuItem2.add(new MenuItem("readme.txt"));
      subMenuItem2.add(new MenuItem("helloworld.txt"));

      MenuItem subMenuNew = new MenuItem("New");
      MenuItem subMenuFile = new MenuItem("Open File");
      subMenuFile.setSubMenu(subMenuItem2);

      Menu menuFile = new Menu();
      menuFile.addSelectionHandler(handler);
      menuFile.add(subMenuNew);
      menuFile.add(subMenuFile);

      Menu subMenuEdit = new Menu();
      subMenuEdit.addSelectionHandler(handler);
      subMenuEdit.add(new MenuItem("Cut"));
      subMenuEdit.add(new MenuItem("Copy"));

      MenuBarItem menuBarEdit = new MenuBarItem("Edit", subMenuEdit);

      Menu subMenuItem3 = new Menu();
      subMenuItem3.addSelectionHandler(handler);
      subMenuItem3.add(new MenuItem("Search"));
      subMenuItem3.add(new MenuItem("File"));
      subMenuItem3.add(new MenuItem("Java"));

      MenuBarItem menuBarItem3 = new MenuBarItem("Search", subMenuItem3);

      CheckMenuItem checkMenuItem1 = new CheckMenuItem("I Like Cats");
      CheckMenuItem checkMenuItem2 = new CheckMenuItem("I Like Dogs");
      checkMenuItem1.setChecked(true);

      CheckMenuItem menuItemBlue = new CheckMenuItem("Blue Theme");
      CheckMenuItem menuItemGray = new CheckMenuItem("Gray Theme");
      CheckMenuItem menuItemNeptune = new CheckMenuItem("Neptune Theme");
      CheckMenuItem menuItemTriton = new CheckMenuItem("Triton Theme");

      menuItemBlue.setGroup("radios");
      menuItemGray.setGroup("radios");
      menuItemNeptune.setGroup("radios");
      menuItemTriton.setGroup("radios");
      menuItemTriton.setChecked(true);

      Menu radioMenu = new Menu();
      radioMenu.addSelectionHandler(handler);
      radioMenu.add(new HeaderMenuItem("Built-in GXT Themes"));
      radioMenu.add(menuItemBlue);
      radioMenu.add(menuItemGray);
      radioMenu.add(menuItemNeptune);
      radioMenu.add(menuItemTriton);

      MenuItem menuItemRadios = new MenuItem("Radio Options");
      menuItemRadios.setSubMenu(radioMenu);

      final DateMenu subdateMenu = new DateMenu();
      subdateMenu.getDatePicker().addValueChangeHandler(new ValueChangeHandler<Date>() {
        @Override
        public void onValueChange(ValueChangeEvent<Date> event) {
          Date d = event.getValue();
          DateTimeFormat f = DateTimeFormat.getFormat(PredefinedFormat.DATE_SHORT);
          Info.display("Value Changed", "You selected " + f.format(d));
          subdateMenu.hide(true);
        }
      });

      MenuItem menuItemDate = new MenuItem("Choose a Date");
      ImageResource dateIcon =
              Theme.TRITON.isActive() ? Resources.IMAGES.calendarTriton() : Resources.IMAGES.calendar();
      menuItemDate.setIcon(dateIcon);
      menuItemDate.setSubMenu(subdateMenu);

      Menu subMenu4 = new Menu();
      subMenu4.addSelectionHandler(handler);
      subMenu4.add(checkMenuItem1);
      subMenu4.add(checkMenuItem2);
      subMenu4.add(new SeparatorMenuItem());
      subMenu4.add(menuItemRadios);
      subMenu4.add(menuItemDate);

      MenuBarItem menuBarItem4 = new MenuBarItem("Foo", subMenu4);

      MenuBar menuBar = new MenuBar();
      menuBar.add(new MenuBarItem("File", menuFile));
      menuBar.add(menuBarEdit);
      menuBar.add(menuBarItem3);
      menuBar.add(menuBarItem4);

      NorthSouthContainer northSouthContainer = new NorthSouthContainer();
      northSouthContainer.setNorthWidget(menuBar);

      panel = new ContentPanel();
      panel.setHeading("Menu Bar");
      panel.add(northSouthContainer);
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
