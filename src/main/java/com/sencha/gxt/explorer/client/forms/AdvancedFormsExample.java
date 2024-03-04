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

import java.util.List;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.core.client.util.ToggleGroup;
import com.sencha.gxt.explorer.client.app.ui.ExampleContainer;
import com.sencha.gxt.explorer.client.model.Example.Detail;
import com.sencha.gxt.widget.core.client.FramedPanel;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.CssFloatLayoutContainer;
import com.sencha.gxt.widget.core.client.container.CssFloatLayoutContainer.CssFloatData;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;
import com.sencha.gxt.widget.core.client.form.DateField;
import com.sencha.gxt.widget.core.client.form.FieldLabel;
import com.sencha.gxt.widget.core.client.form.FormPanel.LabelAlign;
import com.sencha.gxt.widget.core.client.form.FormPanelHelper;
import com.sencha.gxt.widget.core.client.form.HtmlEditor;
import com.sencha.gxt.widget.core.client.form.Radio;
import com.sencha.gxt.widget.core.client.form.TextField;

@Detail(
    name = "Advanced Forms",
    category = "Forms",
    icon = "advancedforms",
    minHeight = AdvancedFormsExample.MIN_HEIGHT,
    minWidth = AdvancedFormsExample.MIN_WIDTH
)
public class AdvancedFormsExample implements IsWidget, EntryPoint {

  protected static final int MIN_HEIGHT = 480;
  protected static final int MIN_WIDTH = 720;

  private FramedPanel panel;

  @Override
  public Widget asWidget() {
    if (panel == null) {
      TextField firstName = new TextField();
      firstName.setAllowBlank(false);

      TextField lastName = new TextField();
      lastName.setAllowBlank(false);

      TextField company = new TextField();

      TextField email = new TextField();

      DateField birthday = new DateField();

      Radio radio1 = new Radio();
      radio1.setHeight(20);
      radio1.setBoxLabel("Yes");

      Radio radio2 = new Radio();
      radio2.setHeight(20);
      radio2.setBoxLabel("No");

      CssFloatLayoutContainer radios = new CssFloatLayoutContainer();
      radios.add(radio1, new CssFloatData(-1, new Margins(4, 0, 0, 0)));
      radios.add(radio2, new CssFloatData(-1, new Margins(4, 0, 0, 0)));

      ToggleGroup group = new ToggleGroup();
      group.add(radio1);
      group.add(radio2);

      HtmlEditor htmlEditor = new HtmlEditor();

      CssFloatLayoutContainer columns = new CssFloatLayoutContainer();
      columns.add(new FieldLabel(firstName, "First Name"), new CssFloatData(0.5, new Margins(0, 7, 0, 0)));
      columns.add(new FieldLabel(lastName, "Last Name"), new CssFloatData(0.5, new Margins(0, 0, 0, 8)));
      columns.add(new FieldLabel(company, "Company"), new CssFloatData(0.5, new Margins(0, 7, 0, 0)));
      columns.add(new FieldLabel(email, "Email"), new CssFloatData(0.5, new Margins(0, 0, 0, 8)));
      columns.add(new FieldLabel(birthday, "Birthday"), new CssFloatData(0.5, new Margins(0, 7, 0, 0)));
      columns.add(new FieldLabel(radios, "GXT User"), new CssFloatData(0.5, new Margins(0, 0, 0, 8)));

      VerticalLayoutContainer container = new VerticalLayoutContainer();
      container.add(columns, new VerticalLayoutData(1, -1, new Margins(15, 15, 0, 15)));
      container.add(new FieldLabel(htmlEditor, "Comment"), new VerticalLayoutData(1, 1, new Margins(0, 15, 0, 15)));

      panel = new FramedPanel();
      panel.setHeading("Advanced Forms");
      panel.add(container);
      panel.addButton(new TextButton("Cancel"));
      panel.addButton(new TextButton("Submit"));

      // need to call after everything is constructed
      List<FieldLabel> fieldLabels = FormPanelHelper.getFieldLabels(panel);
      for (FieldLabel fieldLabel : fieldLabels) {
        fieldLabel.setLabelAlign(LabelAlign.TOP);
      }
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
