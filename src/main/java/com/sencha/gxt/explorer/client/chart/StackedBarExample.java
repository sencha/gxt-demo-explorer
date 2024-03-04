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
import com.sencha.gxt.chart.client.chart.Chart.Position;
import com.sencha.gxt.chart.client.chart.Legend;
import com.sencha.gxt.chart.client.chart.axis.CategoryAxis;
import com.sencha.gxt.chart.client.chart.axis.NumericAxis;
import com.sencha.gxt.chart.client.chart.series.BarSeries;
import com.sencha.gxt.chart.client.chart.series.SeriesLabelProvider;
import com.sencha.gxt.chart.client.chart.series.SeriesToolTipConfig;
import com.sencha.gxt.chart.client.draw.RGB;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.LabelProvider;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;
import com.sencha.gxt.examples.resources.client.TestData;
import com.sencha.gxt.examples.resources.client.model.Movies;
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
    name = "Stacked Bar Chart",
    category = "Charts",
    icon = "stackedbarchart",
    classes = { Movies.class, TestData.class },
    minHeight = StackedBarExample.MIN_HEIGHT,
    minWidth = StackedBarExample.MIN_WIDTH
)
public class StackedBarExample implements IsWidget, EntryPoint {

  public interface MoviesPropertyAccess extends PropertyAccess<Movies> {
    ValueProvider<Movies, Double> action();

    ValueProvider<Movies, Double> comedy();

    ValueProvider<Movies, Double> drama();

    @Path("year")
    ModelKeyProvider<Movies> nameKey();

    ValueProvider<Movies, Double> thriller();

    ValueProvider<Movies, Integer> year();
  }

  protected static final int MIN_HEIGHT = 480;
  protected static final int MIN_WIDTH = 640;

  private static final MoviesPropertyAccess moviesAccess = GWT.create(MoviesPropertyAccess.class);

  private LabelProvider<Number> million = new LabelProvider<Number>() {
    @Override
    public String getLabel(Number item) {
      int value = item.intValue() / 1000000;
      return value + "M";
    }
  };

  private ContentPanel panel;

  @Override
  public Widget asWidget() {
    if (panel == null) {
      final ListStore<Movies> store = new ListStore<Movies>(moviesAccess.nameKey());
      store.addAll(TestData.getMovieData(2005, 5, 0, 160000000));

      NumericAxis<Movies> axis = new NumericAxis<Movies>();
      axis.setPosition(Position.BOTTOM);
      axis.addField(moviesAccess.comedy());
      axis.addField(moviesAccess.action());
      axis.addField(moviesAccess.drama());
      axis.addField(moviesAccess.thriller());
      axis.setDisplayGrid(true);
      axis.setLabelProvider(million);

      CategoryAxis<Movies, Integer> catAxis = new CategoryAxis<Movies, Integer>();
      catAxis.setPosition(Position.LEFT);
      catAxis.setField(moviesAccess.year());

      SeriesToolTipConfig<Movies> config = new SeriesToolTipConfig<Movies>();
      config.setLabelProvider(new SeriesLabelProvider<Movies>() {
        @Override
        public String getLabel(Movies item, ValueProvider<? super Movies, ? extends Number> valueProvider) {
          return valueProvider.getValue(item).intValue() / 1000000 + "M";
        }
      });
      config.setHideDelay(2000);
      
      final BarSeries<Movies> bar = new BarSeries<Movies>();
      bar.setYAxisPosition(Position.BOTTOM);
      bar.addYField(moviesAccess.comedy());
      bar.addYField(moviesAccess.action());
      bar.addYField(moviesAccess.drama());
      bar.addYField(moviesAccess.thriller());
      bar.addColor(new RGB(148, 174, 10));
      bar.addColor(new RGB(17, 95, 166));
      bar.addColor(new RGB(166, 17, 32));
      bar.addColor(new RGB(255, 136, 9));
      bar.setStacked(true);
      bar.setToolTipConfig(config);

      final Legend<Movies> legend = new Legend<Movies>();
      legend.setItemHiding(true);
      legend.setItemHighlighting(true);
      legend.getBorderConfig().setStrokeWidth(0);

      final Chart<Movies> chart = new Chart<Movies>();
      chart.setStore(store);
      chart.setShadowChart(false);
      chart.addAxis(axis);
      chart.addAxis(catAxis);
      chart.addSeries(bar);
      chart.setLegend(legend);
      chart.setDefaultInsets(30);

      TextButton regenerate = new TextButton("Reload Data");
      regenerate.addSelectHandler(new SelectHandler() {
        @Override
        public void onSelect(SelectEvent event) {
          store.clear();
          store.addAll(TestData.getMovieData(2005, 5, 0, 160000000));
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
      panel.setHeading("Stacked Bar Chart");
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
