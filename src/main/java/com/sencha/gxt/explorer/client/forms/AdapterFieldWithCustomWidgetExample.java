package com.sencha.gxt.explorer.client.forms;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.explorer.client.app.ui.ExampleContainer;
import com.sencha.gxt.explorer.client.model.Example.Detail;
import com.sencha.gxt.widget.core.client.Composite;
import com.sencha.gxt.widget.core.client.FramedPanel;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;
import com.sencha.gxt.widget.core.client.form.AdapterField;
import com.sencha.gxt.widget.core.client.form.FieldLabel;
import com.sencha.gxt.widget.core.client.form.TextField;

@Detail(
  name = "Adapter Field with Custom Widget",
  category = "Forms",
  icon = "advancedforms",
  minHeight = AdapterFieldWithCustomWidgetExample.MIN_HEIGHT,
  minWidth = AdapterFieldWithCustomWidgetExample.MIN_WIDTH)
public class AdapterFieldWithCustomWidgetExample implements IsWidget, EntryPoint {

  protected static final int MIN_HEIGHT = 480;
  protected static final int MIN_WIDTH = 720;

  private FramedPanel panel;

  @Override
  public Widget asWidget() {
    if (panel == null) {
      final MyCustomAdapter customAdapter = new MyCustomAdapter();
      customAdapter.setValue(new Data("Trees", "Flowers"));
      customAdapter.setBorders(true);

      VerticalLayoutContainer vlc = new VerticalLayoutContainer();
      vlc.add(customAdapter, new VerticalLayoutData(1, -1, new Margins(20)));

      panel = new FramedPanel();
      panel.setHeading("Adapter Field Example");
      panel.add(vlc);
    }

    return panel;
  }

  /**
   * Custom adapter which sets/gets the value to/from the widget.
   */
  public class MyCustomAdapter extends AdapterField<Data> {
    public MyCustomAdapter() {
      super(new CustomWidget());
    }

    @Override
    public void setValue(Data value) {
      ((CustomWidget) getWidget()).setValue(value);
    }

    @Override
    public Data getValue() {
      return ((CustomWidget) getWidget()).getValue();
    }
  }

  /**
   * Custom widget for the adapter.
   */
  public class CustomWidget extends Composite {
    private TextField textBox1;
    private TextField textBox2;
    private Data value;
    private VerticalLayoutContainer vlc;

    public CustomWidget() {
      textBox1 = new TextField();
      textBox2 = new TextField();

      FieldLabel fieldLabel1 = new FieldLabel(textBox1, "Col 1");
      FieldLabel fieldLabel2 = new FieldLabel(textBox2, "Col 2");

      vlc = new VerticalLayoutContainer();
      vlc.add(fieldLabel1, new VerticalLayoutData(1, -1, new Margins(20)));
      vlc.add(fieldLabel2, new VerticalLayoutData(1, -1, new Margins(20)));
      initWidget(vlc);
    }

    public void setValue(Data value) {
      this.value = value;
      textBox1.setValue(value.getDataCol1());
      textBox2.setValue(value.getDataCol2());
    }

    public Data getValue() {
      value.setDataCol1(textBox1.getValue());
      value.setDataCol2(textBox2.getValue());
      return value;
    }
  }

  public class Data {
    String dataCol1 = "";
    String dataCol2 = "";

    public Data(String dataCol1, String dataCol2) {
      this.dataCol1 = dataCol1;
      this.dataCol2 = dataCol2;
    }

    public String getDataCol1() {
      return dataCol1;
    }

    public void setDataCol1(String dataCol1) {
      this.dataCol1 = dataCol1;
    }

    public String getDataCol2() {
      return dataCol2;
    }

    public void setDataCol2(String dataCol2) {
      this.dataCol2 = dataCol2;
    }
  }

  @Override
  public void onModuleLoad() {
    new ExampleContainer(this).setMinHeight(MIN_HEIGHT).setMinWidth(MIN_WIDTH).doStandalone();
  }

}
