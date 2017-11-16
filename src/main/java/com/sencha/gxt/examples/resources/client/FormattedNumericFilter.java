package com.sencha.gxt.examples.resources.client;

import com.google.gwt.i18n.client.NumberFormat;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.examples.resources.client.model.Data;
import com.sencha.gxt.widget.core.client.form.NumberPropertyEditor;
import com.sencha.gxt.widget.core.client.grid.filters.NumericFilter;

public class FormattedNumericFilter extends NumericFilter<Data, Double> {

  NumberFormat formatter;

  public FormattedNumericFilter(ValueProvider<? super Data, Double> valueProvider,
      NumberPropertyEditor<Double> propertyEditor, String format) {
    super(valueProvider, propertyEditor);
    formatter = NumberFormat.getFormat(format);
  }

  protected boolean equals(Double a, Double b) {
    return formatter.format(a).equals(formatter.format(b));
  }

}
