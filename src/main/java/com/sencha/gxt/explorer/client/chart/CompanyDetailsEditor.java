package com.sencha.gxt.explorer.client.chart;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.editor.client.Editor;
import com.google.gwt.editor.client.SimpleBeanEditorDriver;
import com.google.gwt.event.logical.shared.HasValueChangeHandlers;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.client.TakesValue;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.chart.client.chart.Chart;
import com.sencha.gxt.chart.client.chart.axis.RadialAxis;
import com.sencha.gxt.chart.client.chart.series.Primitives;
import com.sencha.gxt.chart.client.chart.series.RadarSeries;
import com.sencha.gxt.chart.client.chart.series.SeriesRenderer;
import com.sencha.gxt.chart.client.draw.RGB;
import com.sencha.gxt.chart.client.draw.sprite.Sprite;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.examples.resources.client.model.Data;
import com.sencha.gxt.examples.resources.client.model.RadarData;
import com.sencha.gxt.examples.resources.client.model.RadarDataProperties;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.container.VBoxLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VBoxLayoutContainer.VBoxLayoutAlign;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;
import com.sencha.gxt.widget.core.client.form.DoubleSpinnerField;
import com.sencha.gxt.widget.core.client.form.Field;
import com.sencha.gxt.widget.core.client.form.FieldLabel;
import com.sencha.gxt.widget.core.client.form.TextField;

public class CompanyDetailsEditor implements Editor<Data>, IsWidget, HasValueChangeHandlers<Data>, TakesValue<Data> {

  interface Driver extends SimpleBeanEditorDriver<Data, CompanyDetailsEditor> {
  }
  
  private final static String[] radarLabels = {"Price", "Revenue %", "Growth %", "Product %", "Market %"};

  TextField name = new TextField();
  @Path("data1")
  DoubleSpinnerField price = new DoubleSpinnerField();
  @Path("data2")
  DoubleSpinnerField revenue = new DoubleSpinnerField();
  @Path("data3")
  DoubleSpinnerField growth = new DoubleSpinnerField();
  @Path("data4")
  DoubleSpinnerField product = new DoubleSpinnerField();
  @Path("data5")
  DoubleSpinnerField market = new DoubleSpinnerField();

  private final HandlerManager handlerManager = new HandlerManager(this);
  private final Driver driver = GWT.<Driver>create(Driver.class);
  private final RadarDataProperties radarProperties = GWT.<RadarDataProperties>create(RadarDataProperties.class);
  private final ListStore<RadarData> radarStore = new ListStore<RadarData>(radarProperties.nameKey());
  
  private Field<?>[] fields = new Field<?>[]{name, price, revenue, growth, product, market};
  private Chart<RadarData> radarChart;
  private ContentPanel panel;

  public CompanyDetailsEditor() {
    radarChart = createRadar();

    name.setReadOnly(true);

    initializeSpinner(price);
    initializeSpinner(revenue);
    initializeSpinner(growth);
    initializeSpinner(product);
    initializeSpinner(market);

    VBoxLayoutContainer container = new VBoxLayoutContainer();
    container.setVBoxLayoutAlign(VBoxLayoutAlign.CENTER);
    container.add(radarChart);
    container.add(new FieldLabel(name, "Name"));
    container.add(new FieldLabel(price, "Price $"));
    container.add(new FieldLabel(revenue, "Revenue %"));
    container.add(new FieldLabel(growth, "Growth %"));
    container.add(new FieldLabel(product, "Product %"));
    container.add(new FieldLabel(market, "Market %"));

    panel = new ContentPanel();
    panel.setHeading("Company Details");
    panel.addStyleName("white-bg");
    panel.add(container, new VerticalLayoutData(1, 1, new Margins(20, 0, 0, 0)));

    driver.initialize(this);
  }


  @Override
  public HandlerRegistration addValueChangeHandler(ValueChangeHandler<Data> handler) {
    return handlerManager.addHandler(ValueChangeEvent.getType(), handler);
  }

  @Override
  public Widget asWidget() {
    return panel;
  }

  @Override
  public void fireEvent(GwtEvent<?> event) {
    handlerManager.fireEvent(event);
  }

  @Override
  public Data getValue() {
    return driver.flush();
  }

  @Override
  public void setValue(final Data value) {
    persistFields();

    // If one of the spinners has focus with changes,
    // it needs to persist before editing new bean
    Scheduler.get().scheduleDeferred(new ScheduledCommand() {
      @Override
      public void execute() {
        resetFields();
        driver.edit(value);
        updateRadar(value);
      }
    });
  }

  private Chart<RadarData> createRadar() {
    RadialAxis<RadarData, String> axis = new RadialAxis<RadarData, String>();
    axis.setCategoryField(radarProperties.name());
    axis.setSteps(5);
    
    Sprite marker = Primitives.circle(0, 0, 4);
    marker.setFill(new RGB(69, 109, 159));

    RadarSeries<RadarData> radar = new RadarSeries<RadarData>();
    radar.setYField(radarProperties.data());
    radar.setFill(new RGB(194, 214, 240));
    radar.setStrokeWidth(0.5);
    radar.setShowMarkers(true);
    radar.setMarkerConfig(marker);
    radar.setLineRenderer(new SeriesRenderer<RadarData>() {
      @Override
      public void spriteRenderer(Sprite sprite, int index, ListStore<RadarData> store) {
        sprite.setOpacity(0.5);
      }
    });
    
    Chart<RadarData> chart = new Chart<RadarData>(320, 320);
    chart.setStore(radarStore);
    chart.setAnimated(true);
    chart.setDefaultInsets(50);
    chart.addAxis(axis);
    chart.addSeries(radar);

    return chart;
  }

  private void initializeSpinner(final DoubleSpinnerField spinner) {
    spinner.setIncrement(1d);
    spinner.setMinValue(0d);
    spinner.setMaxValue(100d);
    spinner.setAllowBlank(false);
    spinner.getPropertyEditor().setFormat(NumberFormat.getFormat("0.00"));
    spinner.addValueChangeHandler(new ValueChangeHandler<Double>() {
      @Override
      public void onValueChange(ValueChangeEvent<Double> event) {
        if (spinner.isValid()) {
          Data value = driver.flush();
          updateRadar(value);
          ValueChangeEvent.fire(CompanyDetailsEditor.this, value);
        }
      }
    });
  }

  private void resetFields() {
    for (Field field : fields) {
      field.clearInvalid();
    }
  }

  private void persistFields() {
    for (Field field : fields) {
      if (field.isEditing()) {
        field.finishEditing();
      }
    }
  }

  private void updateRadar(Data data) {
    radarStore.clear();

    radarStore.add(new RadarData(radarLabels[0], data.getData1()));
    radarStore.add(new RadarData(radarLabels[1], data.getData2()));
    radarStore.add(new RadarData(radarLabels[2], data.getData3()));
    radarStore.add(new RadarData(radarLabels[3], data.getData4()));
    radarStore.add(new RadarData(radarLabels[4], data.getData5()));

    radarChart.redrawChart();
  }

}
