package com.sencha.gxt.explorer.client.chart;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor.Path;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.chart.client.chart.Chart;
import com.sencha.gxt.chart.client.chart.event.SeriesSelectionEvent;
import com.sencha.gxt.chart.client.chart.event.SeriesSelectionEvent.SeriesSelectionHandler;
import com.sencha.gxt.chart.client.chart.series.AreaHighlighter;
import com.sencha.gxt.chart.client.chart.series.PieSeries;
import com.sencha.gxt.chart.client.chart.series.Series.LabelPosition;
import com.sencha.gxt.chart.client.chart.series.SeriesLabelConfig;
import com.sencha.gxt.chart.client.chart.series.SeriesRenderer;
import com.sencha.gxt.chart.client.draw.Color;
import com.sencha.gxt.chart.client.draw.RGB;
import com.sencha.gxt.chart.client.draw.sprite.Sprite;
import com.sencha.gxt.chart.client.draw.sprite.TextSprite;
import com.sencha.gxt.chart.client.draw.sprite.TextSprite.TextAnchor;
import com.sencha.gxt.chart.client.draw.sprite.TextSprite.TextBaseline;
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
import com.sencha.gxt.widget.core.client.info.Info;
import com.sencha.gxt.widget.core.client.toolbar.ToolBar;

@Detail(
    name = "Pie Renderer Chart",
    category = "Charts",
    icon = "pierendererchart",
    classes = { Data.class, TestData.class },
    minHeight = PieRendererExample.MIN_HEIGHT,
    minWidth = PieRendererExample.MIN_WIDTH
)
public class PieRendererExample implements IsWidget, EntryPoint {

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

  @Override
  public Widget asWidget() {
    if (panel == null) {
      final ListStore<Data> store = new ListStore<Data>(dataAccess.nameKey());
      store.addAll(TestData.getData(5, 20, 100));

      TextSprite textConfig = new TextSprite();
      textConfig.setFont("Arial");
      textConfig.setFontSize(14);
      textConfig.setTextBaseline(TextBaseline.MIDDLE);
      textConfig.setTextAnchor(TextAnchor.MIDDLE);
      textConfig.setFill(new RGB("#333"));

      SeriesLabelConfig<Data> labelConfig = new SeriesLabelConfig<Data>();
      labelConfig.setLabelPosition(LabelPosition.START);
      labelConfig.setLabelContrast(true);
      labelConfig.setValueProvider(dataAccess.name(), new LabelProvider<String>() {
        @Override
        public String getLabel(String item) {
          return item.substring(0, 3);
        }
      });
      labelConfig.setSpriteConfig(textConfig);

      final PieSeries<Data> series = new PieSeries<Data>();
      series.setAngleField(dataAccess.data1());
      series.addLengthField(dataAccess.data2());
      series.setHighlighting(true);
      series.setLabelConfig(labelConfig);
      series.setPopOutMargin(0);
      series.setHighlighter(new AreaHighlighter());
      series.addSeriesSelectionHandler(new SeriesSelectionHandler<Data>() {
        @Override
        public void onSeriesSelection(SeriesSelectionEvent<Data> event) {
          Info.display("Click", event.getValueProvider().getValue(event.getItem()).toString());
        }
      });

      final Color[] colors = {
          new RGB("#94ae0a"), new RGB("#115fa6"), new RGB("#a61120"), new RGB("#ff8809"), new RGB("#ffd13e"),
          new RGB("#a61187"), new RGB("#24ad9a"), new RGB("#a66111")};
      series.setRenderer(new SeriesRenderer<Data>() {
        @Override
        public void spriteRenderer(Sprite sprite, int index, ListStore<Data> store) {
          double value = dataAccess.data1().getValue(store.get(index));
          sprite.setFill(colors[(int) Math.round(value) % 8]);
          sprite.setStroke(RGB.WHITE);
          sprite.setStrokeWidth(2);
          sprite.redraw();
        }
      });

      final Chart<Data> chart = new Chart<Data>();
      chart.setDefaultInsets(30);
      chart.setStore(store);
      chart.setShadowChart(false);
      chart.addSeries(series);

      TextButton regenerate = new TextButton("Reload Data");
      regenerate.addSelectHandler(new SelectHandler() {
        @Override
        public void onSelect(SelectEvent event) {
          store.clear();
          store.addAll(TestData.getData(5, 20, 100));
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
      panel.setHeading("Pie Renderer Chart");
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
