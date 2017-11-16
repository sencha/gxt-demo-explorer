package com.sencha.gxt.explorer.client.databinding;

import com.google.gwt.editor.client.Editor;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.core.client.util.Util;
import com.sencha.gxt.examples.resources.client.model.Stock;
import com.sencha.gxt.widget.core.client.button.ButtonBar;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.BoxLayoutContainer.BoxLayoutData;
import com.sencha.gxt.widget.core.client.container.CssFloatLayoutContainer;
import com.sencha.gxt.widget.core.client.container.CssFloatLayoutContainer.CssFloatData;
import com.sencha.gxt.widget.core.client.event.SelectEvent.HasSelectHandlers;
import com.sencha.gxt.widget.core.client.form.FieldLabel;
import com.sencha.gxt.widget.core.client.form.FormPanel;
import com.sencha.gxt.widget.core.client.form.TextField;
import com.sencha.gxt.widget.core.client.toolbar.LabelToolItem;

public class StockEditor implements IsWidget, Editor<Stock> {

  private FormPanel panel;
  private CssFloatLayoutContainer container;
  private TextButton save;

  TextField name;
  TextField symbol;

  public StockEditor() {
    name = new TextField();
    name.setEnabled(false);
    symbol = new TextField();
    symbol.setEnabled(false);

    save = new TextButton("Save");
    save.setEnabled(false);

    ButtonBar buttons = new ButtonBar();
    BoxLayoutData flex = new BoxLayoutData();
    flex.setFlex(1);
    buttons.add(new LabelToolItem(Util.NBSP_SAFE_HTML), flex);
    buttons.add(save);


    container = new CssFloatLayoutContainer();
    container.add(new FieldLabel(name, "Name"), new CssFloatData(1, new Margins(0, 0, 5, 0)));
    container.add(new FieldLabel(symbol, "Symbol"), new CssFloatData(1, new Margins(0, 0, 5, 0)));
    container.add(buttons, new CssFloatData(1, new Margins(0)));

    panel = new FormPanel();
    panel.setLabelWidth(50);
    panel.setWidget(container);
    panel.setLabelWidth(50);
  }

  @Override
  public Widget asWidget() {
    return panel;
  }

  public void setSaveEnabled(boolean enabled) {
    save.setEnabled(enabled);
    name.setEnabled(enabled);
    symbol.setEnabled(enabled);
    if (!enabled) {

      name.setValue("");
      symbol.setValue("");
    }
  }

  public HasSelectHandlers getSaveButton() {
    return save;
  }

}