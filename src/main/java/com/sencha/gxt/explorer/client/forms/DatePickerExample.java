package com.sencha.gxt.explorer.client.forms;

import java.util.Date;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.DateTimeFormat.PredefinedFormat;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.core.client.util.DateWrapper;
import com.sencha.gxt.explorer.client.app.ui.ExampleContainer;
import com.sencha.gxt.explorer.client.model.Example.Detail;
import com.sencha.gxt.widget.core.client.DatePicker;
import com.sencha.gxt.widget.core.client.info.Info;

@Detail(
  name = "Date Picker",
  category = "Forms",
  icon = "datepicker",
  preferredHeight = DatePickerExample.PREFERRED_HEIGHT,
  preferredWidth = DatePickerExample.PREFERRED_WIDTH)
public class DatePickerExample implements IsWidget, EntryPoint {

  protected static final int PREFERRED_HEIGHT = -1;
  protected static final int PREFERRED_WIDTH = -1;

  private DatePicker datePicker;

  @Override
  public Widget asWidget() {
    if (datePicker == null) {
      datePicker = new DatePicker();
      datePicker.setMinDate(new DateWrapper().addDays(-5).asDate());
      datePicker.addValueChangeHandler(new ValueChangeHandler<Date>() {
        @Override
        public void onValueChange(ValueChangeEvent<Date> event) {
          Date d = event.getValue();
          DateTimeFormat f = DateTimeFormat.getFormat(PredefinedFormat.DATE_SHORT);
          Info.display("Value Changed", "You selected " + f.format(d));
        }
      });
    }

    return datePicker;
  }

  @Override
  public void onModuleLoad() {
    new ExampleContainer(this).setPreferredHeight(PREFERRED_HEIGHT).setPreferredWidth(PREFERRED_WIDTH).doStandalone();
  }

}
