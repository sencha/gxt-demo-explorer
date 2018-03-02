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
package com.sencha.gxt.explorer.client.databinding;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.SimpleBeanEditorDriver;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.core.client.util.DateWrapper;
import com.sencha.gxt.examples.resources.client.Utils;
import com.sencha.gxt.examples.resources.client.model.Kid;
import com.sencha.gxt.examples.resources.client.model.Person;
import com.sencha.gxt.explorer.client.app.ui.ExampleContainer;
import com.sencha.gxt.explorer.client.model.Example.Detail;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.container.MarginData;

@Detail(
    name="List Property Binding",
    category="Data Binding",
    icon = "listproperty",
    classes = {
        PersonEditor.class,
        Person.class,
        Kid.class,
        Utils.class
    },
    minHeight = ListPropertyBindingExample.MIN_HEIGHT,
    minWidth = ListPropertyBindingExample.MIN_WIDTH
)
public class ListPropertyBindingExample implements EntryPoint, IsWidget {

  interface Driver extends SimpleBeanEditorDriver<Person, PersonEditor> {
  }

  protected static final int MIN_HEIGHT = 445;
  protected static final int MIN_WIDTH = 350;

  private Driver driver = GWT.create(Driver.class);
  private ContentPanel panel;

  @Override
  public Widget asWidget() {
    if (panel == null) {
      PersonEditor personEditor = new PersonEditor(driver);

      Person person = new Person("John Doe", "ACME Widget Co.", "Widgets", "Anytown, USA", 43460d);
      List<Kid> kids = new ArrayList<Kid>();
      kids.add(new Kid("Noah", 4, new DateWrapper(2011, 1, 1).asDate()));
      kids.add(new Kid("Emma", 2, new DateWrapper(2013, 1, 1).asDate()));
      kids.add(new Kid("Liam", 1, new DateWrapper(2014, 1, 1).asDate()));
      person.setKids(kids);
      driver.edit(person);

      panel = new ContentPanel();
      panel.setHeading("List Property Binding");
      panel.add(personEditor.asWidget(), new MarginData(10));
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
