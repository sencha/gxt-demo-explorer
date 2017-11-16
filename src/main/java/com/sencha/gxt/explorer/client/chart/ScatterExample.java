package com.sencha.gxt.explorer.client.chart;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor.Path;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.chart.client.chart.Chart;
import com.sencha.gxt.chart.client.chart.Chart.Position;
import com.sencha.gxt.chart.client.chart.axis.NumericAxis;
import com.sencha.gxt.chart.client.chart.series.Primitives;
import com.sencha.gxt.chart.client.chart.series.ScatterSeries;
import com.sencha.gxt.chart.client.draw.RGB;
import com.sencha.gxt.chart.client.draw.sprite.Sprite;
import com.sencha.gxt.chart.client.draw.sprite.TextSprite;
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
    name = "Scatter Chart",
    category = "Charts",
    icon = "scatterchart",
    classes = { Data.class, TestData.class },
    minHeight = ScatterExample.MIN_HEIGHT,
    minWidth = ScatterExample.MIN_WIDTH
)
public class ScatterExample implements IsWidget, EntryPoint {

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

  private ContentPanel panel;

  public Widget asWidget() {
    if (panel == null) {
      final ListStore<Data> store = new ListStore<Data>(dataAccess.nameKey());
      store.addAll(TestData.getData(12, 0, 100));

      final Chart<Data> chart = new Chart<Data>();
      chart.setStore(store);
      chart.setShadowChart(false);
      chart.setDefaultInsets(30);

      NumericAxis<Data> axis = new NumericAxis<Data>();
      axis.setPosition(Position.LEFT);
      axis.addField(dataAccess.data1());
      axis.addField(dataAccess.data2());
      axis.addField(dataAccess.data3());
      axis.setMinimum(0);
      axis.setMaximum(100);
      TextSprite title = new TextSprite("Number of Hits");
      title.setFontSize(18);
      axis.setTitleConfig(title);
      chart.addAxis(axis);

      final ScatterSeries<Data> series = new ScatterSeries<Data>();
      series.setYAxisPosition(Position.LEFT);
      series.setYField(dataAccess.data1());
      Sprite marker = Primitives.cross(0, 0, 8);
      marker.setFill(RGB.GREEN);
      series.setMarkerConfig(marker);
      series.setHighlighting(true);
      chart.addSeries(series);

      final ScatterSeries<Data> series2 = new ScatterSeries<Data>();
      series2.setYAxisPosition(Position.LEFT);
      series2.setYField(dataAccess.data2());
      marker = Primitives.circle(0, 0, 8);
      marker.setFill(RGB.RED);
      series2.setMarkerConfig(marker);
      series2.setHighlighting(true);
      chart.addSeries(series2);

      final ScatterSeries<Data> series3 = new ScatterSeries<Data>();
      series3.setYAxisPosition(Position.LEFT);
      series3.setYField(dataAccess.data3());
      marker = Primitives.plus(0, 0, 8);
      marker.setFill(RGB.BLUE);
      series3.setMarkerConfig(marker);
      series3.setHighlighting(true);
      chart.addSeries(series3);

      TextButton regenerate = new TextButton("Reload Data");
      regenerate.addSelectHandler(new SelectHandler() {
        @Override
        public void onSelect(SelectEvent event) {
          store.clear();
          store.addAll(TestData.getData(12, 0, 100));
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

      ToolBar toolBar = new ToolBar();
      toolBar.add(regenerate);
      toolBar.add(animation);
      toolBar.add(shadow);

      VerticalLayoutContainer layout = new VerticalLayoutContainer();
      layout.add(toolBar, new VerticalLayoutData(1, -1));
      layout.add(chart, new VerticalLayoutData(1, 1));

      panel = new ContentPanel();
      panel.setHeading("Scatter Chart");
      panel.add(layout);
    }

    return panel;

  }

  @Override
  public void onModuleLoad() {
    new ExampleContainer(this)
        .setMinHeight(MIN_HEIGHT)
        .setMinWidth(MIN_WIDTH)
        .doStandalone();
  }

}
