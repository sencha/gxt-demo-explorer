package com.sencha.gxt.explorer.client.forms;

import java.util.Date;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.DateTimeFormat.PredefinedFormat;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.core.client.util.DateWrapper;
import com.sencha.gxt.core.client.util.ToggleGroup;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.examples.resources.client.TestData;
import com.sencha.gxt.examples.resources.client.model.Stock;
import com.sencha.gxt.examples.resources.client.model.StockProperties;
import com.sencha.gxt.explorer.client.app.ui.ExampleContainer;
import com.sencha.gxt.explorer.client.model.Example.Detail;
import com.sencha.gxt.widget.core.client.event.ParseErrorEvent;
import com.sencha.gxt.widget.core.client.form.CheckBox;
import com.sencha.gxt.widget.core.client.form.ComboBox;
import com.sencha.gxt.widget.core.client.form.DateField;
import com.sencha.gxt.widget.core.client.form.DoubleSpinnerField;
import com.sencha.gxt.widget.core.client.form.IntegerField;
import com.sencha.gxt.widget.core.client.form.Radio;
import com.sencha.gxt.widget.core.client.form.TextArea;
import com.sencha.gxt.widget.core.client.form.TimeField;
import com.sencha.gxt.widget.core.client.form.validator.MinDateValidator;
import com.sencha.gxt.widget.core.client.form.validator.MinLengthValidator;
import com.sencha.gxt.widget.core.client.info.Info;

@Detail(
    name = "Forms Example (UiBinder)",
    category = "Forms",
    icon = "formsexampleuibinder",
    classes = {
        Stock.class,
        StockProperties.class,
        TestData.class
    },
    files = "FormsUiBinderExample.ui.xml",
    minHeight = FormsUiBinderExample.MIN_HEIGHT,
    minWidth = FormsUiBinderExample.MIN_WIDTH
)
public class FormsUiBinderExample implements IsWidget, EntryPoint {

  interface MyUiBinder extends UiBinder<Widget, FormsUiBinderExample> {
  }

  protected static final int MIN_HEIGHT = 940;
  protected static final int MIN_WIDTH = 360;

  private static MyUiBinder uiBinder = GWT.create(MyUiBinder.class);

  private Widget widget;

  StockProperties properties = GWT.create(StockProperties.class);
  ListStore<Stock> store = new ListStore<Stock>(properties.key());

  @UiField(provided = true)
  ComboBox<Stock> combo = new ComboBox<Stock>(store, properties.nameLabel());
  @UiField
  DateField date;
  @UiField
  Radio radio;
  @UiField
  Radio radio2;
  @UiField
  TextArea description;
  @UiField
  DoubleSpinnerField spinnerField;
  @UiField
  IntegerField age;
  @UiField(provided = true)
  Date minValue = new DateWrapper().clearTime().addHours(8).asDate();
  @UiField(provided = true)
  Date maxValue = new DateWrapper().clearTime().addHours(18).addSeconds(1).asDate();
  @UiField
  TimeField time;

  @Override
  public Widget asWidget() {
    if (widget == null) {
      store.addAll(TestData.getStocks());

      widget = uiBinder.createAndBindUi(this);

      time.setFormat(DateTimeFormat.getFormat("hh:mm a"));

      combo.addValueChangeHandler(new ValueChangeHandler<Stock>() {
        @Override
        public void onValueChange(ValueChangeEvent<Stock> event) {
          Info.display("Selected", "You selected " + event.getValue());
        }
      });

      date.addValidator(new MinDateValidator(new Date()));
      description.addValidator(new MinLengthValidator(10));

      spinnerField.setIncrement(.1d);
      spinnerField.setMinValue(-10d);
      spinnerField.setMaxValue(10d);
      spinnerField.getPropertyEditor().setFormat(NumberFormat.getFormat("0.0"));
      spinnerField.addValueChangeHandler(new ValueChangeHandler<Double>() {
        @Override
        public void onValueChange(ValueChangeEvent<Double> event) {
          Info.display("Duration Changed",
              "Duration changed to " + event.getValue() == null ? "nothing" : event.getValue() + "");
        }
      });

      // set name on radios or use toggle group
      ToggleGroup toggle = new ToggleGroup();
      toggle.add(radio);
      toggle.add(radio2);
      toggle.addValueChangeHandler(new ValueChangeHandler<HasValue<Boolean>>() {
        @Override
        public void onValueChange(ValueChangeEvent<HasValue<Boolean>> event) {
          ToggleGroup group = (ToggleGroup) event.getSource();
          Radio radio = (Radio) group.getValue();
          Info.display("Color Changed", "You selected " + radio.getBoxLabel());
        }
      });
    }

    return widget;
  }

  @UiHandler("firstName")
  public void firstNameChanged(ValueChangeEvent<String> event) {
    Info.display("Value Changed", "First name changed to " + event.getValue() == null ? "blank" : event.getValue());
  }

  @UiHandler("age")
  public void onAgeParseError(ParseErrorEvent event) {
    Info.display("Parse Error", event.getErrorValue() + " could not be parsed as a number");
  }

  @UiHandler("date")
  public void onDateParseError(ParseErrorEvent event) {
    Info.display("Parse Error", event.getErrorValue() + " could not be parsed as a date");
  }

  @UiHandler("date")
  public void onDateChanged(ValueChangeEvent<Date> event) {
    String v = event.getValue() == null ? "nothing" : DateTimeFormat.getFormat(PredefinedFormat.DATE_MEDIUM).format(
        event.getValue());
    Info.display("Selected", "You selected " + v);
  }

  @UiHandler("spinnerField")
  public void onSpinChange(SelectionEvent<Double> event) {
    String msg = "nothing";
    if (event.getSelectedItem() != null) {
      msg = spinnerField.getPropertyEditor().render(event.getSelectedItem());
    }
    Info.display("Spin Change", "Current value changed to " + msg);
  }

  @UiHandler({"check1", "check2", "check3"})
  public void onMusicChange(ValueChangeEvent<Boolean> event) {
    CheckBox check = (CheckBox) event.getSource();
    Info.display("Music Changed", check.getBoxLabel() + " " + (event.getValue() ? "selected" : "not selected"));
  }

  @UiHandler("time")
  public void onTimeParseError(ParseErrorEvent event) {
    Info.display("Parse Error", event.getErrorValue() + " could not be parsed as a valid time.");
  }

  @Override
  public void onModuleLoad() {
    new ExampleContainer(this)
        .setMinHeight(MIN_HEIGHT)
        .setMinWidth(MIN_WIDTH)
        .doStandalone();
  }

}
