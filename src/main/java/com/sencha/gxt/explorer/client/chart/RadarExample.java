package com.sencha.gxt.explorer.client.chart;

import java.util.List;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor.Path;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.chart.client.chart.Chart;
import com.sencha.gxt.chart.client.chart.axis.RadialAxis;
import com.sencha.gxt.chart.client.chart.series.Primitives;
import com.sencha.gxt.chart.client.chart.series.RadarSeries;
import com.sencha.gxt.chart.client.chart.series.Series;
import com.sencha.gxt.chart.client.chart.series.SeriesRenderer;
import com.sencha.gxt.chart.client.draw.Color;
import com.sencha.gxt.chart.client.draw.RGB;
import com.sencha.gxt.chart.client.draw.sprite.Sprite;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;
import com.sencha.gxt.examples.resources.client.TestData;
import com.sencha.gxt.examples.resources.client.model.Data;
import com.sencha.gxt.explorer.client.app.ui.ExampleContainer;
import com.sencha.gxt.explorer.client.model.Example.Detail;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.button.ToggleButton;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.toolbar.ToolBar;

@Detail(
    name = "Radar Chart",
    category = "Charts",
    icon = "radarchart",
    classes = { Data.class, TestData.class },
    minHeight = RadarExample.MIN_HEIGHT,
    minWidth = RadarExample.MIN_WIDTH
)
public class RadarExample implements IsWidget, EntryPoint {

  public interface DataPropertyAccess extends PropertyAccess<Data> {
    ValueProvider<Data, Double> data1();

    ValueProvider<Data, Double> data2();

    ValueProvider<Data, Double> data3();

    ValueProvider<Data, String> name();

    @Path("id")
    ModelKeyProvider<Data> nameKey();
  }

  protected static final int MIN_HEIGHT = 480;
  protected static final int MIN_WIDTH = 640;

  private static final DataPropertyAccess dataAccess = GWT.create(DataPropertyAccess.class);

  private Color[] colors = {new RGB("#6d9824"), new RGB("#87146e"), new RGB("#2a9196")};
  private Color[] currentColor = {Color.NONE, Color.NONE, Color.NONE};
  private double strokeWidth = 2;
  private double opacity = 1;
  private ContentPanel panel;

  @Override
  public Widget asWidget() {
    if (panel == null) {
      final ListStore<Data> store = new ListStore<Data>(dataAccess.nameKey());
      store.addAll(TestData.getData(12, 20, 100));

      RadialAxis<Data, String> axis = new RadialAxis<Data, String>();
      axis.setCategoryField(dataAccess.name());
      axis.setDisplayGrid(true);

      Sprite marker = Primitives.circle(0, 0, 4);
      marker.setFill(colors[0]);

      final RadarSeries<Data> radar = new RadarSeries<Data>();
      radar.setYField(dataAccess.data1());
      radar.setStroke(colors[0]);
      radar.setShowMarkers(true);
      radar.setMarkerConfig(marker);
      radar.setLineRenderer(createRenderer(0));

      marker = Primitives.diamond(0, 0, 4);
      marker.setFill(colors[1]);

      final RadarSeries<Data> radar2 = new RadarSeries<Data>();
      radar2.setYField(dataAccess.data2());
      radar2.setStroke(colors[1]);
      radar2.setShowMarkers(true);
      radar2.setMarkerConfig(marker);
      radar2.setLineRenderer(createRenderer(1));

      marker = Primitives.square(0, 0, 4);
      marker.setFill(colors[2]);

      final RadarSeries<Data> radar3 = new RadarSeries<Data>();
      radar3.setYField(dataAccess.data3());
      radar3.setStroke(colors[2]);
      radar3.setShowMarkers(true);
      radar3.setMarkerConfig(marker);
      radar3.setLineRenderer(createRenderer(2));

      final Chart<Data> chart = new Chart<Data>();
      chart.setStore(store);
      chart.addAxis(axis);
      chart.addSeries(radar);
      chart.addSeries(radar2);
      chart.addSeries(radar3);
      chart.setDefaultInsets(30);

      TextButton regenerate = new TextButton("Reload Data");
      regenerate.addSelectHandler(new SelectHandler() {
        @Override
        public void onSelect(SelectEvent event) {
          store.clear();
          store.addAll(TestData.getData(12, 20, 100));
          chart.redrawChart();
        }
      });

      ToggleButton animation = new ToggleButton("Animate");
      animation.addValueChangeHandler(new ValueChangeHandler<Boolean>() {
        @Override
        public void onValueChange(ValueChangeEvent<Boolean> event) {
          chart.setAnimated(event.getValue());
        }
      });
      animation.setValue(true, true);

      ToggleButton shadow = new ToggleButton("Shadow");
      shadow.addValueChangeHandler(new ValueChangeHandler<Boolean>() {
        @Override
        public void onValueChange(ValueChangeEvent<Boolean> event) {
          chart.setShadowChart(event.getValue());
          chart.redrawChart();
        }
      });
      shadow.setValue(false);

      ToggleButton fill = new ToggleButton("Fill");
      fill.addValueChangeHandler(new ValueChangeHandler<Boolean>() {
        @Override
        public void onValueChange(ValueChangeEvent<Boolean> event) {
          if (event.getValue()) {
            opacity = 0.4;
            currentColor[0] = colors[0];
            currentColor[1] = colors[1];
            currentColor[2] = colors[2];
            strokeWidth = Double.NaN;
          } else {
            opacity = 1;
            currentColor[0] = Color.NONE;
            currentColor[1] = Color.NONE;
            currentColor[2] = Color.NONE;
            strokeWidth = 2;
          }

          List<Series<Data>> series = chart.getSeries();
          for (int i = 0; i < series.size(); i++) {
            RadarSeries<Data> radar = (RadarSeries<Data>) series.get(i);
            radar.setShowMarkers(!event.getValue());
          }
          chart.redrawChart();
        }
      });
      fill.setValue(true, true);

      ToolBar toolBar = new ToolBar();
      toolBar.add(regenerate);
      toolBar.add(animation);
      toolBar.add(shadow);
      toolBar.add(fill);

      VerticalLayoutContainer layout = new VerticalLayoutContainer();
      layout.add(toolBar, new VerticalLayoutData(1, -1));
      layout.add(chart, new VerticalLayoutData(1, 1));

      panel = new ContentPanel();
      panel.setHeading("Radar Chart");
      panel.add(layout);
    }

    return panel;
  }

  public SeriesRenderer<Data> createRenderer(final int seriesIndex) {
    return new SeriesRenderer<Data>() {
      @Override
      public void spriteRenderer(Sprite sprite, int index, ListStore<Data> store) {
        sprite.setStrokeWidth(strokeWidth);
        sprite.setOpacity(opacity);
        sprite.setFill(currentColor[seriesIndex]);
        sprite.redraw();
      }
    };
  }

  @Override
  public void onModuleLoad() {
    new ExampleContainer(this)
        .setMinHeight(MIN_HEIGHT)
        .setMinWidth(MIN_WIDTH)
        .doStandalone();
  }

}
