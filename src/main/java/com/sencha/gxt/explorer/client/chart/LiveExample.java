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

import java.util.Date;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor.Path;
import com.google.gwt.event.logical.shared.AttachEvent;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.datepicker.client.CalendarUtil;
import com.sencha.gxt.chart.client.chart.Chart;
import com.sencha.gxt.chart.client.chart.Chart.Position;
import com.sencha.gxt.chart.client.chart.axis.NumericAxis;
import com.sencha.gxt.chart.client.chart.axis.TimeAxis;
import com.sencha.gxt.chart.client.chart.series.LineSeries;
import com.sencha.gxt.chart.client.chart.series.Primitives;
import com.sencha.gxt.chart.client.draw.RGB;
import com.sencha.gxt.chart.client.draw.sprite.Sprite;
import com.sencha.gxt.chart.client.draw.sprite.TextSprite;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.LabelProvider;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;
import com.sencha.gxt.examples.resources.client.TestData;
import com.sencha.gxt.examples.resources.client.model.Data;
import com.sencha.gxt.examples.resources.client.model.Site;
import com.sencha.gxt.explorer.client.app.ui.ExampleContainer;
import com.sencha.gxt.explorer.client.model.Example.Detail;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.button.ToggleButton;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;
import com.sencha.gxt.widget.core.client.toolbar.ToolBar;

@Detail(
    name = "Live Chart",
    category = "Charts",
    icon = "livechart",
    classes = { Data.class, TestData.class },
    minHeight = LiveExample.MIN_HEIGHT,
    minWidth = LiveExample.MIN_WIDTH
)
public class LiveExample implements IsWidget, EntryPoint {

  public interface SitePropertyAccess extends PropertyAccess<Site> {
    ValueProvider<Site, Date> date();

    @Path("date")
    ModelKeyProvider<Site> nameKey();

    ValueProvider<Site, Double> veins();

    ValueProvider<Site, Double> views();

    ValueProvider<Site, Double> visits();
  }

  protected static final int MIN_HEIGHT = 480;
  protected static final int MIN_WIDTH = 640;

  private static final SitePropertyAccess siteAccess = GWT.create(SitePropertyAccess.class);
  private static final DateTimeFormat f = DateTimeFormat.getFormat("MMM d");

  private Timer update;
  private ContentPanel panel;

  @Override
  public Widget asWidget() { 
    if (panel == null) {
      final ListStore<Site> store = new ListStore<Site>(siteAccess.nameKey());
      
      Date initial = f.parse("Feb 1");
      for (int i = 0; i < 7; i++) {
        store.add(new Site(initial, Math.random() * 20 + 80, Math.random() * 20 + 40, Math.random() * 20));
        initial = CalendarUtil.copyDate(initial);
        CalendarUtil.addDaysToDate(initial, 1);
      }
      
      TextSprite title = new TextSprite("Number of Hits");
      title.setFontSize(18);

      NumericAxis<Site> axis = new NumericAxis<Site>();
      axis.setPosition(Position.LEFT);
      axis.addField(siteAccess.visits());
      axis.setTitleConfig(title);
      axis.setDisplayGrid(true);
      axis.setMinimum(0);
      axis.setMaximum(100);
      
      final TimeAxis<Site> time = new TimeAxis<Site>();
      time.setField(siteAccess.date());
      time.setStartDate(f.parse("Feb 1"));
      time.setEndDate(f.parse("Feb 7"));
      time.setLabelProvider(new LabelProvider<Date>() {
        @Override
        public String getLabel(Date item) {
          return f.format(item);
        }
      });
      
      Sprite marker = Primitives.circle(0, 0, 6);
      marker.setFill(new RGB(148, 174, 10));

      LineSeries<Site> series1 = new LineSeries<Site>();
      series1.setYAxisPosition(Position.LEFT);
      series1.setYField(siteAccess.visits());
      series1.setStroke(new RGB(148, 174, 10));
      series1.setShowMarkers(true);
      series1.setMarkerIndex(1);
      series1.setMarkerConfig(marker);
      
      marker = Primitives.cross(0, 0, 6);
      marker.setFill(new RGB(17, 95, 166));

      LineSeries<Site> series2 = new LineSeries<Site>();
      series2.setYAxisPosition(Position.LEFT);
      series2.setYField(siteAccess.views());
      series2.setStroke(new RGB(17, 95, 166));
      series2.setShowMarkers(true);
      series2.setMarkerIndex(1);
      series2.setMarkerConfig(marker);
     
      marker = Primitives.diamond(0, 0, 6);
      marker.setFill(new RGB(166, 17, 32));

      LineSeries<Site> series3 = new LineSeries<Site>();
      series3.setYAxisPosition(Position.LEFT);
      series3.setYField(siteAccess.veins());
      series3.setStroke(new RGB(166, 17, 32));
      series3.setShowMarkers(true);
      series3.setMarkerIndex(1);
      series3.setMarkerConfig(marker);
      
      final Chart<Site> chart = new Chart<Site>();
      chart.setDefaultInsets(30);
      chart.setStore(store);
      chart.addAxis(axis);
      chart.addAxis(time);
      chart.addSeries(series1);
      chart.addSeries(series2);
      chart.addSeries(series3);

      update = new Timer() {
        @Override
        public void run() {
          Date startDate = CalendarUtil.copyDate(time.getStartDate());
          Date endDate = CalendarUtil.copyDate(time.getEndDate());
          CalendarUtil.addDaysToDate(startDate, 1);
          CalendarUtil.addDaysToDate(endDate, 1);
          chart.getStore().add(new Site(endDate, Math.random() * 20 + 80, Math.random() * 20 + 40, Math.random() * 20));
          time.setStartDate(startDate);
          time.setEndDate(endDate);
          chart.redrawChart();
        }
      };

      ToggleButton animation = new ToggleButton("Animate");
      animation.addValueChangeHandler(new ValueChangeHandler<Boolean>() {
        @Override
        public void onValueChange(ValueChangeEvent<Boolean> event) {
          chart.setAnimated(event.getValue());
        }
      });
      animation.setValue(true, true);

      ToolBar toolBar = new ToolBar();
      toolBar.add(animation);

      VerticalLayoutContainer layout = new VerticalLayoutContainer();
      layout.add(toolBar, new VerticalLayoutData(1, -1));
      layout.add(chart, new VerticalLayoutData(1, 1));

      panel = new ContentPanel();
      panel.setHeading("Live Chart");
      panel.add(layout);
      panel.addAttachHandler(new AttachEvent.Handler() {
        @Override
        public void onAttachOrDetach(AttachEvent event) {
          if (event.isAttached()) {
            update.scheduleRepeating(1000);
          } else {
            update.cancel();
          }
        }
      });
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
