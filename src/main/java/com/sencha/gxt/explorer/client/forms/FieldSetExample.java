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
package com.sencha.gxt.explorer.client.forms;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.explorer.client.app.ui.ExampleContainer;
import com.sencha.gxt.explorer.client.model.Example.Detail;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;
import com.sencha.gxt.widget.core.client.form.FieldLabel;
import com.sencha.gxt.widget.core.client.form.FieldSet;
import com.sencha.gxt.widget.core.client.form.TextField;

@Detail(
    name = "FieldSet",
    category = "Forms",
    icon = "formsexample",
    preferredHeight = FieldSetExample.PREFERRED_HEIGHT,
    preferredWidth = FieldSetExample.PREFERRED_WIDTH
)
public class FieldSetExample implements IsWidget, EntryPoint {

  protected static final int PREFERRED_HEIGHT = -1;
  protected static final int PREFERRED_WIDTH = 500;

  private ContentPanel panel;

  @Override
  public Widget asWidget() {
    if (panel == null) {
      TextField field1 = new TextField();
      TextField field2 = new TextField();
      TextField field3 = new TextField();
      
      FieldLabel fieldLabel1 = new FieldLabel(field1, "Field 1");
      FieldLabel fieldLabel2 = new FieldLabel(field2, "Field 2");
      FieldLabel fieldLabel3 = new FieldLabel(field3, "Field 3");
      
      VerticalLayoutContainer fields = new VerticalLayoutContainer();
      fields.add(fieldLabel1, new VerticalLayoutData(1, -1, new Margins(10)));
      fields.add(fieldLabel2, new VerticalLayoutData(1, -1, new Margins(10)));
      fields.add(fieldLabel3, new VerticalLayoutData(1, -1, new Margins(10)));
      
      FieldSet fieldSet1 = new FieldSet();
      fieldSet1.setCollapsible(true);
      fieldSet1.setHeading("My Fields");
      fieldSet1.add(fields);
      
      VerticalLayoutContainer vbox = new VerticalLayoutContainer();
      vbox.add(fieldSet1, new VerticalLayoutData(1, -1, new Margins(20)));

      panel = new ContentPanel();
      panel.setHeading("Example of the FieldSet");
      panel.add(vbox);
    }

    return panel;
  }

  @Override
  public void onModuleLoad() {
    new ExampleContainer(this)
        .setPreferredHeight(PREFERRED_HEIGHT)
        .setPreferredWidth(PREFERRED_WIDTH)
        .doStandalone();
  }

}
