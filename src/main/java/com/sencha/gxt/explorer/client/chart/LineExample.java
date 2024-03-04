/**
 * Sencha GXT 1.0.0-SNAPSHOT - Sencha for GWT
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
import com.sencha.gxt.chart.client.chart.Chart.Position;
import com.sencha.gxt.chart.client.chart.Legend;
import com.sencha.gxt.chart.client.chart.axis.CategoryAxis;
import com.sencha.gxt.chart.client.chart.axis.NumericAxis;
import com.sencha.gxt.chart.client.chart.series.LineSeries;
import com.sencha.gxt.chart.client.chart.series.Primitives;
import com.sencha.gxt.chart.client.draw.Color;
import com.sencha.gxt.chart.client.draw.RGB;
import com.sencha.gxt.chart.client.draw.path.PathSprite;
import com.sencha.gxt.chart.client.draw.sprite.Sprite;
import com.sencha.gxt.chart.client.draw.sprite.TextSprite;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.LabelProvider;
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
    name = "Line Chart",
    category = "Charts",
    icon = "linechart",
    classes = { Data.class, TestData.class },
    minHeight = LineExample.MIN_HEIGHT,
    minWidth = LineExample.MIN_WIDTH
)
public class LineExample implements IsWidget, EntryPoint {

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
      store.addAll(TestData.getData(8, 20, 100));

      TextSprite title = new TextSprite("Number of Hits");
      title.setFontSize(18);
      
      PathSprite odd = new PathSprite();
      odd.setOpacity(1);
      odd.setFill(new Color("#ddd"));
      odd.setStroke(new Color("#bbb"));
      odd.setStrokeWidth(0.5);
      
      NumericAxis<Data> axis = new NumericAxis<Data>();
      axis.setPosition(Position.LEFT);
      axis.addField(dataAccess.data1());
      axis.addField(dataAccess.data2());
      axis.addField(dataAccess.data3());
      axis.setTitleConfig(title);
      axis.setMinorTickSteps(1);
      axis.setDisplayGrid(true);
      axis.setGridOddConfig(odd);
      axis.setMinimum(0);
      axis.setMaximum(100);

      title = new TextSprite("Month of the Year");
      title.setFontSize(18);
      
      CategoryAxis<Data, String> catAxis = new CategoryAxis<Data, String>();
      catAxis.setPosition(Position.BOTTOM);
      catAxis.setField(dataAccess.name());
      catAxis.setTitleConfig(title);
      catAxis.setLabelProvider(new LabelProvider<String>() {
        @Override
        public String getLabel(String item) {
          return item.substring(0, 3);
        }
      });
      
      Sprite marker = Primitives.square(0, 0, 6);
      marker.setFill(new RGB(194, 0, 36));

      final LineSeries<Data> series = new LineSeries<Data>();
      series.setYAxisPosition(Position.LEFT);
      series.setYField(dataAccess.data1());
      series.setStroke(new RGB(194, 0, 36));
      series.setShowMarkers(true);
      series.setMarkerConfig(marker);
      series.setHighlighting(true);

      marker = Primitives.circle(0, 0, 6);
      marker.setFill(new RGB(240, 165, 10));
      
      final LineSeries<Data> series2 = new LineSeries<Data>();
      series2.setYAxisPosition(Position.LEFT);
      series2.setYField(dataAccess.data2());
      series2.setStroke(new RGB(240, 165, 10));
      series2.setShowMarkers(true);
      series2.setSmooth(true);
      series2.setMarkerConfig(marker);
      series2.setHighlighting(true);

      marker = Primitives.diamond(0, 0, 6);
      marker.setFill(new RGB(32, 68, 186));
      
      final LineSeries<Data> series3 = new LineSeries<Data>();
      series3.setYAxisPosition(Position.LEFT);
      series3.setYField(dataAccess.data3());
      series3.setStroke(new RGB(32, 68, 186));
      series3.setShowMarkers(true);
      series3.setSmooth(true);
      series3.setFill(new RGB(32, 68, 186));
      series3.setMarkerConfig(marker);
      series3.setHighlighting(true);

      final Legend<Data> legend = new Legend<Data>();
      legend.setItemHighlighting(true);
      legend.setItemHiding(true);
      legend.getBorderConfig().setStrokeWidth(0);

      final Chart<Data> chart = new Chart<Data>();
      chart.setStore(store);
      chart.setShadowChart(false);
      chart.addAxis(axis);
      chart.addAxis(catAxis);
      chart.addSeries(series);
      chart.addSeries(series2);
      chart.addSeries(series3);
      chart.setLegend(legend);
      chart.setDefaultInsets(30);

      TextButton regenerate = new TextButton("Reload Data");
      regenerate.addSelectHandler(new SelectHandler() {
        @Override
        public void onSelect(SelectEvent event) {
          store.clear();
          store.addAll(TestData.getData(8, 20, 100));
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
      panel.setHeading("Line Chart");
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
