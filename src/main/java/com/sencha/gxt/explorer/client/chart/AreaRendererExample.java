package com.sencha.gxt.explorer.client.chart;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor.Path;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.chart.client.chart.Chart;
import com.sencha.gxt.chart.client.chart.Chart.Position;
import com.sencha.gxt.chart.client.chart.Legend;
import com.sencha.gxt.chart.client.chart.axis.CategoryAxis;
import com.sencha.gxt.chart.client.chart.axis.NumericAxis;
import com.sencha.gxt.chart.client.chart.series.AreaSeries;
import com.sencha.gxt.chart.client.chart.series.SeriesLabelProvider;
import com.sencha.gxt.chart.client.chart.series.SeriesRenderer;
import com.sencha.gxt.chart.client.chart.series.SeriesToolTipConfig;
import com.sencha.gxt.chart.client.draw.Color;
import com.sencha.gxt.chart.client.draw.Gradient;
import com.sencha.gxt.chart.client.draw.RGB;
import com.sencha.gxt.chart.client.draw.Stop;
import com.sencha.gxt.chart.client.draw.path.MoveTo;
import com.sencha.gxt.chart.client.draw.path.PathSprite;
import com.sencha.gxt.chart.client.draw.sprite.Sprite;
import com.sencha.gxt.chart.client.draw.sprite.TextSprite;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;
import com.sencha.gxt.examples.resources.client.model.BrowserData;
import com.sencha.gxt.examples.resources.client.model.BrowserProxy;
import com.sencha.gxt.examples.resources.client.model.ExampleData;
import com.sencha.gxt.explorer.client.app.ui.ExampleContainer;
import com.sencha.gxt.explorer.client.model.Example.Detail;
import com.sencha.gxt.widget.core.client.ContentPanel;

@Detail(
    name = "Area Renderer Chart",
    category = "Charts",
    icon = "arearendererchart",
    minHeight = AreaRendererExample.MIN_HEIGHT,
    minWidth = AreaRendererExample.MIN_WIDTH
)
public class AreaRendererExample implements IsWidget, EntryPoint {

  interface BrowserProperties extends PropertyAccess<BrowserData> {
    ValueProvider<BrowserProxy, Double> Chrome();

    ValueProvider<BrowserProxy, String> date();

    ValueProvider<BrowserProxy, Double> Firefox();

    ValueProvider<BrowserProxy, Double> IE();

    @Path("date")
    ModelKeyProvider<BrowserProxy> nameKey();

    ValueProvider<BrowserProxy, Double> Opera();

    ValueProvider<BrowserProxy, Double> Other();

    ValueProvider<BrowserProxy, Double> Safari();
  }

  protected static final int MIN_HEIGHT = 480;
  protected static final int MIN_WIDTH = 640;

  private ContentPanel panel;

  @Override
  public Widget asWidget() {
    if (panel == null) {
      BrowserProperties props = GWT.create(BrowserProperties.class);

      final ListStore<BrowserProxy> store = new ListStore<BrowserProxy>(props.nameKey());
      store.addAll(ExampleData.getBrowserData());

      TextSprite titleUsage = new TextSprite("Usage %");
      titleUsage.setFontSize(18);
      
      NumericAxis<BrowserProxy> axis = new NumericAxis<BrowserProxy>();
      axis.setPosition(Position.LEFT);
      axis.addField(props.Chrome());
      axis.addField(props.Firefox());
      axis.addField(props.IE());
      axis.addField(props.Opera());
      axis.addField(props.Other());
      axis.addField(props.Safari());
      axis.setDisplayGrid(true);      
      axis.setTitleConfig(titleUsage);
      axis.setMaximum(100);
      axis.setMinimum(0);

      TextSprite sprite = new TextSprite();
      sprite.setRotation(315);
      
      CategoryAxis<BrowserProxy, String> catAxis = new CategoryAxis<BrowserProxy, String>();
      catAxis.setPosition(Position.BOTTOM);
      catAxis.setField(props.date());
      catAxis.setLabelPadding(-10);
      catAxis.setLabelConfig(sprite);
      catAxis.setLabelTolerance(50);

      Gradient area1 = new Gradient(0);
      area1.addStop(new Stop(0, new RGB("#2fa2df")));
      area1.addStop(new Stop(100, new RGB("#1d86be")));

      Gradient area2 = new Gradient(0);
      area2.addStop(new Stop(0, new RGB("#3c852e")));
      area2.addStop(new Stop(100, new RGB("#2b5f21")));

      Gradient area3 = new Gradient(0);
      area3.addStop(new Stop(0, new RGB("#ea6611")));
      area3.addStop(new Stop(100, new RGB("#ba510e")));

      Gradient area4 = new Gradient(0);
      area4.addStop(new Stop(0, new RGB("#9ab0d5")));
      area4.addStop(new Stop(100, new RGB("#7694c6")));

      Gradient area5 = new Gradient(0);
      area5.addStop(new Stop(0, new RGB("#ba0a19")));
      area5.addStop(new Stop(100, new RGB("#8a0712")));

      Gradient area6 = new Gradient(0);
      area6.addStop(new Stop(0, new RGB("#282828")));
      area6.addStop(new Stop(100, new RGB("#0e0e0e")));

      SeriesToolTipConfig<BrowserProxy> toolTip = new SeriesToolTipConfig<BrowserProxy>();
      toolTip.setTrackMouse(true);
      toolTip.setHideDelay(200);
      toolTip.setLabelProvider(new SeriesLabelProvider<BrowserProxy>() {
        @Override
        public String getLabel(BrowserProxy item, ValueProvider<? super BrowserProxy, ? extends Number> valueProvider) {
          return valueProvider.getPath() + " - " + item.getDate() + " - " + valueProvider.getValue(item) + "%";
        }
      });
      
      PathSprite highlightLine = new PathSprite();
      highlightLine.setHidden(true);
      highlightLine.addCommand(new MoveTo(0, 0));
      highlightLine.setZIndex(1000);
      highlightLine.setStrokeWidth(5);
      highlightLine.setStroke(new RGB("#444"));
      highlightLine.setOpacity(0.3);
      
      AreaSeries<BrowserProxy> series = new AreaSeries<BrowserProxy>();
      series.setHighlighting(true);
      series.setYAxisPosition(Position.LEFT);
      series.addYField(props.IE());
      series.addYField(props.Chrome());
      series.addYField(props.Firefox());
      series.addYField(props.Safari());
      series.addYField(props.Opera());
      series.addYField(props.Other());
      series.addColor(area1);
      series.addColor(area2);
      series.addColor(area3);
      series.addColor(area4);
      series.addColor(area5);
      series.addColor(area6);
      series.setStroke(new Color("#666"));
      series.setToolTipConfig(toolTip);
      series.setHighlightLineConfig(highlightLine);
      series.setRenderer(new SeriesRenderer<BrowserProxy>() {
        @Override
        public void spriteRenderer(Sprite sprite, int index, ListStore<BrowserProxy> store) {
          sprite.setOpacity(0.86);
          sprite.redraw();
        }
      });

      Legend<BrowserProxy> legend = new Legend<BrowserProxy>();
      legend.setItemHiding(true);
      legend.getBorderConfig().setStrokeWidth(0);

      final Chart<BrowserProxy> chart = new Chart<BrowserProxy>();
      chart.setStore(store);
      chart.addAxis(axis);
      chart.addAxis(catAxis);
      chart.addGradient(area1);
      chart.addGradient(area2);
      chart.addGradient(area3);
      chart.addGradient(area4);
      chart.addGradient(area5);
      chart.addGradient(area6);
      chart.addSeries(series);
      chart.setLegend(legend);
      chart.setDefaultInsets(30);
      

      panel = new ContentPanel();
      panel.setHeading("Area Renderer Chart");
      panel.add(chart);
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
