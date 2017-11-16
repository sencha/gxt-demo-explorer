package com.sencha.gxt.explorer.client.forms;

import java.math.BigDecimal;
import java.math.BigInteger;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.examples.resources.client.TestData;
import com.sencha.gxt.examples.resources.client.model.Stock;
import com.sencha.gxt.examples.resources.client.model.StockProperties;
import com.sencha.gxt.explorer.client.app.ui.ExampleContainer;
import com.sencha.gxt.explorer.client.model.Example.Detail;
import com.sencha.gxt.widget.core.client.FramedPanel;
import com.sencha.gxt.widget.core.client.form.BigDecimalSpinnerField;
import com.sencha.gxt.widget.core.client.form.BigIntegerSpinnerField;
import com.sencha.gxt.widget.core.client.form.DoubleSpinnerField;
import com.sencha.gxt.widget.core.client.form.FieldLabel;
import com.sencha.gxt.widget.core.client.form.FloatSpinnerField;
import com.sencha.gxt.widget.core.client.form.IntegerSpinnerField;
import com.sencha.gxt.widget.core.client.form.LongSpinnerField;
import com.sencha.gxt.widget.core.client.form.ShortSpinnerField;

@Detail(
    name = "Spinner Fields",
    category = "Forms",
    icon = "formsexample",
    classes = {
        Stock.class,
        StockProperties.class,
        TestData.class
    },
    minHeight = SpinnerFieldsExample.MIN_HEIGHT,
    minWidth = SpinnerFieldsExample.MIN_WIDTH
)
public class SpinnerFieldsExample implements IsWidget, EntryPoint {

  protected static final int MIN_HEIGHT = 600;
  protected static final int MIN_WIDTH = 500;

  private FramedPanel widget;

  @Override
  public Widget asWidget() {
    if (widget == null) {
      widget = new FramedPanel();
      
      widget.add(createNumberSpinnerFields());
    }

    return widget;
  }
  
  private VerticalPanel createNumberSpinnerFields() {
    BigDecimalSpinnerField bigDecimalSpinnerField = new BigDecimalSpinnerField();
    bigDecimalSpinnerField.setValue(new BigDecimal("3.1415926535897932384626433832795028841971"));

    BigIntegerSpinnerField bigIntegerSpinnerField = new BigIntegerSpinnerField();
    bigIntegerSpinnerField.setValue(new BigInteger("9876543210987654321098765432109876543210"));

    DoubleSpinnerField doubleSpinnerField = new DoubleSpinnerField();
    doubleSpinnerField.setValue(12345.09827D);

    FloatSpinnerField floatSpinnerField = new FloatSpinnerField();
    floatSpinnerField.setValue(12345.09827F);

    IntegerSpinnerField integerSpinnerField = new IntegerSpinnerField();
    integerSpinnerField.setValue(2147483647);

    LongSpinnerField longSpinnerField = new LongSpinnerField();
    longSpinnerField.setValue(4294967296L);

    ShortSpinnerField shortSpinnerField = new ShortSpinnerField();
    shortSpinnerField.setValue(Short.parseShort("32767"));

    FieldLabel bigDecimalFieldLabel = new FieldLabel(bigDecimalSpinnerField, "BigDecimalSpinnerField");
    FieldLabel bigIntegerFieldLabel = new FieldLabel(bigIntegerSpinnerField, "BigIntegerSpinnerField");
    FieldLabel doubleFieldLabel = new FieldLabel(doubleSpinnerField, "DoubleSpinnerField");
    FieldLabel floatFieldLabel = new FieldLabel(floatSpinnerField, "FloatSpinnerField");
    FieldLabel integerFieldLabel = new FieldLabel(integerSpinnerField, "IntegerSpinnerField");
    FieldLabel longFieldLabel = new FieldLabel(longSpinnerField, "LongSpinnerField");
    FieldLabel shortFieldLabel = new FieldLabel(shortSpinnerField, "ShortField");

    bigDecimalFieldLabel.setLabelWidth(150);
    bigIntegerFieldLabel.setLabelWidth(150);
    doubleFieldLabel.setLabelWidth(150);
    floatFieldLabel.setLabelWidth(150);
    integerFieldLabel.setLabelWidth(150);
    longFieldLabel.setLabelWidth(150);
    shortFieldLabel.setLabelWidth(150);

    VerticalPanel widget = new VerticalPanel();
    widget.setSpacing(10);
    widget.add(bigDecimalFieldLabel);
    widget.add(bigIntegerFieldLabel);
    widget.add(doubleFieldLabel);
    widget.add(floatFieldLabel);
    widget.add(integerFieldLabel);
    widget.add(longFieldLabel);
    widget.add(shortFieldLabel);

    return widget;
  }

  @Override
  public void onModuleLoad() {
    new ExampleContainer(this)
        .setMinHeight(MIN_HEIGHT)
        .setMinWidth(MIN_WIDTH)
        .doStandalone();
  }

}
