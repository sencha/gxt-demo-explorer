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

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor;
import com.google.gwt.editor.client.SimpleBeanEditorDriver;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.core.client.XTemplates;
import com.sencha.gxt.data.shared.LabelProvider;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.examples.resources.client.TestData;
import com.sencha.gxt.examples.resources.client.model.Stock;
import com.sencha.gxt.examples.resources.client.model.StockProperties;
import com.sencha.gxt.explorer.client.app.ui.ExampleContainer;
import com.sencha.gxt.explorer.client.model.Example.Detail;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.box.MessageBox;
import com.sencha.gxt.widget.core.client.container.CssFloatLayoutContainer;
import com.sencha.gxt.widget.core.client.container.HorizontalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.MarginData;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.form.ComboBox;
import com.sencha.gxt.widget.core.client.form.DateField;
import com.sencha.gxt.widget.core.client.form.DoubleField;
import com.sencha.gxt.widget.core.client.form.FormPanelHelper;
import com.sencha.gxt.widget.core.client.form.TextField;
import com.sencha.gxt.widget.core.client.form.validator.MinNumberValidator;
import com.sencha.gxt.widget.core.client.form.validator.RegExValidator;

@Detail(
  name = "Basic Data Binding (UiBinder)",
  category = "Data Binding",
  icon = "basicbindinguibinder",
  files = { "BasicDataBindingExample.html", "BasicDataBindingUiBinderExample.ui.xml" },
  classes = { Stock.class, StockProperties.class, TestData.class },
  minHeight = BasicDataBindingUiBinderExample.MIN_HEIGHT,
  minWidth = BasicDataBindingUiBinderExample.MIN_WIDTH)
public class BasicDataBindingUiBinderExample implements IsWidget, EntryPoint, Editor<Stock> {

  interface MyUiBinder extends UiBinder<ContentPanel, BasicDataBindingUiBinderExample> {
  }

  interface StockDriver extends SimpleBeanEditorDriver<Stock, BasicDataBindingUiBinderExample> {
  }

  interface StockTemplate extends XTemplates {
    @XTemplate(source = "BasicDataBindingExample.html")
    SafeHtml drawStock(Stock stock);
  }

  protected static final int MIN_HEIGHT = 370;
  protected static final int MIN_WIDTH = 495;

  private static MyUiBinder uiBinder = GWT.create(MyUiBinder.class);

  private Stock stock;
  private StockProperties properties = GWT.create(StockProperties.class);
  private StockDriver driver = GWT.create(StockDriver.class);
  private ContentPanel panel;

  @UiField
  @Ignore
  ComboBox<Stock> scb;
  @UiField
  CssFloatLayoutContainer inner;

  // editor fields
  @UiField
  TextField name;
  @UiField
  TextField symbol;
  @UiField
  DoubleField last;
  @UiField
  DoubleField change;
  @UiField
  DateField lastTrans;

  @UiField
  @Ignore
  HTML display;
  @UiField(provided = true)
  NumberFormat numberFormat = NumberFormat.getFormat("0.00");
  @UiField(provided = true)
  ListStore<Stock> stockStore;
  @UiField(provided = true)
  LabelProvider<Stock> stockLabelProvider = properties.nameLabel();
  @UiField
  HorizontalLayoutContainer hp;

  @Override
  public Widget asWidget() {
    if (panel == null) {
      stockStore = new ListStore<Stock>(properties.key());
      stockStore.addAll(TestData.getStocks());

      stock = stockStore.get(0);

      panel = uiBinder.createAndBindUi(this);

      name.setAllowBlank(false);
      
      last.setAllowBlank(false);
      last.addValidator(new MinNumberValidator<Double>(0D));

      symbol.setAllowBlank(false);
      symbol.addValidator(new RegExValidator("^[^a-z]+$", "Only uppercase letters allowed"));
      symbol.setAutoValidate(true);
      
      change.setAllowBlank(false);
      
      lastTrans.setAllowBlank(false);

      display.setHTML(getUpdatedPanel());

      panel.add(hp, new MarginData(10));

      driver.initialize(this);
      scb.setValue(stock);
      driver.edit(stock);
    }

    return panel;
  }

  @UiHandler("scb")
  public void nameComboChange(SelectionEvent<Stock> event) {
    symbol.clearInvalid();
    change.clearInvalid();
    last.clearInvalid();
    lastTrans.clearInvalid();

    stock = event.getSelectedItem();
    driver.edit(stock);
    display.setHTML(getUpdatedPanel());
  }

  @UiHandler("reset")
  public void resetClicked(SelectEvent event) {
    FormPanelHelper.reset(inner);
    driver.edit(stock);
  }

  @UiHandler("save")
  public void saveClicked(SelectEvent event) {
    stock = driver.flush();
    if (driver.hasErrors()) {
      new MessageBox("Please correct the errors before saving.").show();
      return;
    }
    display.setHTML(getUpdatedPanel());
    stockStore.update(stock);
  }

  private SafeHtml getUpdatedPanel() {
    StockTemplate template = GWT.create(StockTemplate.class);
    return template.drawStock(stock);
  }

  @Override
  public void onModuleLoad() {
    new ExampleContainer(this).setMinHeight(MIN_HEIGHT).setMinWidth(MIN_WIDTH).doStandalone();
  }

}
