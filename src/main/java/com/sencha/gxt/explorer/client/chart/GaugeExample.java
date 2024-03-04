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
package com.sencha.gxt.explorer.client.chart;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor.Path;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.chart.client.chart.Chart;
import com.sencha.gxt.chart.client.chart.axis.GaugeAxis;
import com.sencha.gxt.chart.client.chart.series.GaugeSeries;
import com.sencha.gxt.chart.client.draw.Color;
import com.sencha.gxt.chart.client.draw.RGB;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;
import com.sencha.gxt.examples.resources.client.TestData;
import com.sencha.gxt.examples.resources.client.model.Data;
import com.sencha.gxt.explorer.client.app.ui.ExampleContainer;
import com.sencha.gxt.explorer.client.model.Example.Detail;
import com.sencha.gxt.fx.client.easing.BackOut;
import com.sencha.gxt.fx.client.easing.Default;
import com.sencha.gxt.fx.client.easing.EasingFunction;
import com.sencha.gxt.fx.client.easing.ElasticIn;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.button.ToggleButton;
import com.sencha.gxt.widget.core.client.container.HorizontalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.HorizontalLayoutContainer.HorizontalLayoutData;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.toolbar.ToolBar;

@Detail(
    name = "Gauge Chart",
    category = "Charts",
    icon = "gaugechart",
    classes = { Data.class, TestData.class },
    minHeight = GaugeExample.MIN_HEIGHT,
    minWidth = GaugeExample.MIN_WIDTH
)
public class GaugeExample implements IsWidget, EntryPoint {

  public interface DataPropertyAccess extends PropertyAccess<Data> {
    ValueProvider<Data, Double> data1();

    ValueProvider<Data, Double> data2();

    ValueProvider<Data, Double> data3();

    ValueProvider<Data, Double> data4();

    ValueProvider<Data, String> name();

    @Path("id")
    ModelKeyProvider<Data> nameKey();
  }

  protected static final int MIN_HEIGHT = 480;
  protected static final int MIN_WIDTH = 640;

  private static final DataPropertyAccess dataAccess = GWT.create(DataPropertyAccess.class);

  private ContentPanel panel;

  @Override
  public Widget asWidget() {
    if (panel == null) {
      final ListStore<Data> store = new ListStore<Data>(dataAccess.nameKey());
      store.addAll(TestData.getData(1, 0, 100));

      final Chart<Data> chart1 = createGauge(store, 0, new RGB("#F49D10"), true, new Default(), dataAccess.data1());
      final Chart<Data> chart2 = createGauge(store, 30, new RGB("#82B525"), false, new Default(), dataAccess.data2());
      final Chart<Data> chart3 = createGauge(store, 80, new RGB("#3AA8CB"), false, new ElasticIn(), dataAccess.data3());
      final Chart<Data> chart4 = createGauge(store, 80, new RGB("#C44598"), true, new BackOut(), dataAccess.data4());

      TextButton regenerate = new TextButton("Reload Data");
      regenerate.addSelectHandler(new SelectHandler() {
        @Override
        public void onSelect(SelectEvent event) {
          store.clear();
          store.addAll(TestData.getData(1, 0, 100));
          chart1.redrawChart();
          chart2.redrawChart();
          chart3.redrawChart();
          chart4.redrawChart();
        }
      });

      ToggleButton animation = new ToggleButton("Animate");
      animation.addValueChangeHandler(new ValueChangeHandler<Boolean>() {
        @Override
        public void onValueChange(ValueChangeEvent<Boolean> event) {
          chart1.setAnimated(event.getValue());
          chart2.setAnimated(event.getValue());
          chart3.setAnimated(event.getValue());
          chart4.setAnimated(event.getValue());
        }
      });
      animation.setValue(true, true);

      ToolBar toolBar = new ToolBar();
      toolBar.add(regenerate);
      toolBar.add(animation);

      HorizontalLayoutContainer row1 = new HorizontalLayoutContainer();
      row1.add(chart1, new HorizontalLayoutData(0.5, 1.0, new Margins(20)));
      row1.add(chart2, new HorizontalLayoutData(0.5, 1.0, new Margins(20)));

      HorizontalLayoutContainer row2 = new HorizontalLayoutContainer();
      row2.add(chart3, new HorizontalLayoutData(0.5, 1.0, new Margins(20)));
      row2.add(chart4, new HorizontalLayoutData(0.5, 1.0, new Margins(20)));

      VerticalLayoutContainer layout = new VerticalLayoutContainer();
      layout.add(toolBar, new VerticalLayoutData(1, -1));
      layout.add(row1, new VerticalLayoutData(1.0, 0.5));
      layout.add(row2, new VerticalLayoutData(1.0, 0.5));

      panel = new ContentPanel();
      panel.setHeading("Gauge Chart");
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

  private Chart<Data> createGauge(ListStore<Data> store, double donut, Color color, boolean needle,
      EasingFunction easing, ValueProvider<Data, Double> provider) {
    GaugeAxis<Data> axis = new GaugeAxis<Data>();
    axis.setMargin(8);
    axis.setDisplayGrid(true);
    axis.setMinimum(0);
    axis.setMaximum(100);

    final GaugeSeries<Data> gauge = new GaugeSeries<Data>();
    gauge.addColor(color);
    gauge.addColor(new RGB("#ddd"));
    gauge.setAngleField(provider);
    gauge.setNeedle(needle);
    gauge.setDonut(donut);

    Chart<Data> chart = new Chart<Data>();
    chart.setStore(store);
    chart.setAnimationDuration(750);
    chart.setAnimationEasing(easing);
    chart.setDefaultInsets(35);
    chart.addAxis(axis);
    chart.addSeries(gauge);

    return chart;
  }

}
