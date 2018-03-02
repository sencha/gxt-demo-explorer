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
package com.sencha.gxt.explorer.client.forms;

import java.util.Date;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.cell.core.client.form.ComboBoxCell.TriggerAction;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.explorer.client.app.ui.ExampleContainer;
import com.sencha.gxt.explorer.client.model.Example.Detail;
import com.sencha.gxt.widget.core.client.Composite;
import com.sencha.gxt.widget.core.client.FramedPanel;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.BoxLayoutContainer.BoxLayoutData;
import com.sencha.gxt.widget.core.client.container.HBoxLayoutContainer;
import com.sencha.gxt.widget.core.client.container.HBoxLayoutContainer.HBoxLayoutAlign;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;
import com.sencha.gxt.widget.core.client.event.CollapseEvent;
import com.sencha.gxt.widget.core.client.event.CollapseEvent.CollapseHandler;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.event.TriggerClickEvent;
import com.sencha.gxt.widget.core.client.event.TriggerClickEvent.TriggerClickHandler;
import com.sencha.gxt.widget.core.client.form.AdapterField;
import com.sencha.gxt.widget.core.client.form.DateField;
import com.sencha.gxt.widget.core.client.form.IntegerSpinnerField;
import com.sencha.gxt.widget.core.client.form.NumberPropertyEditor;
import com.sencha.gxt.widget.core.client.form.SpinnerField;
import com.sencha.gxt.widget.core.client.form.StringComboBox;
import com.sencha.gxt.widget.core.client.info.Info;
import com.sencha.gxt.widget.core.client.toolbar.LabelToolItem;

@Detail(
  name = "Adapter Field with DateTime",
  category = "Forms",
  icon = "advancedforms",
  minHeight = AdapterFieldWithDateTimePickerExample.MIN_HEIGHT,
  minWidth = AdapterFieldWithDateTimePickerExample.MIN_WIDTH)
public class AdapterFieldWithDateTimePickerExample implements IsWidget, EntryPoint {

  // Example of using a custom editor
  public class MinutePropertyEditor extends NumberPropertyEditor<Integer> {
    public MinutePropertyEditor() {
      super(1);
    }

    public MinutePropertyEditor(NumberFormat format) {
      super(format, 1);
    }

    @Override
    public Integer doDecr(Integer value) {
      int newValue = value - getIncrement();
      newValue = newValue > value ? value : newValue;
      return newValue;
    }

    @Override
    public Integer doIncr(Integer value) {
      int newValue = value + getIncrement();
      newValue = newValue < value ? value : newValue;
      return newValue;
    }

    @Override
    protected Integer parseString(String string) {
      // Handle non-U.S. locales (e.g. 1.234 instead of 1,234)
      return (int) format.parse(string);
    }

    @Override
    protected Integer returnTypedValue(Number number) {
      return number.intValue();
    }
  }

  protected static final int MIN_HEIGHT = 480;
  protected static final int MIN_WIDTH = 480;

  private FramedPanel panel;

  @Override
  public Widget asWidget() {
    if (panel == null) {
      final DateTimeFieldAdapter dateTimeField = new DateTimeFieldAdapter();

      TextButton button = new TextButton("Display Value");
      button.addSelectHandler(new SelectHandler() {
        @Override
        public void onSelect(SelectEvent event) {
          String v = "";
          if (dateTimeField.getValue() != null) {
            v = dateTimeField.getValue().toLocaleString();
          }
          Info.display("", "Date is " + v);
        }
      });

      VerticalLayoutContainer vlc = new VerticalLayoutContainer();
      vlc.add(dateTimeField, new VerticalLayoutData(-1, -1, new Margins(20)));
      vlc.add(button, new VerticalLayoutData(-1, -1, new Margins(20)));

      panel = new FramedPanel();
      panel.setHeading("Date Time Adapter Field Example");
      panel.add(vlc);
    }

    return panel;
  }

  /**
   * Custom adapter which sets/gets the value to/from the widget.
   */
  public class DateTimeFieldAdapter extends AdapterField<Date> {
    public DateTimeFieldAdapter() {
      super(new DateTimeWidget());
    }

    @Override
    public void setValue(Date value) {
      ((DateTimeWidget) getWidget()).setValue(value);
    }

    @Override
    public Date getValue() {
      return ((DateTimeWidget) getWidget()).getValue();
    }
  }

  /**
   * Custom DateTime widget for the adapter.
   */
  public class DateTimeWidget extends Composite {
    private DateField dateField;
    private IntegerSpinnerField hourField;
    private SpinnerField<Integer> minField;
    private StringComboBox amPmField;

    public DateTimeWidget() {
      dateField = new DateField();
      dateField.addCollapseHandler(new CollapseHandler() {
        @Override
        public void onCollapse(CollapseEvent event) {
          setTime(dateField.getCurrentValue());
        }
      });

      hourField = new IntegerSpinnerField();
      hourField.setWidth(70);
      hourField.setMinValue(1);
      hourField.setMaxValue(12);
      hourField.addValueChangeHandler(new ValueChangeHandler<Integer>() {
        @Override
        public void onValueChange(ValueChangeEvent<Integer> event) {
        }
      });
      hourField.addTriggerClickHandler(new TriggerClickHandler() {
        @Override
        public void onTriggerClick(TriggerClickEvent event) {
        }
      });

      minField = new SpinnerField<Integer>(new MinutePropertyEditor(NumberFormat.getFormat("00")));
      minField.setWidth(70);
      minField.setMinValue(0);
      minField.setMaxValue(59);
      minField.addValueChangeHandler(new ValueChangeHandler<Integer>() {
        @Override
        public void onValueChange(ValueChangeEvent<Integer> event) {
        }
      });
      minField.addTriggerClickHandler(new TriggerClickHandler() {
        @Override
        public void onTriggerClick(TriggerClickEvent event) {
        }
      });

      amPmField = new StringComboBox();
      amPmField.setTriggerAction(TriggerAction.ALL);
      amPmField.setWidth(60);
      amPmField.add("AM");
      amPmField.add("PM");

      HBoxLayoutContainer hlc = new HBoxLayoutContainer(HBoxLayoutAlign.MIDDLE);
      hlc.add(dateField, new BoxLayoutData(new Margins(0, 20, 0, 0)));
      hlc.add(hourField);
      hlc.add(new LabelToolItem(":"));
      hlc.add(minField);
      hlc.add(amPmField);

      initWidget(hlc);
      
      setValue(new Date());
    }

    public void setValue(Date value) {
      if (value == null) {
        dateField.reset();
        hourField.reset();
        minField.reset();
        amPmField.reset();
      } else {
        dateField.setValue(value);
        setTime(value);
      }
    }

    public void setTime(Date value) {
      String shourOfDay = DateTimeFormat.getFormat("hh").format(value);
      String sminOfHour = DateTimeFormat.getFormat("mm").format(value);

      int hourOfDay = Integer.parseInt(shourOfDay);
      int minOfHour = Integer.parseInt(sminOfHour);

      hourField.setValue(hourOfDay);
      minField.setValue(minOfHour);

      String ampm = DateTimeFormat.getFormat("a").format(value);
      amPmField.setValue(ampm);
    }

    /**
     * This is a rough example of how it can work. This may need to be tested more.
     * @return the date
     * @since 4.0.3
     */
    public Date getValue() {
      int hours = 0;
      if (hourField != null) {
        hours = hourField.getCurrentValue();
      }
      if (amPmField.getCurrentValue() != null && amPmField.getCurrentValue().equals("PM")) {
        hours += 12;
      }

      int minutes = minField.getValue();

      Date val = dateField.getCurrentValue();
      if (val == null) {
        val = new Date();
      }
      val.setHours(hours);
      val.setMinutes(minutes);
      val.setSeconds(0);

      return val;
    }
  }

  @Override
  public void onModuleLoad() {
    new ExampleContainer(this).setMinHeight(MIN_HEIGHT).setMinWidth(MIN_WIDTH).doStandalone();
  }

}
