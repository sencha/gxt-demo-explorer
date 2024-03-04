/**
 * Sencha GXT 1.1.0-SNAPSHOT - Sencha for GWT
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

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import com.google.gwt.cell.client.TextCell;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor.Path;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.core.client.util.ToggleGroup;
import com.sencha.gxt.data.shared.LabelProvider;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;
import com.sencha.gxt.dnd.core.client.DND.Feedback;
import com.sencha.gxt.examples.resources.client.TestData;
import com.sencha.gxt.examples.resources.client.model.State;
import com.sencha.gxt.examples.resources.client.model.Stock;
import com.sencha.gxt.examples.resources.client.model.StockProperties;
import com.sencha.gxt.explorer.client.app.ui.ExampleContainer;
import com.sencha.gxt.explorer.client.model.Example.Detail;
import com.sencha.gxt.widget.core.client.ColorPalette;
import com.sencha.gxt.widget.core.client.DatePicker;
import com.sencha.gxt.widget.core.client.FramedPanel;
import com.sencha.gxt.widget.core.client.ListView;
import com.sencha.gxt.widget.core.client.Slider;
import com.sencha.gxt.widget.core.client.button.SplitButton;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.button.ToggleButton;
import com.sencha.gxt.widget.core.client.container.MarginData;
import com.sencha.gxt.widget.core.client.form.BigDecimalField;
import com.sencha.gxt.widget.core.client.form.BigDecimalSpinnerField;
import com.sencha.gxt.widget.core.client.form.BigIntegerField;
import com.sencha.gxt.widget.core.client.form.BigIntegerSpinnerField;
import com.sencha.gxt.widget.core.client.form.CheckBox;
import com.sencha.gxt.widget.core.client.form.ComboBox;
import com.sencha.gxt.widget.core.client.form.DateField;
import com.sencha.gxt.widget.core.client.form.DoubleField;
import com.sencha.gxt.widget.core.client.form.DoubleSpinnerField;
import com.sencha.gxt.widget.core.client.form.DualListField;
import com.sencha.gxt.widget.core.client.form.FieldLabel;
import com.sencha.gxt.widget.core.client.form.FileUploadField;
import com.sencha.gxt.widget.core.client.form.FloatField;
import com.sencha.gxt.widget.core.client.form.FloatSpinnerField;
import com.sencha.gxt.widget.core.client.form.HtmlEditor;
import com.sencha.gxt.widget.core.client.form.IntegerField;
import com.sencha.gxt.widget.core.client.form.IntegerSpinnerField;
import com.sencha.gxt.widget.core.client.form.ListField;
import com.sencha.gxt.widget.core.client.form.LongField;
import com.sencha.gxt.widget.core.client.form.LongSpinnerField;
import com.sencha.gxt.widget.core.client.form.PasswordField;
import com.sencha.gxt.widget.core.client.form.Radio;
import com.sencha.gxt.widget.core.client.form.ShortField;
import com.sencha.gxt.widget.core.client.form.ShortSpinnerField;
import com.sencha.gxt.widget.core.client.form.SimpleComboBox;
import com.sencha.gxt.widget.core.client.form.StringComboBox;
import com.sencha.gxt.widget.core.client.form.TextArea;
import com.sencha.gxt.widget.core.client.form.TextField;
import com.sencha.gxt.widget.core.client.form.TimeField;
import com.sencha.gxt.widget.core.client.form.validator.EmptyValidator;
import com.sencha.gxt.widget.core.client.form.validator.MaxDateValidator;
import com.sencha.gxt.widget.core.client.form.validator.MaxLengthValidator;
import com.sencha.gxt.widget.core.client.form.validator.MaxNumberValidator;
import com.sencha.gxt.widget.core.client.form.validator.MinDateValidator;
import com.sencha.gxt.widget.core.client.form.validator.MinLengthValidator;
import com.sencha.gxt.widget.core.client.form.validator.MinNumberValidator;
import com.sencha.gxt.widget.core.client.form.validator.RegExValidator;
import com.sencha.gxt.widget.core.client.menu.Menu;
import com.sencha.gxt.widget.core.client.menu.MenuItem;

@Detail(
  name = "Fields",
  category = "Forms",
  icon = "formsexample",
  classes = { Stock.class, StockProperties.class, TestData.class, State.class },
  minHeight = FieldsExample.MIN_HEIGHT,
  minWidth = FieldsExample.MIN_WIDTH)
public class FieldsExample implements IsWidget, EntryPoint {

  protected static final int MIN_HEIGHT = 800;
  protected static final int MIN_WIDTH = 800;

  private static final int VERTICAL_SPACING = 3;
  private static final String[] COLORS = new String[] { "161616", "002241", "006874", "82a700", "bbc039", "f3f1cd" };

  interface StateProperties extends PropertyAccess<State> {
    ModelKeyProvider<State> abbr();

    LabelProvider<State> name();

    @Path("name")
    ValueProvider<State, String> nameProp();
  }

  private VerticalPanel verticalPanel;
  private FramedPanel framedPanel;

  @Override
  public Widget asWidget() {
    if (framedPanel == null) {
      verticalPanel = new VerticalPanel();
      
      framedPanel = new FramedPanel();
      framedPanel.add(verticalPanel, new MarginData(20));
      
      createCheckBox();
      createColorPalette();
      createComboBox();
      createDateField();
      createDatePicker();
      createDualListField();
      createFileUpload();
      createHtmlEditor();
      createNumberInputs();
      createListField();
      createPasswordField();
      createRadio();
      createSimpleComboBox();
      createSlider();
      createSplitButton();
      createStringComboBox();
      createTextArea();
      createTextButton();
      createTextField();
      createTimeField();
      createToggleButton();
    }

    return framedPanel;
  }

  private void createCheckBox() {
    CheckBox checkBox = new CheckBox();
    checkBox.setTitle("GXT CheckBox");
    checkBox.setBoxLabel("BoxLabel");

    FieldLabel fieldLabel = new FieldLabel(checkBox, "CheckBox");
    verticalPanel.add(fieldLabel);
  }

  private void createColorPalette() {
    ColorPalette colorPalette = new ColorPalette(COLORS, COLORS);

    FieldLabel fieldLabel = new FieldLabel(colorPalette, "ColorPalette");
    verticalPanel.add(fieldLabel);
  }

  private void createComboBox() {
    StateProperties properties = GWT.create(StateProperties.class);
    ListStore<State> listStore = new ListStore<State>(properties.abbr());
    listStore.addAll(TestData.getStates());

    ComboBox<State> comboBox = new ComboBox<State>(listStore, properties.name());

    FieldLabel fieldLabel = new FieldLabel(comboBox, "ComboBox");
    verticalPanel.add(fieldLabel);
  }

  @SuppressWarnings("deprecation")
  private void createDateField() {
    Date minDate = new Date(2013, 1, 31);
    Date maxDate = new Date(2014, 12, 31);

    DateField dateField = new DateField();
    dateField.setMinValue(minDate);
    dateField.setMaxValue(maxDate);
    dateField.addValidator(new MinDateValidator(minDate));
    dateField.addValidator(new MaxDateValidator(maxDate));

    FieldLabel fieldLabel = new FieldLabel(dateField, "DateField");
    verticalPanel.add(fieldLabel);
  }

  private void createDatePicker() {
    DatePicker datePicker = new DatePicker();

    FieldLabel fieldLabel = new FieldLabel(datePicker, "DatePicker");
    verticalPanel.add(fieldLabel);
  }

  private void createDualListField() {
    StateProperties properties = GWT.create(StateProperties.class);
    ListStore<State> states = new ListStore<State>(properties.abbr());
    states.addAll(TestData.getStates());

    ListStore<State> toStates = new ListStore<State>(properties.abbr());

    DualListField<State, String> dualListField = new DualListField<State, String>(states, toStates, properties.nameProp(), new TextCell());
    dualListField.addValidator(new EmptyValidator<List<State>>());
    dualListField.setEnableDnd(true);
    dualListField.setMode(DualListField.Mode.INSERT);

    FieldLabel fieldLabel = new FieldLabel(dualListField, "DualListField");
    verticalPanel.add(fieldLabel);
  }

  private void createFileUpload() {
    FileUploadField fileUploadField = new FileUploadField();

    FieldLabel fieldLabel = new FieldLabel(fileUploadField, "FileUploadField");
    verticalPanel.add(fieldLabel);
  }

  private void createHtmlEditor() {
    String htmlEditorText = "The <span style=\"background-color: rgb(222, 184, 135);\">brown</span> "
        + "fox <font color=\"B22222\">jumped</font> <b>over</b> the <i><font color=\"808000\">moon</font></i>.<br>";
    HtmlEditor htmlEditor = new HtmlEditor();
    htmlEditor.setValue(htmlEditorText);
    htmlEditor.setHeight(100);

    FieldLabel fieldLabel = new FieldLabel(htmlEditor, "HtmlEditor");
    verticalPanel.add(fieldLabel);
  }

  private void createListField() {
    StateProperties properties = GWT.create(StateProperties.class);
    ListStore<State> states = new ListStore<State>(properties.abbr());
    states.addAll(TestData.getStates());

    ListView<State, String> listView = new ListView<State, String>(states, properties.nameProp());

    ListField<State, String> listField = new ListField<State, String>(listView);
    listField.setPixelSize(100, 75);

    FieldLabel fieldLabel = new FieldLabel(listField, "ListField");
    verticalPanel.add(fieldLabel);
  }

  private void createNumberInputs() {
    HorizontalPanel horizontalPanel = new HorizontalPanel();

    horizontalPanel.add(createNumberFields());
    horizontalPanel.add(new HTML("&nbsp;&nbsp;&nbsp;&nbsp;"));
    horizontalPanel.add(createNumberSpinnerFields());

    verticalPanel.add(horizontalPanel);
  }

  private VerticalPanel createNumberFields() {
    BigDecimalField bigDecimalField = new BigDecimalField();
    bigDecimalField.setValue(new BigDecimal("3.1415926535897932384626433832795028841971"));

    BigIntegerField bigIntegerField = new BigIntegerField();
    bigIntegerField.setValue(new BigInteger("9876543210987654321098765432109876543210"));

    DoubleField doubleField = new DoubleField();
    doubleField.setValue(12345.09827D);

    FloatField floatField = new FloatField();
    floatField.setValue(12345.09827F);

    IntegerField integerField = new IntegerField();
    integerField.setValue(2147483647);

    LongField longField = new LongField();
    longField.setValue(4294967296L);

    ShortField shortField = new ShortField();
    shortField.addValidator(new MinNumberValidator<Short>((short) 0));
    shortField.addValidator(new MaxNumberValidator<Short>((short) 32767));
    shortField.setValue((short) 32767);

    FieldLabel bigDecimalFieldLabel = new FieldLabel(bigDecimalField, "BigDecimalField");
    FieldLabel bigIntegerFieldLabel = new FieldLabel(bigIntegerField, "BigIntegerField");
    FieldLabel doubleFieldLabel = new FieldLabel(doubleField, "DoubleField");
    FieldLabel floatFieldLabel = new FieldLabel(floatField, "FloatField");
    FieldLabel integerFieldLabel = new FieldLabel(integerField, "IntegerField");
    FieldLabel longFieldLabel = new FieldLabel(longField, "LongField");
    FieldLabel shortFieldLabel = new FieldLabel(shortField, "ShortField");

    VerticalPanel widget = new VerticalPanel();
    widget.setSpacing(VERTICAL_SPACING);
    widget.add(bigDecimalFieldLabel);
    widget.add(bigIntegerFieldLabel);
    widget.add(doubleFieldLabel);
    widget.add(floatFieldLabel);
    widget.add(integerFieldLabel);
    widget.add(longFieldLabel);
    widget.add(shortFieldLabel);

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
    widget.setSpacing(VERTICAL_SPACING);
    widget.add(bigDecimalFieldLabel);
    widget.add(bigIntegerFieldLabel);
    widget.add(doubleFieldLabel);
    widget.add(floatFieldLabel);
    widget.add(integerFieldLabel);
    widget.add(longFieldLabel);
    widget.add(shortFieldLabel);

    return widget;
  }

  private void createPasswordField() {
    PasswordField passwordField = new PasswordField();
    passwordField.setValue("abcdefghijkl");

    FieldLabel fieldLabel = new FieldLabel(passwordField, "PasswordField");
    verticalPanel.add(fieldLabel);
  }

  private void createRadio() {
    Radio radio1 = new Radio();
    CheckBox radio2 = new Radio();
    CheckBox radio3 = new Radio();

    radio1.setBoxLabel("Yes");
    radio2.setBoxLabel("No");
    radio3.setBoxLabel("Other");

    ToggleGroup toggleGroup = new ToggleGroup();
    toggleGroup.add(radio1);
    toggleGroup.add(radio2);
    toggleGroup.add(radio3);

    HorizontalPanel horizontalPanel = new HorizontalPanel();
    horizontalPanel.add(radio1);
    horizontalPanel.add(radio2);
    horizontalPanel.add(radio3);

    FieldLabel fieldLabel = new FieldLabel(horizontalPanel, "Radio");
    verticalPanel.add(fieldLabel);
  }

  private void createSimpleComboBox() {
    SimpleComboBox<Feedback> simpleComboBox = new SimpleComboBox<Feedback>(new LabelProvider<Feedback>() {
      @Override
      public String getLabel(Feedback item) {
        return item.toString().substring(0, 1) + item.toString().substring(1).toLowerCase();
      }
    });
    simpleComboBox.add(Feedback.APPEND);
    simpleComboBox.add(Feedback.INSERT);
    simpleComboBox.add(Feedback.BOTH);

    FieldLabel fieldLabel = new FieldLabel(simpleComboBox, "SimpleComboBox");
    verticalPanel.add(fieldLabel);
  }

  private void createSlider() {
    Slider slider = new Slider();

    FieldLabel fieldLabel = new FieldLabel(slider, "Slider");
    verticalPanel.add(fieldLabel);
  }

  private void createSplitButton() {
    Menu menu = new Menu();
    menu.add(new MenuItem("Menu Item 1"));
    menu.add(new MenuItem("Menu Item 2"));
    menu.add(new MenuItem("Menu Item 3"));

    SplitButton splitButton = new SplitButton();
    splitButton.setValue("Select");
    splitButton.setMenu(menu);

    FieldLabel fieldLabel = new FieldLabel(splitButton, "SplitButton");
    verticalPanel.add(fieldLabel);
  }

  private void createStringComboBox() {
    StringComboBox stringComboBox = new StringComboBox();
    stringComboBox.add("Apples");
    stringComboBox.add("Oranges");
    stringComboBox.add("Bananas");

    FieldLabel fieldLabel = new FieldLabel(stringComboBox, "StringComboBox");
    verticalPanel.add(fieldLabel);
  }

  private void createTextArea() {
    TextArea textArea = new TextArea();

    FieldLabel fieldLabel = new FieldLabel(textArea, "TextArea");
    verticalPanel.add(fieldLabel);
  }

  private void createTextButton() {
    TextButton textButton = new TextButton("Button");

    FieldLabel fieldLabel = new FieldLabel(textButton, "TextButton");
    verticalPanel.add(fieldLabel);
  }

  private void createTextField() {
    TextField textField = new TextField();
    textField.addValidator(new MinLengthValidator(50));
    textField.addValidator(new MaxLengthValidator(255));
    textField.addValidator(new RegExValidator("^[^a-z]+$", "Only uppercase letters allowed"));

    FieldLabel fieldLabel = new FieldLabel(textField, "TextField");
    verticalPanel.add(fieldLabel);
  }

  private void createTimeField() {
    TimeField timeField = new TimeField();

    FieldLabel fieldLabel = new FieldLabel(timeField, "TimeField");
    verticalPanel.add(fieldLabel);
  }

  private void createToggleButton() {
    ToggleButton toggleButton = new ToggleButton("Button");

    FieldLabel fieldLabel = new FieldLabel(toggleButton, "ToggleButton");
    verticalPanel.add(fieldLabel);
  }

  @Override
  public void onModuleLoad() {
    new ExampleContainer(this).setMinHeight(MIN_HEIGHT).setMinWidth(MIN_WIDTH).doStandalone();
  }

}