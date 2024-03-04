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
package com.sencha.gxt.explorer.client.databinding;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor;
import com.google.gwt.editor.client.SimpleBeanEditorDriver;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.data.shared.Converter;
import com.sencha.gxt.examples.resources.client.model.Stock;
import com.sencha.gxt.explorer.client.app.ui.ExampleContainer;
import com.sencha.gxt.explorer.client.model.Example.Detail;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.box.MessageBox;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.MarginData;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.form.ConverterEditorAdapter;
import com.sencha.gxt.widget.core.client.form.FieldLabel;
import com.sencha.gxt.widget.core.client.form.TextField;

@Detail(
    name = "Binding Data Converter",
    category = "Data Binding",
    icon = "bindingconverter",
    classes = Stock.class,
    minHeight = BindingConverterExample.MIN_HEIGHT,
    minWidth = BindingConverterExample.MIN_WIDTH
)
public class BindingConverterExample implements IsWidget, EntryPoint, Editor<Stock> {

  interface Driver extends SimpleBeanEditorDriver<Stock, BindingConverterExample> {
  }

  /**
   * A simple converter that illustrates how to perform automatic data conversion between the TextField
   * and the backing model. This converter toggles the case of the input String.
   */
  static class ExampleConverter implements Converter<String, String> {

    @Override
    public String convertFieldValue(String fieldValue) {
      return toggleCase(fieldValue);
    }

    @Override
    public String convertModelValue(String modelValue) {
      return toggleCase(modelValue);
    }

    private String toggleCase(String input) {
      StringBuilder sb = new StringBuilder();
      for (char c : input.toCharArray()) {
        if (Character.isUpperCase(c)) {
          sb.append(Character.toLowerCase(c));
        } else if (Character.isLowerCase(c)) {
          sb.append(Character.toUpperCase(c));
        } else {
          sb.append(c);
        }
      }
      return sb.toString();
    }

  }

  protected static final int MIN_WIDTH = 400;
  protected static final int MIN_HEIGHT = 210;

  @Ignore
  TextField nameField = new TextField();

  ConverterEditorAdapter<String, String, TextField> name = new ConverterEditorAdapter<String, String, TextField>(
      nameField, new ExampleConverter());

  private ContentPanel panel;

  @Override
  public Widget asWidget() {
    if (panel == null) {
      Stock stock = new Stock("wAYNE eNTERPRISES", 93.55, 0.0, 0.0, null, "Manufacturing");

      final Driver driver = GWT.create(Driver.class);
      driver.initialize(this);
      driver.edit(stock);

      SafeHtml html = SafeHtmlUtils.fromSafeConstant("<div style=\""
          + "padding-bottom: 10px; border-bottom: 1px dashed; margin-bottom: 10px; font-size: small;\">"
          + "This example illustrates how to perform automatic data conversion between a form field and "
          + "a backing data model using the GWT Editor framework."
          + "</div>");

      TextButton button = new TextButton("Show Converted Model Value", new SelectHandler() {

        @Override
        @SuppressWarnings("deprecation")
        public void onSelect(SelectEvent event) {
          Stock s = driver.flush();
          if (driver.hasErrors()) {
            new MessageBox("Please correct the errors before converting.").show();
            return;
          }

          String modelValue = s.getName();
          new MessageBox("Converted Value From Data Model", modelValue != null ? modelValue : "— no value —").show();
        }

      });

      VerticalLayoutContainer container = new VerticalLayoutContainer();
      container.add(new HTML(html), new VerticalLayoutData(1, -1));
      container.add(new FieldLabel(nameField, "Name"), new VerticalLayoutData(1, -1));

      panel = new ContentPanel();
      panel.setHeading("Data Binding Converter");
      panel.add(container, new MarginData(10));
      panel.addButton(button);
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
