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
import com.sencha.gxt.chart.client.chart.series.AreaSeries;
import com.sencha.gxt.chart.client.chart.series.SeriesRenderer;
import com.sencha.gxt.chart.client.draw.RGB;
import com.sencha.gxt.chart.client.draw.path.MoveTo;
import com.sencha.gxt.chart.client.draw.path.PathSprite;
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
    name = "Area Chart",
    category = "Charts",
    icon = "areachart",
    classes = { Data.class, TestData.class },
    minHeight = AreaExample.MIN_HEIGHT,
    minWidth = AreaExample.MIN_WIDTH
)
public class AreaExample implements IsWidget, EntryPoint {

  public interface DataPropertyAccess extends PropertyAccess<Data> {
    ValueProvider<Data, Double> data1();

    ValueProvider<Data, Double> data2();

    ValueProvider<Data, Double> data3();

    ValueProvider<Data, Double> data4();

    ValueProvider<Data, Double> data5();

    ValueProvider<Data, Double> data6();

    ValueProvider<Data, Double> data7();

    ValueProvider<Data, String> name();

    @Path("id")
    ModelKeyProvider<Data> nameKey();
  }

  protected static final int MIN_HEIGHT = 480;
  protected static final int MIN_WIDTH = 640;

  private static final DataPropertyAccess dataAccess = GWT.create(DataPropertyAccess.class);

  private ContentPanel panel;
  private Chart<Data> chart;

  @Override
  public Widget asWidget() {
    if (panel == null) {
      final ListStore<Data> store = new ListStore<Data>(dataAccess.nameKey());
      store.addAll(TestData.getData(12, 20, 100));

      TextSprite titleHits = new TextSprite("Number of Hits");
      titleHits.setFontSize(18);

      TextSprite titleMonthYear = new TextSprite("Month of the Year");
      titleMonthYear.setFontSize(18);

      PathSprite gridConfig = new PathSprite();
      gridConfig.setStroke(new RGB("#bbb"));
      gridConfig.setFill(new RGB("#ddd"));
      gridConfig.setZIndex(1);
      gridConfig.setStrokeWidth(1);

      NumericAxis<Data> axis = new NumericAxis<Data>();
      axis.setPosition(Position.LEFT);
      axis.addField(dataAccess.data1());
      axis.addField(dataAccess.data2());
      axis.addField(dataAccess.data3());
      axis.addField(dataAccess.data4());
      axis.addField(dataAccess.data5());
      axis.addField(dataAccess.data6());
      axis.addField(dataAccess.data7());
      axis.setGridOddConfig(gridConfig);
      axis.setDisplayGrid(true);
      axis.setTitleConfig(titleHits);
      axis.setMinorTickSteps(2);

      TextSprite labelConfig = new TextSprite();
      labelConfig.setRotation(315);

      CategoryAxis<Data, String> catAxis = new CategoryAxis<Data, String>();
      catAxis.setPosition(Position.BOTTOM);
      catAxis.setField(dataAccess.name());
      catAxis.setTitleConfig(titleMonthYear);
      catAxis.setDisplayGrid(true);
      catAxis.setLabelConfig(labelConfig);
      catAxis.setLabelPadding(-10);
      catAxis.setMinorTickSteps(5);
      catAxis.setLabelTolerance(20);

      final PathSprite highlightLine = new PathSprite();
      highlightLine.setHidden(true);
      highlightLine.addCommand(new MoveTo(0, 0));
      highlightLine.setZIndex(1000);
      highlightLine.setStrokeWidth(5);
      highlightLine.setStroke(new RGB("#444"));
      highlightLine.setOpacity(0.3);

      final AreaSeries<Data> series = new AreaSeries<Data>();
      series.setHighlighting(true);
      series.setYAxisPosition(Position.LEFT);
      series.addYField(dataAccess.data1());
      series.addYField(dataAccess.data2());
      series.addYField(dataAccess.data3());
      series.addYField(dataAccess.data4());
      series.addYField(dataAccess.data5());
      series.addYField(dataAccess.data6());
      series.addYField(dataAccess.data7());
      series.addColor(new RGB(148, 174, 10));
      series.addColor(new RGB(17, 95, 166));
      series.addColor(new RGB(166, 17, 32));
      series.addColor(new RGB(255, 136, 9));
      series.addColor(new RGB(255, 209, 62));
      series.addColor(new RGB(166, 17, 135));
      series.addColor(new RGB(36, 173, 154));
      series.setHighlightLineConfig(highlightLine);
      series.setRenderer(new SeriesRenderer<Data>() {
        @Override
        public void spriteRenderer(Sprite sprite, int index, ListStore<Data> store) {
          sprite.setOpacity(0.93);
          sprite.redraw();
        }
      });

      Legend<Data> legend = new Legend<Data>();
      legend.setItemHighlighting(true);
      legend.setItemHiding(true);
      legend.getBorderConfig().setStrokeWidth(0);

      chart = new Chart<Data>();
      chart.setStore(store);
      // Allow room for rotated labels
      chart.setDefaultInsets(30);
      chart.addAxis(axis);
      chart.addAxis(catAxis);
      chart.addSeries(series);
      chart.setLegend(legend);

      TextButton regenerate = new TextButton("Reload Data");
      regenerate.addSelectHandler(new SelectHandler() {
        @Override
        public void onSelect(SelectEvent event) {
          store.clear();
          store.addAll(TestData.getData(12, 20, 100));
          chart.redrawChart();
          series.setHighlightLineConfig(highlightLine);
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

      ToolBar toolBar = new ToolBar();
      toolBar.add(regenerate);
      toolBar.add(animation);
      toolBar.setLayoutData(new VerticalLayoutData(1, -1));

      VerticalLayoutContainer layout = new VerticalLayoutContainer();
      layout.add(toolBar, new VerticalLayoutData(1, -1));
      layout.add(chart, new VerticalLayoutData(1, 1));

      panel = new ContentPanel();
      panel.setHeading("Area Chart");
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
